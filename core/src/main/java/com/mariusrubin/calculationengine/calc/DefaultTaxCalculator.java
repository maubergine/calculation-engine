package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.lessThanOrEqual;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.roundDownInt;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.sum;

import com.mariusrubin.calculationengine.TaxCalculator;
import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.PensionCalc;
import com.mariusrubin.calculationengine.api.calc.TaxCalc;
import com.mariusrubin.calculationengine.calc.pension.PensionAllowanceCalculator;
import com.mariusrubin.calculationengine.calc.pension.TotalEmployerPensionCalculator;
import com.mariusrubin.calculationengine.model.PensionCharge;
import com.mariusrubin.calculationengine.model.calc.DefaultTaxCalc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The main implementation class that orchestrates all the sub-calculators to provide an overall
 * calculation of the amount of tax/payments due based on the information provided.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class DefaultTaxCalculator implements TaxCalculator {

  private final TotalSippCalculator            totalSipp           = new TotalSippCalculator();
  private final IncomeTaxCalculator            incomeTax           = new IncomeTaxCalculator();
  private final PaymentDueCalculator           paymentDue          = new PaymentDueCalculator();
  private final PensionAllowanceCalculator     pensionAllowance    = new PensionAllowanceCalculator();
  private final TotalEmployerPensionCalculator employerPension     = new TotalEmployerPensionCalculator();
  private final SalarySacrificeCalculator      salarySacrifice     = new SalarySacrificeCalculator();
  private final BasicRateAdjustmentCalculator  basicRateAdjustment = new BasicRateAdjustmentCalculator();
  private final PersonalAllowanceCalculator    personalAllowance   = new PersonalAllowanceCalculator();
  private final IncomeCalculator               income              = new IncomeCalculator();

  private final UkTaxRates rates;

  /**
   * Constructs an instance of the calculator that will operate using the provided rates.
   *
   * @param rates the tax rates to use in the calculations
   */
  public DefaultTaxCalculator(final UkTaxRates rates) {
    this.rates = rates;
  }

  @Override
  public TaxCalc calculate(final TaxPayer payer) {

    final var incomeAnalysis = income.calculate(payer, rates);

    final var basicAdjustmentAnalysis = basicRateAdjustment.calculate(payer, rates);

    final var paAnalysis = personalAllowance.calculate(payer, rates);

    final var pensionAnalysis = pensionAllowance.calculate(payer, rates);

    final var withPensionAA = addPensionCharges(payer, rates, pensionAnalysis);

    final var ita = incomeTax.calculate(rates,
                                        withPensionAA,
                                        paAnalysis,
                                        basicAdjustmentAnalysis);

    //TODO some kind of NI projection
    //TODO BigDecimal scale for all model items
    //TODO work out how to deal with lower Scottish income tax rates

    final var due = paymentDue.calculate(ita,
                                         payer.paymentsMade(),
                                         payer.taxPaidOverride(),
                                         rates.financialYear());

    return new DefaultTaxCalc(incomeAnalysis,
                              pensionAnalysis,
                              ita,
                              due,
                              paAnalysis,
                              basicAdjustmentAnalysis,
                              payer,
                              rates);

  }

  private List<Income> addPensionCharges(final TaxPayer payer,
                                         final UkTaxRates rates,
                                         final PensionCalc pensionAnalysis) {

    //TODO add NI rebate amount to this list
    //TODO deal with the fact that contribution to defined benefit is valued differently
    //when contributing vs. when increasing pension allowance
    final var totalPensionContribution = roundDownInt(sum(totalSipp.calculate(payer, rates),
                                                          employerPension.calculate(payer),
                                                          salarySacrifice.calculate(payer)));

    //If your pension contribution is under your allowance then no additional charges.
    if (lessThanOrEqual(totalPensionContribution, pensionAnalysis.allowance())) {
      return payer.incomes();
    }

    final var incomes = new ArrayList<>(payer.incomes());

    incomes.add(new PensionCharge(totalPensionContribution.subtract(pensionAnalysis.allowance())));

    return Collections.unmodifiableList(incomes);

  }

}
