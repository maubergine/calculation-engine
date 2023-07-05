package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.IncomeType.EMPLOYMENT;

import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.Expense;
import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Calculates the total amount of allowable expenses from all employment incomes.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AllowableExpenseCalculator {

  /**
   * Calculate the total allowable expenses.
   *
   * @param taxPayer the taxpayer
   * @return the total allowable expense amount
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {
    return calculate(taxPayer.incomes(EMPLOYMENT));
  }

  /**
   * Calculate the total allowable expenses.
   *
   * @param incomes a list of the taxpayer's incomes (of any type).
   * @return the total allowable expense amount
   */
  public BigDecimal calculate(final List<Income> incomes) {

    return incomes.stream()
                  .filter(inc -> inc.type() == EMPLOYMENT)
                  .map(EmploymentIncome.class::cast)
                  .map(EmploymentIncome::expenses)
                  .flatMap(Collection::stream)
                  .map(Expense::amount)
                  .map(TaxMathUtils::roundUpInt)
                  .reduce(BigDecimal::add)
                  .orElse(TaxMathUtils.ZERO);
  }

}