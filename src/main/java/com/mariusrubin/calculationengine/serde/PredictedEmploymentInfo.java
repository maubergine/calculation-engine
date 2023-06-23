package com.mariusrubin.calculationengine.serde;

import com.mariusrubin.calculationengine.model.DefaultBenefit;
import com.mariusrubin.calculationengine.model.DefaultExpense;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Used to serialise/deserialise information about a predicted employment. This information is then
 * used by
 * {@link com.mariusrubin.calculationengine.model.PredictedEmploymentIncome
 * PredictedEmploymentIncome}.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class PredictedEmploymentInfo {

  @NotNull
  @DecimalMin("0")
  private BigDecimal base;
  @Min(0)
  private int        holidayDays;

  private BigDecimal predictedBonus;
  private BigDecimal knownBonus;

  @NotNull
  @DecimalMin("0")
  private BigDecimal salarySacrifice;
  @NotNull
  @DecimalMin("0")
  private BigDecimal payrollGiving;

  private List<DefaultBenefit> benefits = Collections.emptyList();
  private List<DefaultExpense> expenses = Collections.emptyList();


  /**
   * Get the base salary amount.
   *
   * @return the amount
   */
  public BigDecimal getBase() {
    return base;
  }

  /**
   * Set the base salary amount.
   *
   * @param base the amount
   */
  public void setBase(final BigDecimal base) {
    this.base = base;
  }

  /**
   * Get the number of holiday days bought/sold.
   *
   * @return the number of days
   */
  public int getHolidayDays() {
    return holidayDays;
  }

  /**
   * Set the number of holiday days bought/sold.
   *
   * @param holidayDays the number of days, positive if bought, negative if sold.
   */
  public void setHolidayDays(final int holidayDays) {
    this.holidayDays = holidayDays;
  }

  /**
   * The predicted bonus %age. A 9% bonus will come back as 0.09.
   *
   * @return the bonus %age
   */
  public BigDecimal getPredictedBonus() {
    return predictedBonus;
  }

  /**
   * Set the bonus prediction as a %age of salary. A 9% bonus should be entered as 0.09.
   *
   * @param predictedBonus the bonus %age
   */
  public void setPredictedBonus(final BigDecimal predictedBonus) {
    this.predictedBonus = predictedBonus;
  }

  /**
   * Get the known bonus as an amount - this can be used instead of {@link #getPredictedBonus()}.
   *
   * @return the amount.
   */
  public BigDecimal getKnownBonus() {
    return knownBonus;
  }

  /**
   * Set the known bonus as an amount e.g. 2000.25. The engine uses this value (if it exists) to
   * override the %age provided to {@link #setPredictedBonus(BigDecimal)}.
   *
   * @param knownBonus the known bonus amount
   */
  public void setKnownBonus(final BigDecimal knownBonus) {
    this.knownBonus = knownBonus;
  }

  /**
   * The salary sacrifice %age. A 9% salary sacrifice will come back as 0.09.
   *
   * @return the salary sacrifice %age
   */
  public BigDecimal getSalarySacrifice() {
    return salarySacrifice;
  }

  /**
   * Set the salary sacrifice as a %age of salary. A 9% salary sacrifice should be entered as 0.09.
   *
   * @param salarySacrifice the salary sacrifice %age
   */
  public void setSalarySacrifice(final BigDecimal salarySacrifice) {
    this.salarySacrifice = salarySacrifice;
  }

  /**
   * Get the amount of pre-tax pay given to charity annually (via a GAYE arrangement).
   *
   * @return the amount
   */
  public BigDecimal getPayrollGiving() {
    return payrollGiving;
  }

  /**
   * Set the amount of pre-tax pay given to charity annually (via a GAYE arrangement). This should
   * be entered as the amount itself e.g. 2000.00.
   *
   * @return the amount
   */
  public void setPayrollGiving(final BigDecimal payrollGiving) {
    this.payrollGiving = payrollGiving;
  }

  /**
   * Get the list of benefits associated with the employment.
   *
   * @return the benefits.
   */
  public List<DefaultBenefit> getBenefits() {
    return Collections.unmodifiableList(benefits);
  }

  /**
   * Set the list of benefits associated with the employment. This will entirely replace any
   * benefits that have been previously set.
   *
   * @param benefits the benefits.
   */
  public void setBenefits(final List<DefaultBenefit> benefits) {
    this.benefits = Collections.unmodifiableList(benefits);
  }

  /**
   * Get the list of expenses associated with the employment.
   *
   * @return the expenses.
   */
  public List<DefaultExpense> getExpenses() {
    return Collections.unmodifiableList(expenses);
  }


  /**
   * Set the list of expenses associated with the employment. This will entirely replace any
   * expenses that have been previously set.
   *
   * @param expenses the expenses.
   */
  public void setExpenses(final List<DefaultExpense> expenses) {
    this.expenses = Collections.unmodifiableList(expenses);
  }

}
