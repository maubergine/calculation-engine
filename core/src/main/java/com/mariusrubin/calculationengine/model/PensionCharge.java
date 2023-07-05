package com.mariusrubin.calculationengine.model;

import static com.mariusrubin.calculationengine.api.IncomeType.PENSION_CHARGE;

import com.mariusrubin.calculationengine.api.IncomeType;
import java.math.BigDecimal;

/**
 * A pension charge (i.e. an amount which has been contributed to pension over the allowance.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class PensionCharge extends AbstractKnownIncome {

  /**
   * Build a pension charge
   *
   * @param amount the amount
   */
  public PensionCharge(final BigDecimal amount) {
    super(amount);
  }

  @Override
  public IncomeType type() {
    return PENSION_CHARGE;
  }

}
