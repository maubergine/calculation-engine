package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.sum;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.PensionType;
import com.mariusrubin.calculationengine.api.TaxPayer;
import java.math.BigDecimal;

/**
 * Calculates the total amount put in SIPPs, grossing up as necessary.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class TotalSippCalculator {

  private final GrossUpper grossUpper = new GrossUpper();

  /**
   * Calculate the total SIPP amount.
   *
   * @param payer the taxpayer
   * @param rate  the tax rates
   * @return the amount
   */
  public BigDecimal calculate(final TaxPayer payer, final UkTaxRates rate) {
    return sum(payer.pensions()
                    .stream()
                    .filter(p -> p.type() == PensionType.SIPP)
                    .map(p -> grossUp(p, rate)));
  }

  private BigDecimal grossUp(final Pension pension, final UkTaxRates rates) {
    return pension.isGrossedUp() ? pension.amount() : grossUpper.grossUp(pension.amount(), rates);
  }

}