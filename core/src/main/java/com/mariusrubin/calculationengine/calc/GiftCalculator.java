package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.roundUpInt;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.sum;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.Gift;
import com.mariusrubin.calculationengine.api.TaxPayer;
import java.math.BigDecimal;

/**
 * Calculates the value of gifts which qualify for gift aid. On Self Assessment you just provide the
 * total amount. This is designed to process multiple gifts, add them together, round them UP to the
 * nearest whole number (which HMRC do), and then gross them up.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class GiftCalculator {

  private final GrossUpper grossUpper = new GrossUpper();


  /**
   * Calculate the gift amount.
   *
   * @param payer the taxpayer
   * @param rates the tax rates in the year
   * @return the total gift amount
   */
  public BigDecimal calculate(final TaxPayer payer, final UkTaxRates rates) {
    final var totalGifts = roundUpInt(sum(payer.gifts().stream().map(Gift::amount)));
    return roundUpInt(grossUpper.grossUp(totalGifts, rates));
  }

}