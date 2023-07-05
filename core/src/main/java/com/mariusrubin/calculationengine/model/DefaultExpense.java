package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.Expense;
import java.math.BigDecimal;

/**
 * Simple default implementation of {@link Expense} making it a positive amount.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class DefaultExpense extends AbstractKnownPositiveAmount implements Expense {

  @Override
  public BigDecimal amount() {
    return getAmount();
  }

}
