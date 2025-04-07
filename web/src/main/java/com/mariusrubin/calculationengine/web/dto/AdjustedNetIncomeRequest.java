package com.mariusrubin.calculationengine.web.dto;

import com.mariusrubin.calculationengine.UkTaxRates;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class AdjustedNetIncomeRequest {

  @NotNull(message = "Tax year is required")
  private String taxYear;
  
  @NotNull(message = "Total taxable income is required")
  @PositiveOrZero(message = "Total taxable income must be zero or positive")
  private BigDecimal totalTaxableIncome;
  
  @PositiveOrZero(message = "Total pension contribution must be zero or positive")
  private BigDecimal totalPensionContribution = BigDecimal.ZERO;
  
  @PositiveOrZero(message = "Total gifts must be zero or positive")
  private BigDecimal totalGifts = BigDecimal.ZERO;

  // Getters and setters
  public String getTaxYear() {
    return taxYear;
  }

  public void setTaxYear(String taxYear) {
    this.taxYear = taxYear;
  }

  public BigDecimal getTotalTaxableIncome() {
    return totalTaxableIncome;
  }

  public void setTotalTaxableIncome(BigDecimal totalTaxableIncome) {
    this.totalTaxableIncome = totalTaxableIncome;
  }

  public BigDecimal getTotalPensionContribution() {
    return totalPensionContribution;
  }

  public void setTotalPensionContribution(BigDecimal totalPensionContribution) {
    this.totalPensionContribution = totalPensionContribution;
  }

  public BigDecimal getTotalGifts() {
    return totalGifts;
  }

  public void setTotalGifts(BigDecimal totalGifts) {
    this.totalGifts = totalGifts;
  }
  
  public UkTaxRates getUkTaxRates() {
    return UkTaxRates.valueOf(taxYear);
  }
}