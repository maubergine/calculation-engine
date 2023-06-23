package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.IncomeType;
import java.math.BigDecimal;

/**
 * Interest income (with positivity via the {@link AbstractKnownIncome}. This income is assumed to
 * require tax i.e. it is not tax-free interest income that may have been gained by an ISA.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class InterestIncome extends AbstractKnownIncome {

  /**
   * Build an instance of interest income.
   *
   * @param amount the amount
   */
  public InterestIncome(final BigDecimal amount) {
    super(amount);
  }

  @Override
  public IncomeType type() {
    return IncomeType.INTEREST;
  }

}
