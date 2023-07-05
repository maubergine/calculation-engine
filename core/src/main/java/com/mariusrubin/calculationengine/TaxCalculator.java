package com.mariusrubin.calculationengine;

import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.TaxCalc;

/**
 * Top-level interface for implementations of the engine.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface TaxCalculator {

  /**
   * Calculate the tax that should apply to the taxpayer.
   *
   * @param payer the taxpayer information
   */
  TaxCalc calculate(final TaxPayer payer);

}
