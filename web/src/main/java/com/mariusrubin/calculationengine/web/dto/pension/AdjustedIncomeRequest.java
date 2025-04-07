package com.mariusrubin.calculationengine.web.dto.pension;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class AdjustedIncomeRequest {

  @NotNull(message = "Net income is required")
  @PositiveOrZero(message = "Net income must be zero or positive")
  private BigDecimal netIncome;
  
  @NotNull(message = "Employer pension is required")
  @PositiveOrZero(message = "Employer pension must be zero or positive")
  private BigDecimal employerPension;

  // Getters and setters
  public BigDecimal getNetIncome() {
    return netIncome;
  }

  public void setNetIncome(BigDecimal netIncome) {
    this.netIncome = netIncome;
  }

  public BigDecimal getEmployerPension() {
    return employerPension;
  }

  public void setEmployerPension(BigDecimal employerPension) {
    this.employerPension = employerPension;
  }
}