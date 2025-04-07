package com.mariusrubin.calculationengine.web.dto.pension;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class ThresholdIncomeRequest {

  @NotNull(message = "Net income is required")
  @PositiveOrZero(message = "Net income must be zero or positive")
  private BigDecimal netIncome;
  
  @NotNull(message = "Employee pension is required")
  @PositiveOrZero(message = "Employee pension must be zero or positive")
  private BigDecimal employeePension;

  // Getters and setters
  public BigDecimal getNetIncome() {
    return netIncome;
  }

  public void setNetIncome(BigDecimal netIncome) {
    this.netIncome = netIncome;
  }

  public BigDecimal getEmployeePension() {
    return employeePension;
  }

  public void setEmployeePension(BigDecimal employeePension) {
    this.employeePension = employeePension;
  }
}