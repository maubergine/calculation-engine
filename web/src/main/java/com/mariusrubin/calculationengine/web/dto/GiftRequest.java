package com.mariusrubin.calculationengine.web.dto;

import com.mariusrubin.calculationengine.UkTaxRates;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class GiftRequest {

  @NotNull(message = "Tax year is required")
  private String taxYear;
  
  @NotNull(message = "Gift amount is required")
  @PositiveOrZero(message = "Gift amount must be zero or positive")
  private BigDecimal giftAmount;

  // Getters and setters
  public String getTaxYear() {
    return taxYear;
  }

  public void setTaxYear(String taxYear) {
    this.taxYear = taxYear;
  }

  public BigDecimal getGiftAmount() {
    return giftAmount;
  }

  public void setGiftAmount(BigDecimal giftAmount) {
    this.giftAmount = giftAmount;
  }
  
  public UkTaxRates getUkTaxRates() {
    return UkTaxRates.valueOf(taxYear);
  }
}