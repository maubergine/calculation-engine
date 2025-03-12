package com.mariusrubin.calculationengine.web.dto;

import com.mariusrubin.calculationengine.FinancialYear;
import com.mariusrubin.calculationengine.UkFinancialYear;
import java.math.BigDecimal;

public class CalculationResponse {

  private FinancialYear taxYear;
  private BigDecimal totalIncome;
  private BigDecimal taxableIncome;
  private BigDecimal personalAllowance;
  
  private BigDecimal incomeTax;
  private BigDecimal basicRateIncomeTax;
  private BigDecimal higherRateIncomeTax;
  private BigDecimal additionalRateIncomeTax;
  
  private BigDecimal dividendTax;
  private BigDecimal basicRateDividendTax;
  private BigDecimal higherRateDividendTax;
  private BigDecimal additionalRateDividendTax;
  
  private BigDecimal dividendIncome;
  private BigDecimal dividendAllowance;
  
  private BigDecimal pensionCharge;
  private BigDecimal pensionAnnualAllowance;
  
  private BigDecimal totalTaxDue;
  
  // Getters and setters
  public FinancialYear getTaxYear() {
    return taxYear;
  }
  
  public void setTaxYear(FinancialYear taxYear) {
    this.taxYear = taxYear;
  }
  
  public BigDecimal getTotalIncome() {
    return totalIncome;
  }
  
  public void setTotalIncome(BigDecimal totalIncome) {
    this.totalIncome = totalIncome;
  }
  
  public BigDecimal getTaxableIncome() {
    return taxableIncome;
  }
  
  public void setTaxableIncome(BigDecimal taxableIncome) {
    this.taxableIncome = taxableIncome;
  }
  
  public BigDecimal getPersonalAllowance() {
    return personalAllowance;
  }
  
  public void setPersonalAllowance(BigDecimal personalAllowance) {
    this.personalAllowance = personalAllowance;
  }
  
  public BigDecimal getIncomeTax() {
    return incomeTax;
  }
  
  public void setIncomeTax(BigDecimal incomeTax) {
    this.incomeTax = incomeTax;
  }
  
  public BigDecimal getBasicRateIncomeTax() {
    return basicRateIncomeTax;
  }
  
  public void setBasicRateIncomeTax(BigDecimal basicRateIncomeTax) {
    this.basicRateIncomeTax = basicRateIncomeTax;
  }
  
  public BigDecimal getHigherRateIncomeTax() {
    return higherRateIncomeTax;
  }
  
  public void setHigherRateIncomeTax(BigDecimal higherRateIncomeTax) {
    this.higherRateIncomeTax = higherRateIncomeTax;
  }
  
  public BigDecimal getAdditionalRateIncomeTax() {
    return additionalRateIncomeTax;
  }
  
  public void setAdditionalRateIncomeTax(BigDecimal additionalRateIncomeTax) {
    this.additionalRateIncomeTax = additionalRateIncomeTax;
  }
  
  public BigDecimal getDividendTax() {
    return dividendTax;
  }
  
  public void setDividendTax(BigDecimal dividendTax) {
    this.dividendTax = dividendTax;
  }
  
  public BigDecimal getBasicRateDividendTax() {
    return basicRateDividendTax;
  }
  
  public void setBasicRateDividendTax(BigDecimal basicRateDividendTax) {
    this.basicRateDividendTax = basicRateDividendTax;
  }
  
  public BigDecimal getHigherRateDividendTax() {
    return higherRateDividendTax;
  }
  
  public void setHigherRateDividendTax(BigDecimal higherRateDividendTax) {
    this.higherRateDividendTax = higherRateDividendTax;
  }
  
  public BigDecimal getAdditionalRateDividendTax() {
    return additionalRateDividendTax;
  }
  
  public void setAdditionalRateDividendTax(BigDecimal additionalRateDividendTax) {
    this.additionalRateDividendTax = additionalRateDividendTax;
  }
  
  public BigDecimal getTotalTaxDue() {
    return totalTaxDue;
  }
  
  public void setTotalTaxDue(BigDecimal totalTaxDue) {
    this.totalTaxDue = totalTaxDue;
  }
  
  public BigDecimal getDividendIncome() {
    return dividendIncome;
  }
  
  public void setDividendIncome(BigDecimal dividendIncome) {
    this.dividendIncome = dividendIncome;
  }
  
  public BigDecimal getDividendAllowance() {
    return dividendAllowance;
  }
  
  public void setDividendAllowance(BigDecimal dividendAllowance) {
    this.dividendAllowance = dividendAllowance;
  }
  
  public BigDecimal getPensionCharge() {
    return pensionCharge;
  }
  
  public void setPensionCharge(BigDecimal pensionCharge) {
    this.pensionCharge = pensionCharge;
  }
  
  public BigDecimal getPensionAnnualAllowance() {
    return pensionAnnualAllowance;
  }
  
  public void setPensionAnnualAllowance(BigDecimal pensionAnnualAllowance) {
    this.pensionAnnualAllowance = pensionAnnualAllowance;
  }
}