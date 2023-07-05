package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.Income;
import java.math.BigDecimal;

/**
 * Convenience abstraction that wraps positive amounts and provides a standard implementation for
 * {@link Income}.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public abstract class AbstractKnownIncome extends AbstractKnownPositiveAmount implements Income {

  protected AbstractKnownIncome() {
  }

  protected AbstractKnownIncome(final BigDecimal amount) {
    super(amount);
  }

  @Override
  public BigDecimal amount() {
    return getAmount();
  }

}
