package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.BoundedRate;
import com.mariusrubin.calculationengine.api.RateLevel;
import java.math.BigDecimal;

/**
 * Simple default implementation for {@link BoundedRate}.
 *
 * @param level      the level
 * @param rate       the rate percentage - note this should be expressed as a percentage that is
 *                   100x greater than the actual number e.g. 24.23% should be represented as 24.23
 *                   and not 0.2423.
 * @param upperBound the upper bound
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultBoundedRate(RateLevel level, BigDecimal rate, BigDecimal upperBound) implements
                                                                                          BoundedRate {

  /**
   * Build a bounded rate.
   *
   * @param level      the level of the rate
   * @param rate       the rate percentage - note this should be expressed as a percentage that is
   *                   100x greater than the actual number e.g. 24.23% should be represented as
   *                   24.23f and not 0.2423f.
   * @param upperBound the upper bound
   */
  public DefaultBoundedRate(final RateLevel level,
                            final float rate,
                            final float upperBound) {
    this(level, new BigDecimal(rate), new BigDecimal(upperBound));
  }

  /**
   * Build a bounded rate.
   *
   * @param level      the level of the rate
   * @param rate       the rate percentage - note this should be expressed as a percentage that is
   *                   100x greater than the actual number e.g. 24% should be represented as
   *                   24.
   * @param upperBound the upper bound
   */
  public DefaultBoundedRate(final RateLevel level,
                            final int rate,
                            final int upperBound) {

    this(level, new BigDecimal(rate), new BigDecimal(upperBound));

  }
}
