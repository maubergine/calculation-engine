package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.max;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.min;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.roundUpInt;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.twoDec;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.UkTaxRates.PersonalAllowanceRates;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.PersonalAllowanceCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultPersonalAllowanceCalc;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;

/**
 * Calculates the personal allowance, which is tapered based on income.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class PersonalAllowanceCalculator {

  private final AdjustedNetIncomeCalculator adjustedNetIncomeCalculator = new AdjustedNetIncomeCalculator();
  private final RarTotalCalculator          rarTotalCalculator          = new RarTotalCalculator();

  /**
   * Calculate the personal allowance.
   *
   * @param taxPayer the taxpayer
   * @param rates    the rates applicable
   * @return the allowance calculation
   */
  public PersonalAllowanceCalc calculate(final TaxPayer taxPayer,
                                         final UkTaxRates rates) {

    return calculate(adjustedNetIncomeCalculator.calculate(taxPayer, rates),
                     rarTotalCalculator.calculate(taxPayer),
                     rates.personalAllowanceRates());

  }

  /**
   * Calculate the personal allowance.
   *
   * @param adjustedNetIncome adjusted net income
   * @param paRates           personal allowance rates
   * @return the calculation
   */
  public DefaultPersonalAllowanceCalc calculate(final BigDecimal adjustedNetIncome,
                                                final BigDecimal contributionsToRars,
                                                final PersonalAllowanceRates paRates) {

    final var allowance = twoDec(paRates.amount());

    final var adjustedNetIncomeAfterRar = adjustedNetIncome.subtract(contributionsToRars);

    final var amountOverThreshold = max(TaxMathUtils.ZERO,
                                        adjustedNetIncomeAfterRar.subtract(paRates.threshold()));

    if (amountOverThreshold.compareTo(TaxMathUtils.ZERO) == 0) {
      return new DefaultPersonalAllowanceCalc(allowance, TaxMathUtils.ZERO, TaxMathUtils.ZERO);
    }

    final var taperAmount = twoDec(min(amountOverThreshold.multiply(paRates.taperRate()),
                                       paRates.amount()));

    final var allowanceRecalc = roundUpInt(TaxMathUtils.max(TaxMathUtils.ZERO,
                                                            allowance.subtract(taperAmount)));

    return new DefaultPersonalAllowanceCalc(allowanceRecalc, amountOverThreshold, taperAmount);

  }


}