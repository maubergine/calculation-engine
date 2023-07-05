package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.IncomeType;

/**
 * Dividend income (with positivity via the {@link AbstractKnownIncome}.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class DividendIncome extends AbstractKnownIncome {

  @Override
  public IncomeType type() {
    return IncomeType.DIVIDENDS;
  }

}
