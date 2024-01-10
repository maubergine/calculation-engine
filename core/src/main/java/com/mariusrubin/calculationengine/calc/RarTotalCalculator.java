package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.PensionType.DEFINED_BENEFIT_LUMP;
import static com.mariusrubin.calculationengine.api.PensionType.RAR;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.sum;

import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * Calculates the total value of amounts given to retirement annuity schemes or similar (e.g. lump
 * sum contributions to defined benefit schemes).
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class RarTotalCalculator {

  /**
   * Calculate the total rar contribution amount.
   *
   * @param taxPayer the taxpayer
   * @return the amount
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {

    //This is a bit painful but is handling the fact that HMRC round the total amount contributed
    //to multiple pensions of the same type up to the nearest whole integer before then
    //summing that total across types.

    return taxPayer.pensions()
                   .stream()
                   .filter(p -> p.type() == RAR || p.type() == DEFINED_BENEFIT_LUMP)
                   .collect(Collectors.groupingBy(Pension::type))
                   .values()
                   .stream()
                   .map(pensions -> sum(pensions.stream().map(Pension::amount)))
                   .map(TaxMathUtils::roundUpInt)
                   .reduce(BigDecimal::add)
                   .orElse(ZERO);

  }

}