package com.mariusrubin.calculationengine.web.dto;

import com.mariusrubin.calculationengine.UkTaxRates;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class DefinedBenefitValueRequest {

  @NotNull(message = "Tax year is required")
  private String taxYear;
  
  @NotNull(message = "Increase amount is required")
  @PositiveOrZero(message = "Increase amount must be zero or positive")
  private BigDecimal increase;

  // Getters and setters
  public String getTaxYear() {
    return taxYear;
  }

  public void setTaxYear(String taxYear) {
    this.taxYear = taxYear;
  }

  public BigDecimal getIncrease() {
    return increase;
  }

  public void setIncrease(BigDecimal increase) {
    this.increase = increase;
  }
  
  public UkTaxRates getUkTaxRates() {
    return UkTaxRates.valueOf(taxYear);
  }
}