package com.mariusrubin.calculationengine.api;

import java.math.BigDecimal;

/**
 * An instance of income.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface Income {

  /**
   * The income amount.
   *
   * @return the amount
   */
  BigDecimal amount();

  /**
   * The type of income.
   *
   * @return the type
   */
  IncomeType type();

}
