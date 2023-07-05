package com.mariusrubin.calculationengine.model;

import com.mariusrubin.calculationengine.api.Gift;
import java.math.BigDecimal;

/**
 * Simple default implementation of {@link Gift} making it a positive amount.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class DefaultGift extends AbstractKnownPositiveAmount implements Gift {

  /**
   * Default constructor needed for deserialisation.
   */
  public DefaultGift() {
  }

  /**
   * Build a gift.
   *
   * @param amount the amount
   */
  public DefaultGift(final BigDecimal amount) {
    super(amount);
  }

  @Override
  public BigDecimal amount() {
    return getAmount();
  }

}
