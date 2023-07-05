package com.mariusrubin.calculationengine.api;

import java.math.BigDecimal;

/**
 * A instance of a tax rate i.e. a percentage that is then applied to income by the engine. Rates
 * often come in levels/bands e.g. a basic/upper rate, as well as some rates being "nil" i.e. they
 * are an allowance.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface Rate {

  /**
   * A convenience "zero" rate used to represent scenarios where no tax has been applied.
   *
   * @return the zero rate
   */
  static Rate zero() {
    return new Rate() {
      @Override
      public RateLevel level() {
        return RateLevel.ZERO;
      }

      @Override
      public BigDecimal rate() {
        return BigDecimal.ZERO;
      }

    };
  }

  /**
   * The level of the rate (e.g. {@link RateLevel#BASIC}.
   *
   * @return the level
   */
  RateLevel level();

  /**
   * The rate. Because tax percentages are so often expressed as a number e.g. 40%, 38.25% etc. this
   * represents the percentage to two decimal places i.e. 38.25% is represented as 38.25 rather
   * than 0.3825. The engine takes care of using/multiplying these numbers appropriately.
   *
   * @return the rate
   */
  BigDecimal rate();

  /**
   * Check to confirm that a given rate is of a given level.
   *
   * @param rateLevel the level to check against
   * @return true if this rate is of the given level else false
   */
  default boolean isLevel(final RateLevel rateLevel) {
    return level() == rateLevel;
  }

}
