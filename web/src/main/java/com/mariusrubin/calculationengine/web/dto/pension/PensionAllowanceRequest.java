package com.mariusrubin.calculationengine.web.dto.pension;

import com.mariusrubin.calculationengine.UkTaxRates;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class PensionAllowanceRequest {

  @NotNull(message = "Tax year is required")
  private String taxYear;
  
  @NotNull(message = "Adjusted income is required")
  @PositiveOrZero(message = "Adjusted income must be zero or positive")
  private BigDecimal adjustedIncome;
  
  @NotNull(message = "Threshold income is required")
  @PositiveOrZero(message = "Threshold income must be zero or positive")
  private BigDecimal thresholdIncome;

  // Getters and setters
  public String getTaxYear() {
    return taxYear;
  }

  public void setTaxYear(String taxYear) {
    this.taxYear = taxYear;
  }

  public BigDecimal getAdjustedIncome() {
    return adjustedIncome;
  }

  public void setAdjustedIncome(BigDecimal adjustedIncome) {
    this.adjustedIncome = adjustedIncome;
  }

  public BigDecimal getThresholdIncome() {
    return thresholdIncome;
  }

  public void setThresholdIncome(BigDecimal thresholdIncome) {
    this.thresholdIncome = thresholdIncome;
  }
  
  public UkTaxRates getUkTaxRates() {
    return UkTaxRates.valueOf(taxYear);
  }
}