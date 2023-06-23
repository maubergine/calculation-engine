package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.PensionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A "known" pension i.e. one where the amounts contributed to the pension are either confirmed
 * in certificates, or where the sacrificed salary has happened in the past. Where pension needs to
 * be forecast this should be done using {@link PredictedEmployerPension}.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class KnownPension implements Pension {

  @NotNull
  @DecimalMin("0")
  private BigDecimal amount;

  private boolean grossedUp;

  private PensionType type;

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public BigDecimal amount() {
    return amount;
  }

  @Override
  public boolean isGrossedUp() {
    return grossedUp;
  }

  @Override
  public PensionType type() {
    return type;
  }

  public void setGrossedUp(final boolean grossedUp) {
    this.grossedUp = grossedUp;
  }

  public PensionType getType() {
    return type;
  }

  public void setType(final PensionType type) {
    this.type = type;
  }
}
