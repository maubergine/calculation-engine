package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.Benefit;
import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.Expense;
import com.mariusrubin.calculationengine.api.IncomeType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * "Known" employment income i.e. employment income where the details can be provided as input. This
 * is normally the case for income where someone has an associated P60 (and P11D) - or where someone
 * wants to provide their own amounts.
 * <br>
 * <br>
 * Where income is being predicted (useful for forward tax projections) this can be done using the
 * {@link PredictedEmploymentIncome} class.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class KnownEmploymentIncome extends AbstractKnownIncome implements EmploymentIncome {

  private BigDecimal           amount;
  private List<DefaultBenefit> benefits = Collections.emptyList();
  private List<DefaultExpense> expenses = Collections.emptyList();

  @Override
  public IncomeType type() {
    return IncomeType.EMPLOYMENT;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  public List<DefaultBenefit> getBenefits() {
    return Collections.unmodifiableList(benefits);
  }

  public void setBenefits(final List<DefaultBenefit> benefits) {
    this.benefits = Collections.unmodifiableList(benefits);
  }

  public List<DefaultExpense> getExpenses() {
    return Collections.unmodifiableList(expenses);
  }

  public void setExpenses(final List<DefaultExpense> expenses) {
    this.expenses = Collections.unmodifiableList(expenses);
  }

  @Override
  public BigDecimal amount() {
    return amount;
  }

  @Override
  public List<Benefit> benefits() {
    return benefits.stream().map(Benefit.class::cast).toList();
  }

  @Override
  public List<Expense> expenses() {
    return expenses.stream().map(Expense.class::cast).toList();
  }

  @Override
  public BigDecimal salarySacrifice() {
    return BigDecimal.ZERO;
  }

}
