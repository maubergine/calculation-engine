package com.mariusrubin.calculationengine.web.dto;

import com.mariusrubin.calculationengine.UkTaxRates;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class BasicRateAdjustmentRequest {

  @NotNull(message = "Tax year is required")
  private String taxYear;
  
  @NotNull(message = "Pension contribution is required")
  @PositiveOrZero(message = "Pension contribution must be zero or positive")
  private BigDecimal pensionContribution;

  // Getters and setters
  public String getTaxYear() {
    return taxYear;
  }

  public void setTaxYear(String taxYear) {
    this.taxYear = taxYear;
  }

  public BigDecimal getPensionContribution() {
    return pensionContribution;
  }

  public void setPensionContribution(BigDecimal pensionContribution) {
    this.pensionContribution = pensionContribution;
  }
  
  public UkTaxRates getUkTaxRates() {
    return UkTaxRates.valueOf(taxYear);
  }
}