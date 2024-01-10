package com.mariusrubin.calculationengine.api.calc;

import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.RateLevel;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * This is a wrapper for the <i>outputs</i> of the income tax calculations. These make heavy use of
 * {@link TaxedAmount} to combine both the amount that has been taxed and the tax amount itself.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface IncomeTaxCalc {

  /**
   * The total amount of tax across all incomes.
   *
   * @return the total taxed amount
   */
  TaxedAmount total();

  /**
   * A breakdown of the tax applied i.e. shows the amount of tax applied to every type of income,
   * and (where there are multiple marginal amounts) how much tax has been applied at each rate.
   * <br>
   * <br>
   * Where there are tax free allowances that are applied on a marginal basis (e.g. for dividends),
   * these are included as the "nil" {@link RateLevel}.
   * <br>
   * <br>
   * Where no tax has been applied (e.g. because there was no income of a given type), then there
   * will be no corresponding taxed amount in the collection.
   *
   * @return all tax amounts
   */
  Collection<TaxedAmount> breakDown();

  /**
   * Retrieves the tax applied for a given type of income at the given level.
   * Where there is a taxed amount this will be the same as the one included in the
   * {@link #breakDown()}.
   *
   * @param type      the type of income for which to find the taxed amount
   * @param rateLevel the amount applied at the given rate
   * @return the taxed amount corresponding to the query, else a synthetic zeroed taxed amount. This
   * will never be null
   */
  TaxedAmount taxOn(final IncomeType type, final RateLevel rateLevel);

  /**
   * Totals the tax applied across various income types.
   * <br>
   * <br>
   * The resulting taxed amount will work out the <i>effective</i> tax rate applied where more
   * than one rate has been combined.
   *
   * @param types the types of income
   * @return the total amount, else a synthetic zeroed taxed amount. This will never be null.
   */
  TaxedAmount totalTaxOn(final IncomeType... types);

  /**
   * Totals the tax applied across all income types <i>except</i> for
   * those excluded.
   * <br>
   * <br>
   * The resulting taxed amount will work out the <i>effective</i> tax rate applied where more
   * than one rate has been combined.
   *
   * @param toExclude the types of income not to include in the calculation
   * @return the total amount, else a synthetic zeroed taxed amount. This will never be null.
   */
  TaxedAmount totalTaxExcluding(final IncomeType... toExclude);

  /**
   * The basic rate limit (which varies after allowances for gifts, pension contributions etc.).
   *
   * @return the amount
   */
  BigDecimal basicRateLimit();

  /**
   * Total allowance applied i.e. the amount by which income is treated as reduced by a combination
   * of personal allowance and contribution to annuities etc.
   */
  BigDecimal allowanceApplied();

}
