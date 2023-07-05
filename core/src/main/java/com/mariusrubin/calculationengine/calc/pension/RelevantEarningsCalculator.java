package com.mariusrubin.calculationengine.calc.pension;

import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.calc.TotalBenefitsCalculator;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;

/**
 * Calculates relevant
 * <a
 * href="https://www.gov.uk/hmrc-internal-manuals/pensions-tax-manual/ptm044100#earnings">earnings
 * that attract tax relief</a> pension context.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class RelevantEarningsCalculator {

  private final TotalBenefitsCalculator benefitsCalculator = new TotalBenefitsCalculator();

  /**
   * Calculate the relevant earnings.
   *
   * @param taxPayer the taxpayer
   * @return the amount of relevant earnings
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {

    final var employmentIncomes = taxPayer.incomes(IncomeType.EMPLOYMENT)
                                          .stream()
                                          .map(EmploymentIncome.class::cast)
                                          .toList();

    final var totalEmpIncome = employmentIncomes.stream()
                                                .map(Income::amount)
                                                .map(TaxMathUtils::roundDownInt)
                                                .reduce(BigDecimal::add)
                                                .orElse(TaxMathUtils.ZERO);

    final var totalBens = benefitsCalculator.calculate(employmentIncomes);

    return totalEmpIncome.add(totalBens);

  }

}
