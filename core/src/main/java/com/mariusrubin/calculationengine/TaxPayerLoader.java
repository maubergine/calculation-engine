package com.mariusrubin.calculationengine;

import com.mariusrubin.calculationengine.api.TaxPayer;

/**
 * Interface for any classes that are capable of yielding a {@link TaxPayer} that can be used for
 * calculation. How these classes do this is immaterial (they could load it from a file, generate it
 * via stub code etc.).
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface TaxPayerLoader {

  /**
   * Get the taxpayer.
   *
   * @return the taxpayer
   */
  TaxPayer taxPayer();

}
