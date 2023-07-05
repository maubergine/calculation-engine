package com.mariusrubin.calculationengine.calc.pension;

import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.PensionType;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;

/**
 * Calculates the total amount contributed by employers to pensions across all employments.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class TotalEmployerPensionCalculator {

  /**
   * Calculate the total employer contribution.
   *
   * @param taxPayer the taxpayer
   * @return the total contribution amount
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {

    return taxPayer.pensions()
                   .stream()
                   .filter(p -> p.type() == PensionType.EMPLOYER)
                   .map(Pension::amount)
                   .reduce(BigDecimal::add)
                   .orElse(TaxMathUtils.ZERO);

  }

}