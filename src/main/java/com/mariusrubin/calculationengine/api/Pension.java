package com.mariusrubin.calculationengine.api;

import java.math.BigDecimal;

/**
 * An instance of payment into a pension. This can be SIPP, employer etc. depending on the
 * {@link #type()}.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface Pension {

  /**
   * The amount paid into the pension.
   *
   * @return the amount
   */
  BigDecimal amount();

  /**
   * Whether or not the amount paid gets grossed up by the pension scheme. This is typically the
   * case for a SIPP, but not the case for things like salary sacrifice or employer contributions.
   *
   * @return true if the amount gets grossed up by the provider
   */
  default boolean isGrossedUp() {
    return false;
  }

  /**
   * The tyoe of the pension.
   *
   * @return the type
   */
  PensionType type();

}
