package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.PensionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Pension that is predicted based on being a %age of salary. This object takes care of the
 * multiplication as part of implementing the {@link Pension} interface. If the pension amounts are
 * known then {@link KnownPension} should be used instead.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class PredictedEmployerPension implements Pension {

  //TODO link more explicitly to income
  //in other words, find a tidy way of linking this pension amount to employment income declared
  //elsewhere in the input information, rather than having duplicate the pay in this object

  @NotNull
  @DecimalMin("0")
  @Digits(integer = 9, fraction = 2)
  private BigDecimal income;

  @NotNull
  @DecimalMin("0")
  private BigDecimal percentage;

  public BigDecimal getIncome() {
    return income;
  }

  public void setIncome(final BigDecimal income) {
    this.income = income;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(final BigDecimal percentage) {
    this.percentage = percentage;
  }

  @Override
  public BigDecimal amount() {
    return percentage.multiply(income).setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public PensionType type() {
    return PensionType.EMPLOYER;
  }

}
