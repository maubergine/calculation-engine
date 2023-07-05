package com.mariusrubin.calculationengine.api;

import java.math.BigDecimal;

/**
 * A specific sort of rate that only applies up to a certain point/value. This is mainly relevant
 * in the context of marginal taxes which only apply for a certain amount of income.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface BoundedRate extends Rate {

  /**
   * The upper bound for the rate i.e. the maximum amount to which it applies.
   *
   * @return the amount
   */
  BigDecimal upperBound();

}
