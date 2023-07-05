package com.mariusrubin.calculationengine.calc.pension;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.lessThan;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.lessThanOrEqual;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.max;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.min;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.positive;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.twoDec;
import static java.math.RoundingMode.HALF_UP;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.UkTaxRates.PensionRates;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.PensionCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultPensionCalc;
import java.math.BigDecimal;

/**
 * Calculates someone's pension allowance after taking account of relevant earnings, any
 * carry-forward, applicable thresholds etc.
 * <br>
 * <br>
 * This is a complex area so to understand it better you are best off looking at
 * <a href="https://www.gov.uk/guidance/pension-schemes-work-out-your-tapered-annual-allowance">
 * HMRC guidance</a>.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class PensionAllowanceCalculator {

  private final RelevantEarningsCalculator       relevantEarningsCalculator       = new RelevantEarningsCalculator();
  private final PensionThresholdIncomeCalculator pensionThresholdIncomeCalculator = new PensionThresholdIncomeCalculator();
  private final AdjustedIncomeCalculator         adjustedIncomeCalculator         = new AdjustedIncomeCalculator();

  /**
   * Calculate the pension allowance
   *
   * @param taxPayer the taxpayer
   * @param rates    the year's tax rates to be used to calculate the allowance
   * @return the pension allowance calculation
   */
  public PensionCalc calculate(final TaxPayer taxPayer, final UkTaxRates rates) {

    final var totalRelevantEarnings = relevantEarningsCalculator.calculate(taxPayer);
    final var thresholdIncome       = pensionThresholdIncomeCalculator.calculate(taxPayer, rates);
    final var adjustedIncome        = adjustedIncomeCalculator.calculate(taxPayer);
    final var carryForward          = taxPayer.pensionAllowanceCarryForward();

    //TODO cope with the employer adding back NI scenario.
    return calculate(rates, totalRelevantEarnings, thresholdIncome, adjustedIncome, carryForward);

  }

  /**
   * @param rates                 the year's tax rates to be used to calculate the allowance
   * @param totalRelevantEarnings the taxpayer's total relevant earnings
   * @param thresholdIncome       the taxpayer's threshold income
   * @param adjustedIncome        the taxpayer's adjusted income
   * @param carryForward          any carry-forward being consumed
   * @return the pension allowance calculation
   */
  public PensionCalc calculate(final UkTaxRates rates,
                               final BigDecimal totalRelevantEarnings,
                               final BigDecimal thresholdIncome,
                               final BigDecimal adjustedIncome,
                               final BigDecimal carryForward) {

    final var pRates = rates.pensionRates();

    final BigDecimal baseAllowance = calculateBaseAllowance(pRates, totalRelevantEarnings);

    //Work out how much taper needs to be applied (can be zero).
    final var uncappedTaper = calculateTaper(pRates, thresholdIncome, adjustedIncome);

    //Apply the tapered allowance as far as the minimum.
    final var taperedAllowance = max(pRates.minimumAllowance(),
                                     baseAllowance.subtract(uncappedTaper)).setScale(2, HALF_UP);

    final var taperAmount = positive(uncappedTaper)
                            ? baseAllowance.subtract(taperedAllowance)
                            : ZERO;

    //Add carry-forward, capping at relevant earnings, but not allowing a drop below minimum.
    final var actualAllowance = max(min(taperedAllowance.add(carryForward), totalRelevantEarnings),
                                    twoDec(pRates.earningsLowerLimit()));

    //Complete the calculation, adding back any carry-forward.
    return new DefaultPensionCalc(baseAllowance,
                                  totalRelevantEarnings,
                                  thresholdIncome,
                                  adjustedIncome,
                                  taperAmount,
                                  actualAllowance);

  }

  private static BigDecimal calculateTaper(final PensionRates rate,
                                           final BigDecimal thresholdIncome,
                                           final BigDecimal adjustedIncome) {

    final var taperAmount =
        lessThan(thresholdIncome, rate.thresholdIncome()) ||
        lessThanOrEqual(adjustedIncome, rate.adjustedIncome())
        ? ZERO
        : rate.taperRate().multiply(adjustedIncome.subtract(rate.adjustedIncome()));

    return twoDec(taperAmount);

  }


  private static BigDecimal calculateBaseAllowance(final PensionRates rate,
                                                   final BigDecimal totalRelevantEarnings) {

    final var baseAllowance =
        lessThan(totalRelevantEarnings, rate.earningsLowerLimit())
        ? rate.earningsLowerLimit()
        : min(totalRelevantEarnings, rate.annualAllowance());

    return twoDec(baseAllowance);

  }
}