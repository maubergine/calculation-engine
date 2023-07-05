package com.mariusrubin.calculationengine.api.calc;

import java.math.BigDecimal;

/**
 * The result of calculating someone's personal allowance (which can be tapered down for higher
 * incomes).
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface PersonalAllowanceCalc {

  /**
   * The calculated personal allowance.
   *
   * @return the amount
   */
  BigDecimal allowance();

  /**
   * The amount by which income has exceeded the threshold for personal allowance being tapered
   * down.
   *
   * @return the amount
   */
  BigDecimal incomeOverThreshold();

  /**
   * The amount by which the personal allowance has been tapered.
   *
   * @return the amount
   */
  BigDecimal taperAmount();

}
