package com.mariusrubin.calculationengine.calc;

import com.mariusrubin.calculationengine.api.Benefit;
import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Calculates the total benefits across all employments.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class TotalBenefitsCalculator {

  /**
   * Calculate the total benefit amount.
   *
   * @param incomes the taxpayer's employment incomes.
   * @return the amount
   */
  public BigDecimal calculate(final List<EmploymentIncome> incomes) {

    return incomes.stream()
                  .map(EmploymentIncome::benefits)
                  .flatMap(Collection::stream)
                  .map(Benefit::amount)
                  .map(TaxMathUtils::roundDownInt)
                  .reduce(BigDecimal::add)
                  .orElse(TaxMathUtils.ZERO);

  }

  /**
   * Calculate the total benefit amount.
   *
   * @param payer the taxpayer employment incomes.
   * @return the amount
   */
  public BigDecimal calculate(final TaxPayer payer) {
    return calculate(payer.incomes(IncomeType.EMPLOYMENT)
                          .stream()
                          .map(EmploymentIncome.class::cast)
                          .toList());
  }

}
