package com.mariusrubin.calculationengine.api;

import java.math.BigDecimal;

/**
 * A gift which qualifies for gift aid.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface Gift {

  /**
   * The amount/value of the gift.
   *
   * @return the amount
   */
  BigDecimal amount();

}
