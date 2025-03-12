package com.mariusrubin.calculationengine.web.dto;

import com.mariusrubin.calculationengine.FinancialYear;
import com.mariusrubin.calculationengine.UkFinancialYear;
import com.mariusrubin.calculationengine.UkTaxRates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class CalculationRequest {

  @NotBlank(message = "Name is required")
  private String name;
  
  @NotNull(message = "Tax year is required")
  private FinancialYear taxYear;
  
  @PositiveOrZero(message = "Employment income must be zero or positive")
  private BigDecimal employmentIncome;
  
  @PositiveOrZero(message = "Dividend income must be zero or positive")
  private BigDecimal dividendIncome;
  
  @PositiveOrZero(message = "Interest income must be zero or positive")
  private BigDecimal interestIncome;
  
  @PositiveOrZero(message = "Personal pension contribution must be zero or positive")
  private BigDecimal personalPensionContribution;
  
  @PositiveOrZero(message = "Employer pension contribution must be zero or positive")
  private BigDecimal employerPensionContribution;
  
  // Getters and setters
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public FinancialYear getTaxYear() {
    return taxYear;
  }

  public void setTaxYear(final String taxYear) {
    this.taxYear = UkTaxRates.valueOf(UkTaxRates.class, taxYear).financialYear();
  }

  public void setTaxYear(UkFinancialYear taxYear) {
    this.taxYear = taxYear;
  }
  
  public BigDecimal getEmploymentIncome() {
    return employmentIncome;
  }
  
  public void setEmploymentIncome(BigDecimal employmentIncome) {
    this.employmentIncome = employmentIncome;
  }
  
  public BigDecimal getDividendIncome() {
    return dividendIncome;
  }
  
  public void setDividendIncome(BigDecimal dividendIncome) {
    this.dividendIncome = dividendIncome;
  }
  
  public BigDecimal getInterestIncome() {
    return interestIncome;
  }
  
  public void setInterestIncome(BigDecimal interestIncome) {
    this.interestIncome = interestIncome;
  }
  
  public BigDecimal getPersonalPensionContribution() {
    return personalPensionContribution;
  }
  
  public void setPersonalPensionContribution(BigDecimal personalPensionContribution) {
    this.personalPensionContribution = personalPensionContribution;
  }
  
  public BigDecimal getEmployerPensionContribution() {
    return employerPensionContribution;
  }
  
  public void setEmployerPensionContribution(BigDecimal employerPensionContribution) {
    this.employerPensionContribution = employerPensionContribution;
  }
}