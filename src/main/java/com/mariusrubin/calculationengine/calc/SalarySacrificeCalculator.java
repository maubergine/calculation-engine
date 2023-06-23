package com.mariusrubin.calculationengine.calc;

import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;

/**
 * Calculates the total amount given to salary sacrifice across all employments.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class SalarySacrificeCalculator {

  /**
   * Calculate the total salary sacrifie amount.
   *
   * @param taxPayer the taxpayer
   * @return the amount
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {
    return taxPayer.incomes(IncomeType.EMPLOYMENT)
                   .stream()
                   .map(EmploymentIncome.class::cast)
                   .map(EmploymentIncome::salarySacrifice)
                   .reduce(BigDecimal::add)
                   .orElse(TaxMathUtils.ZERO);
  }

}