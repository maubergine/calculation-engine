package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.Rate;
import com.mariusrubin.calculationengine.api.RateLevel;
import java.math.BigDecimal;

/**
 * Simple default implementation of {@link Rate}.
 *
 * @param level the level of the rate
 * @param rate  the rate percentage - note this should be expressed as a percentage that is 100x
 *              greater than the actual number e.g. 24.23% should be represented as 24.23 and not
 *              0.2423.
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultRate(RateLevel level, BigDecimal rate) implements Rate {

  /**
   * @param level the level of the rate
   * @param rate  the rate percentage - note this should be expressed as a percentage that is 100x
   *              greater than the actual number e.g. 24.23% should be represented as 24.23f and not
   *              0.2423f.
   */
  public DefaultRate(final RateLevel level, final float rate) {
    this(level, new BigDecimal(Float.toString(rate)));
  }

  /**
   * @param level the level of the rate
   * @param rate  the rate percentage - note this should be expressed as a percentage that is 100x
   *              greater than the actual number e.g. 24% should be represented as 24.
   */
  public DefaultRate(final RateLevel level, final int rate) {
    this(level, new BigDecimal(rate));
  }

}
