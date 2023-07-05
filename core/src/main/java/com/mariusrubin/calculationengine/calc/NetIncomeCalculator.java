package com.mariusrubin.calculationengine.calc;

import com.mariusrubin.calculationengine.api.TaxPayer;
import java.math.BigDecimal;

/**
 * Calculates net income (used in HMRC tax breakdowns and in calculating adjusted net income).
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class NetIncomeCalculator {

  private final AllowableExpenseCalculator allowableExpenseCalculator = new AllowableExpenseCalculator();
  private final TotalBenefitsCalculator    totalBenefitsCalculator    = new TotalBenefitsCalculator();
  private final TotalIncomeCalculator      totalIncomeCalculator      = new TotalIncomeCalculator();

  /**
   * Calculate the net income.
   *
   * @param taxPayer the taxpayer
   * @return the amount
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {

    return calculate(totalIncomeCalculator.calculate(taxPayer),
                     totalBenefitsCalculator.calculate(taxPayer),
                     allowableExpenseCalculator.calculate(taxPayer));
  }

  /**
   * Calculate the net income.
   *
   * @param totalIncome            the total income
   * @param totalBenefits          the total benefits
   * @param totalAllowableExpenses the total expenses
   * @return the amount
   */
  public BigDecimal calculate(final BigDecimal totalIncome,
                              final BigDecimal totalBenefits,
                              final BigDecimal totalAllowableExpenses) {
    return totalIncome.add(totalBenefits).subtract(totalAllowableExpenses);
  }
}