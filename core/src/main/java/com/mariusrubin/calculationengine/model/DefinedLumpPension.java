package com.mariusrubin.calculationengine.model;

import static com.mariusrubin.calculationengine.api.PensionType.DEFINED_BENEFIT_LUMP;

import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.PensionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A defined pension lump contribution i.e. an amount paid in lump in order to "buy" future yearly
 * payments. Different pension schemes specify different conversion rates between the lump sum
 * and the yearly amount. Once calculated, the yearly amount is valued in the same way as a defined
 * pension.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class DefinedLumpPension implements Pension {

  @NotNull
  @DecimalMin("0")
  private BigDecimal amount;

  @NotNull
  @DecimalMin("0")
  private BigDecimal costPerHundredYearlyPay;

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getCostPerHundredYearlyPay() {
    return costPerHundredYearlyPay;
  }

  public void setCostPerHundredYearlyPay(final BigDecimal costPerHundredYearlyPay) {
    this.costPerHundredYearlyPay = costPerHundredYearlyPay;
  }

  @Override
  public BigDecimal amount() {
    return amount;
  }

  @Override
  public PensionType type() {
    return DEFINED_BENEFIT_LUMP;
  }
}
