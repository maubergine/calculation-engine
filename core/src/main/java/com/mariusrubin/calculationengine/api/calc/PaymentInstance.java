package com.mariusrubin.calculationengine.api.calc;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a specific amount that is due for payment on a given date.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface PaymentInstance {

  /**
   * The date the payment is due.
   *
   * @return the date
   */
  LocalDate date();

  /**
   * The amount due.
   *
   * @return the amount
   */
  BigDecimal amount();

}
