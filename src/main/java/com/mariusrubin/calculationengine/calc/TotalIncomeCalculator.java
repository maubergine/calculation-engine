package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;

import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Naïvely adds up income amounts, without adjusting them etc. (this is left to other calculators).
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class TotalIncomeCalculator {

  /**
   * Calculate the total income for all incomes of the given types for the given taxpayer.
   *
   * @param taxPayer the taxpayer
   * @param ofType   the types of income to add up
   * @return the total
   */
  public BigDecimal calculate(final TaxPayer taxPayer, final IncomeType... ofType) {
    return calculate(taxPayer.incomes(ofType));
  }

  /**
   * Calculate the total amount of all incomes for a given taxpayer.
   *
   * @param taxPayer the taxpayer.
   * @return the total
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {
    return calculate(taxPayer.incomes());
  }

  /**
   * Calculate the total amount of the given set of incomes.
   *
   * @param incomes the incomes
   * @return the total
   */
  public BigDecimal calculate(final Collection<Income> incomes) {
    return incomes.stream().map(Income::amount)
                  .map(TaxMathUtils::roundDownInt)
                  .reduce(BigDecimal::add)
                  .orElse(ZERO);
  }

}