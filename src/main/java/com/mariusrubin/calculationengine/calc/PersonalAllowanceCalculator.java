package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.min;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.twoDec;
import static java.math.RoundingMode.HALF_UP;

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
                                                final PersonalAllowanceRates paRates) {

    final var allowance = twoDec(paRates.amount());

    final var amountOverThreshold = TaxMathUtils.max(TaxMathUtils.ZERO,
                                                     adjustedNetIncome.subtract(paRates.threshold()));

    if (amountOverThreshold.compareTo(TaxMathUtils.ZERO) == 0) {
      return new DefaultPersonalAllowanceCalc(allowance, TaxMathUtils.ZERO, TaxMathUtils.ZERO);
    }

    final var taperAmount = twoDec(min(amountOverThreshold.multiply(paRates.taperRate()),
                                       paRates.amount()));

    final var allowanceRecalc = TaxMathUtils.max(TaxMathUtils.ZERO, allowance.subtract(taperAmount))
                                            .setScale(2, HALF_UP);

    return new DefaultPersonalAllowanceCalc(allowanceRecalc, amountOverThreshold, taperAmount);

  }


}