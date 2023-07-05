package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.toPercent;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.twoDec;
import static java.math.BigDecimal.ONE;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.Rate;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Converts amounts to their grossed up amounts based on the UK basic rate of income tax.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class GrossUpper {

  /**
   * Gross up the provided amount based on applicable tax rates.
   *
   * @param amount the amount to be grossed up
   * @param rates  the tax rates to use in calculating the grossed-up amount
   * @return the grossed up amount
   */
  public BigDecimal grossUp(final BigDecimal amount, final UkTaxRates rates) {

    //TODO grossing up uses the basic rate of income tax. This does not handle Scotland where the
    //basic rate income tax is 19%, but the grossing up calculation for pensions continues to use
    //the UK rate of 20%.
    return grossUp(amount, rates.incomeTaxRates().basicRate());

  }

  /**
   * Gross up the provided amount based on the specific rate
   *
   * @param amount the amount to be grossed up
   * @param rate   the rates to use in calculating the grossed-up amount
   * @return the grossed up amount
   */
  public BigDecimal grossUp(final BigDecimal amount, final Rate rate) {

    final var divisor = twoDec(ONE).subtract(toPercent(rate));

    return twoDec(amount).divide(divisor, RoundingMode.HALF_UP);

  }


}
