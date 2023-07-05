package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.IncomeType.DIVIDENDS;
import static com.mariusrubin.calculationengine.api.IncomeType.EMPLOYMENT;
import static com.mariusrubin.calculationengine.api.IncomeType.INTEREST;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.Benefit;
import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.IncomeCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultIncomeCalc;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Calculates the total amounts of income that feed into various aspects of the tax calculation,
 * or are presented back by HMRC to explain taxation.
 * <br>
 * <br>
 * Due to the architecture of the engine, this calculator uses the same <i>sub-calculators</i> that
 * generate the amounts used to calculate tax elsewhere, but it is not the case that the specific
 * outputs of this calculator are reused by other calculators. In other words, a change to this
 * class whereby it hard-coded adjusted net income, would not impact anywhere else, and would result
 * in the engine as a whole showing outputs that were contradictory/conflicting.
 * <br>
 * <br>
 * This design decision arose from trading off integrity of the calculation vs. added complexity
 * arising from having to carefully control calculation sequence/couple the outputs of one
 * calculator to another. This decision may not be the right one, so could be revisited in future.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class IncomeCalculator {

  private final AdjustedNetIncomeCalculator adjustedNetIncomeCalculator = new AdjustedNetIncomeCalculator();
  private final AllowableExpenseCalculator  allowableExpenseCalculator  = new AllowableExpenseCalculator();
  private final NetIncomeCalculator         netIncomeCalculator         = new NetIncomeCalculator();
  private final TotalIncomeCalculator       totalIncomeCalculator       = new TotalIncomeCalculator();

  /**
   * Calculate the taxpayer's income.
   *
   * @param payer the taxpayer
   * @param rates the UK tax rates required for the calculation
   * @return the taxpayer's income
   */
  public IncomeCalc calculate(final TaxPayer payer, final UkTaxRates rates) {

    final var totalEmpIncome = totalIncomeCalculator.calculate(payer, EMPLOYMENT);

    final var totalDividendIncome = totalIncomeCalculator.calculate(payer, DIVIDENDS);

    //TODO deal with unallowable expenses
    final var totalBenefits = employmentStream(payer.incomes())
        .map(EmploymentIncome::benefits)
        .flatMap(Collection::stream)
        .map(Benefit::amount)
        .map(TaxMathUtils::roundDownInt)
        .reduce(BigDecimal::add)
        .orElse(TaxMathUtils.ZERO);

    final var totalAllowableExpenses = allowableExpenseCalculator.calculate(payer);

    final var totalFromAllEmployments = totalEmpIncome.add(totalBenefits)
                                                      .subtract(totalAllowableExpenses);

    final var totalInterest = totalIncomeCalculator.calculate(payer, INTEREST);

    final var netIncome = netIncomeCalculator.calculate(payer);

    final var adjustedNetIncome = adjustedNetIncomeCalculator.calculate(payer, rates);

    return new DefaultIncomeCalc(netIncome,
                                 adjustedNetIncome,
                                 totalEmpIncome,
                                 totalBenefits,
                                 totalAllowableExpenses,
                                 totalFromAllEmployments,
                                 totalDividendIncome,
                                 totalInterest);

  }

  private static Stream<EmploymentIncome> employmentStream(final List<Income> incomes) {
    return incomes.stream()
                  .filter(inc -> inc.type() == EMPLOYMENT)
                  .map(EmploymentIncome.class::cast);
  }

}