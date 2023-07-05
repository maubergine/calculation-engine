package com.mariusrubin.calculationengine.model;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.twoDec;

import com.mariusrubin.calculationengine.api.Benefit;
import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.Expense;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.serde.PredictedEmploymentInfo;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Used for when employment income is not yet known and has variable components (e.g. bonus). Has
 * a number of features for projecting income where there is some degree of flex (e.g. for
 * holiday).
 * <br>
 * <br>
 * To enable better separation of model object for serialisation/deserialisation from some of the
 * interface implementation logic, this class wraps {@link PredictedEmploymentInfo}, which provides
 * the
 * raw information.
 * <br>
 * <br>
 * Where income is known because it is in the past, then this can be declared using
 * {@link KnownEmploymentIncome}.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class PredictedEmploymentIncome implements EmploymentIncome {

  private final PredictedEmploymentInfo info;

  public PredictedEmploymentIncome(final PredictedEmploymentInfo info) {
    this.info = info;
  }

  @Override
  public BigDecimal amount() {

    //Prioritise known bonus ahead of predicted bonus.
    final var bonus = info.getKnownBonus() == null
                      ? info.getPredictedBonus() == null
                        ? BigDecimal.ZERO
                        : info.getPredictedBonus()
                              .multiply(info.getBase())
                              .setScale(2, RoundingMode.HALF_UP)
                      : info.getKnownBonus();

    final var baseAfterHoliday = info.getBase()
                                     .setScale(2, RoundingMode.HALF_UP)
                                     .divide(TaxMathUtils.WORKING_DAYS, RoundingMode.HALF_UP)
                                     .multiply(new BigDecimal(info.getHolidayDays()))
                                     .negate()
                                     .add(info.getBase());

    return baseAfterHoliday.add(bonus).subtract(salarySacrifice()).subtract(payrollGiving());

  }

  @Override
  public IncomeType type() {
    return IncomeType.EMPLOYMENT;
  }

  @Override
  public List<Benefit> benefits() {
    return info.getBenefits().stream().map(Benefit.class::cast).toList();
  }

  @Override
  public List<Expense> expenses() {
    return info.getExpenses().stream().map(Expense.class::cast).toList();
  }

  @Override
  public BigDecimal salarySacrifice() {
    final var toMultiply = info.getSalarySacrifice();

    if (toMultiply == null) {
      return BigDecimal.ZERO;
    }

    return twoDec(toMultiply.multiply(info.getBase()));

  }

  private BigDecimal payrollGiving() {
    return info.getPayrollGiving() == null
           ? TaxMathUtils.ZERO
           : info.getPayrollGiving();

  }
}
