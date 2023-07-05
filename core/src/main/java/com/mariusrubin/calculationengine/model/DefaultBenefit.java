package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.Benefit;
import java.math.BigDecimal;

/**
 * Simple default implementation of {@link Benefit}, making it a positive amount.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class DefaultBenefit extends AbstractKnownPositiveAmount implements Benefit {

  @Override
  public BigDecimal amount() {
    return getAmount();
  }
}
