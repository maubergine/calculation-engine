package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.roundUpInt;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.BasicRateAdjustmentCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultBasicRateAdjustmentCalc;
import java.math.BigDecimal;

/**
 * Calculates adjustments to the size of someone's tax basic rate based on contributions to pensions
 * and any qualifying gifts.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class BasicRateAdjustmentCalculator {

  private final GiftCalculator      giftCalculator      = new GiftCalculator();
  private final TotalSippCalculator totalSippCalculator = new TotalSippCalculator();

  /**
   * Calculate the adjustment.
   *
   * @param payer the taxpayer
   * @param rates the tax rates applicable for the calculation
   * @return the adjustment calculation
   */
  public BasicRateAdjustmentCalc calculate(final TaxPayer payer, final UkTaxRates rates) {

    return calculate(giftCalculator.calculate(payer, rates),
                     totalSippCalculator.calculate(payer, rates));

  }

  /**
   * Calculate the adjustment.
   *
   * @param totalGifts   the total amount of qualifying gifts (after grossing up)
   * @param totalPension the total amount of pension contribution
   * @return the adjustment calculation
   */
  public BasicRateAdjustmentCalc calculate(final BigDecimal totalGifts,
                                           final BigDecimal totalPension) {

    //TODO need to deal with the limit for qualifying gift amounts being 4x the total tax paid
    final var totalAdjustment = totalGifts.add(roundUpInt(totalPension));
    return new DefaultBasicRateAdjustmentCalc(totalGifts, totalPension, totalAdjustment);

  }

}