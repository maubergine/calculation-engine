package com.mariusrubin.calculationengine.api;

import java.math.BigDecimal;

/**
 * An allowable expense. These are counted against income.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface Expense {

  /**
   * The amount of the expense.
   *
   * @return the amount
   */
  BigDecimal amount();

}
