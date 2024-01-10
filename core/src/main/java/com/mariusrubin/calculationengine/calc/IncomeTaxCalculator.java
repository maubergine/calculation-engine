package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.IncomeType.DIVIDENDS;
import static com.mariusrubin.calculationengine.api.IncomeType.EMPLOYMENT;
import static com.mariusrubin.calculationengine.api.IncomeType.INTEREST;
import static com.mariusrubin.calculationengine.api.IncomeType.PENSION_CHARGE;
import static com.mariusrubin.calculationengine.api.RateLevel.ADDITIONAL;
import static com.mariusrubin.calculationengine.api.RateLevel.ADDITIONAL_NIL;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC_NIL;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER_NIL;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.lessThanOrEqual;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.positive;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.roundDownInt;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.twoDec;
import static java.math.RoundingMode.DOWN;
import static java.util.Comparator.comparing;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.Benefit;
import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.Expense;
import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.Rate;
import com.mariusrubin.calculationengine.api.RateLevel;
import com.mariusrubin.calculationengine.api.calc.BasicRateAdjustmentCalc;
import com.mariusrubin.calculationengine.api.calc.IncomeTaxCalc;
import com.mariusrubin.calculationengine.api.calc.PersonalAllowanceCalc;
import com.mariusrubin.calculationengine.api.calc.TaxedAmount;
import com.mariusrubin.calculationengine.model.DefaultRate;
import com.mariusrubin.calculationengine.model.calc.DefaultIncomeTaxCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultTaxedAmount;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Calculates the income tax due on the various types of income. Takes care of doing so according
 * to the
 * <a href="https://techzone.abrdn.com/public/personal-taxation/intro-guide-income-tax">order of
 * taxation</a>. Handles cases where there are allowances (either the personal allowance, or
 * specific allowances relating to specific sorts of income.
 * <br>
 * <br>
 * This calculator needs to be treated with the most care of any of the calculators; most other
 * sub-calculators purpose is to generate the amounts/allowances that feed into this one so that
 * the tax amounts themselves can be calculated.
 * <br>
 * <br>
 * At present this calculator can only process tax for employment income, dividends and interest
 * income (as well as handling cases where excess pension contributions generate additional tax).
 * <br>
 * <br>
 * There is no reason the calculator could not be expanded to cope with additional types of income,
 * it is just that this has not yet been implemented.
 * <br>
 * <br>
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class IncomeTaxCalculator {

  private static final Set<IncomeType> TO_CALCULATE = EnumSet.of(EMPLOYMENT,
                                                                 PENSION_CHARGE,
                                                                 DIVIDENDS,
                                                                 INTEREST);


  /**
   * Calculate income tax.
   *
   * @param rates                   the tax rates applicable
   * @param incomes                 the incomes for which tax should be calculated
   * @param pACalc                  the personal allowance available
   * @param basicRateAdjustmentCalc any adjustments to the basic rate as a result of pension/gifts
   * @return the income tax
   */
  public IncomeTaxCalc calculate(final UkTaxRates rates,
                                 final Collection<Income> incomes,
                                 final PersonalAllowanceCalc pACalc,
                                 final BasicRateAdjustmentCalc basicRateAdjustmentCalc,
                                 final BigDecimal totalRarContribs) {

    //Order the incomes according to the order of taxation - this relies on the specific order of
    //declarations in the IncomeType enum.
    final Deque<Income> sorted = incomes.stream()
                                        .filter(income -> TO_CALCULATE.contains(income.type()))
                                        .sorted(Comparator.comparing(Income::type))
                                        .collect(Collectors.toCollection(ArrayDeque::new));

    final List<TaxedAmount> taxes = new ArrayList<>();

    final var initialAllowanceRemaining = pACalc.allowance().add(totalRarContribs);

    var allowanceRemaining = initialAllowanceRemaining;

    final var initialBasicLimit = twoDec(rates.incomeTaxRates()
                                              .basicRate()
                                              .upperBound()).add(basicRateAdjustmentCalc.total());

    var basicRemaining = initialBasicLimit;

    var higherRemaining = twoDec(rates.incomeTaxRates()
                                      .higherRate()
                                      .upperBound()
                                      .subtract(rates.incomeTaxRates()
                                                     .basicRate()
                                                     .upperBound()));

    //Work through the incomes, consuming basic allowance and bands as you go.
    for (final var income : sorted) {

      //HMRC round down employment income (and associated benefits).
      final var roundedAmount = income.type() == EMPLOYMENT
                                ? roundEmployment(income)
                                : TaxMathUtils.roundDownInt(income.amount());

      BigDecimal taxable;

      //If there is some personal allowance or rar contribution to be consumed, consume it.
      if (TaxMathUtils.positive(allowanceRemaining)) {
        taxable = TaxMathUtils.max(roundedAmount.subtract(allowanceRemaining), TaxMathUtils.ZERO);
        allowanceRemaining = TaxMathUtils.max(allowanceRemaining.subtract(roundedAmount),
                                              TaxMathUtils.ZERO);
      } else {
        taxable = roundedAmount;
      }

      if (TaxMathUtils.positive(taxable)) {
        //There is income on which tax is due. For simplicity lets see if it is entirely within
        //basic and handle accordingly.
        final var basicRate      = rates.forType(income.type()).forLevel(BASIC);
        final var higherRate     = rates.forType(income.type()).forLevel(HIGHER);
        final var additionalRate = rates.forType(income.type()).forLevel(ADDITIONAL);

        if (TaxMathUtils.lessThanOrEqual(taxable, basicRemaining)) {
          //The income fits within the remaining basic band, tax at this level and move on.
          final var tax = applyRate(taxable, basicRate);
          taxes.add(new DefaultTaxedAmount(taxable, tax, income.type(), basicRate));
          basicRemaining = basicRemaining.subtract(taxable);
        } else {
          //The income exceeds the basic band, consume the rest of the basic (if there is any) and
          //then start consuming the next band.
          if (TaxMathUtils.positive(basicRemaining)) {
            final var tax = applyRate(basicRemaining, basicRate);
            taxes.add(new DefaultTaxedAmount(basicRemaining, tax, income.type(), basicRate));
            taxable = taxable.subtract(basicRemaining);
            basicRemaining = TaxMathUtils.ZERO;
          }

          //If the income fits within the next band, just use that.
          if (TaxMathUtils.lessThanOrEqual(taxable, higherRemaining)) {
            final var tax = applyRate(taxable, higherRate);
            taxes.add(new DefaultTaxedAmount(taxable, tax, income.type(), higherRate));
            higherRemaining = higherRemaining.subtract(taxable);

          } else {
            //The income exceeds the next band, so consume what's left of that.
            if (TaxMathUtils.positive(higherRemaining)) {
              final var tax = applyRate(higherRemaining, higherRate);
              taxes.add(new DefaultTaxedAmount(higherRemaining, tax, income.type(), higherRate));
              taxable = taxable.subtract(higherRemaining);
              higherRemaining = TaxMathUtils.ZERO;
            }

            //Then tax whatever is left at the additional rate.
            final var tax = applyRate(taxable, additionalRate);
            taxes.add(new DefaultTaxedAmount(taxable, tax, income.type(), additionalRate));

          }

        }

      }

    }

    //Due to how dividend and savings allowances work (and how HMRC represents this back to the
    //taxpayer), you need to undo the tax, rather than just treat the income as non-existent
    //as is the case for personal allowance or RAR contributions. Where tax has been paid across
    //multiple bands (e.g. because the savings income is what causes someone to move from basic to
    //higher rate), then you need to progressively "undo" tax.

    final var postDividends = postProcessDividends(taxes, rates);

    final var postSavings = postProcessSavings(postDividends, rates);

    return new DefaultIncomeTaxCalc(postSavings, initialBasicLimit, initialAllowanceRemaining);

  }

  //TODO consider moving post processors to dedicated classes
  private static List<TaxedAmount> postProcessSavings(final List<TaxedAmount> taxes,
                                                      final UkTaxRates rates) {

    if (taxes.stream().map(TaxedAmount::incomeType).noneMatch(inc -> inc == INTEREST)) {
      return taxes;
    }

    final var levels = taxes.stream()
                            .map(TaxedAmount::rate)
                            .map(Rate::level)
                            .collect(Collectors.toCollection(() -> EnumSet.noneOf(RateLevel.class)));

    //Your savings allowance depends on what level taxpayer you are based on your income.
    final var paRemaining = twoDec(levels.contains(ADDITIONAL) || levels.contains(ADDITIONAL_NIL)
                                   ? rates.savingsAllowances().additionalAllowance()
                                   : levels.contains(HIGHER) || levels.contains(HIGHER_NIL)
                                     ? rates.savingsAllowances().higherAllowance()
                                     : rates.savingsAllowances().basicAllowance());

    return doPostProcess(taxes, paRemaining, INTEREST);

  }

  private static List<TaxedAmount> postProcessDividends(final List<TaxedAmount> taxes,
                                                        final UkTaxRates rates) {

    final var divPaRemaining = twoDec(rates.dividendRates().allowance());

    return doPostProcess(taxes, divPaRemaining, DIVIDENDS);

  }

  private static List<TaxedAmount> doPostProcess(final List<TaxedAmount> taxes,
                                                 final BigDecimal allowance,
                                                 final IncomeType type) {

    final var mutableTaxes       = new ArrayList<>(taxes);
    var       allowanceRemaining = allowance;

    final var toProcess = taxes.stream()
                               .filter(t -> t.incomeType() == type)
                               .sorted(comparing(t -> t.rate().level()))
                               .toList();

    for (final TaxedAmount tax : toProcess) {

      if (positive(allowanceRemaining)) {

        final BigDecimal revisedAmount;
        final BigDecimal revisedTax;
        final BigDecimal paApplied;

        if (lessThanOrEqual(allowanceRemaining, tax.amount())) {
          revisedAmount = tax.amount().subtract(allowanceRemaining);
          revisedTax = applyRate(revisedAmount, tax.rate());
          paApplied = allowanceRemaining;
          allowanceRemaining = ZERO;
        } else {
          revisedAmount = ZERO;
          revisedTax = ZERO;
          paApplied = tax.amount();
          allowanceRemaining = allowanceRemaining.subtract(tax.amount());
        }

        //This relies on the uniqueness of taxed amounts in a set of taxes
        mutableTaxes.removeIf(tax::equals);
        mutableTaxes.add(new DefaultTaxedAmount(revisedAmount,
                                                revisedTax,
                                                tax.incomeType(),
                                                tax.rate()));

        final var nilRate = switch (tax.rate().level()) {
          case BASIC -> BASIC_NIL;
          case HIGHER -> HIGHER_NIL;
          case ADDITIONAL -> ADDITIONAL_NIL;
          default -> throw new IllegalStateException("Unexpected value: " + tax.rate().level());
        };

        mutableTaxes.add(new DefaultTaxedAmount(paApplied,
                                                ZERO,
                                                type,
                                                new DefaultRate(nilRate, ZERO)));

      }

    }

    return Collections.unmodifiableList(mutableTaxes);

  }

  private static BigDecimal applyRate(final BigDecimal amount, final Rate rate) {

    final var percent = TaxMathUtils.toPercent(rate);
    //HMRC round down half pennies
    return amount.multiply(percent).setScale(2, DOWN);

  }

  private static BigDecimal roundEmployment(final Income income) {

    if (income instanceof final EmploymentIncome employmentIncome) {
      final var benefitIncome = employmentIncome.benefits()
                                                .stream()
                                                .map(Benefit::amount)
                                                .map(TaxMathUtils::roundDownInt)
                                                .reduce(BigDecimal::add)
                                                .orElse(ZERO);

      //We assume that allowable expenses cannot exceed income from an employment and we are not
      //attempting to rationalise expenses across multiple employments (at least not yet).
      final var allowableExpenses = employmentIncome.expenses()
                                                    .stream()
                                                    .map(Expense::amount)
                                                    .map(TaxMathUtils::roundUpInt)
                                                    .reduce(BigDecimal::add)
                                                    .orElse(ZERO);

      return roundDownInt(income.amount()).add(benefitIncome).subtract(allowableExpenses);

    }

    throw new IllegalStateException(String.format("Income %s not an employment income", income));

  }

}