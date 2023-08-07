package com.mariusrubin.calculationengine.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Validatable abstraction for amounts that are positive.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public abstract class AbstractKnownPositiveAmount {

  protected AbstractKnownPositiveAmount() {
  }

  protected AbstractKnownPositiveAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  @NotNull
  @DecimalMin("0")
  private BigDecimal amount;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

}
