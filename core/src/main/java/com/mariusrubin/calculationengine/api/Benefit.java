package com.mariusrubin.calculationengine.api;

import java.math.BigDecimal;

/**
 * A taxable benefit.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface Benefit {

  /**
   * The benefit amount (value).
   *
   * @return the amount
   */
  BigDecimal amount();

}
