package com.mariusrubin.calculationengine.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class GrossUpperRequest {

  @NotNull(message = "Net amount is required")
  @PositiveOrZero(message = "Net amount must be zero or positive")
  private BigDecimal netAmount;
  
  @NotNull(message = "Rate is required")
  @PositiveOrZero(message = "Rate must be zero or positive")
  private BigDecimal rate;

  // Getters and setters
  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }
}