package com.mariusrubin.calculationengine.calc;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.TaxPayer;
import java.math.BigDecimal;

/**
 * Calculates adjusted net income as per
 * <a href="https://www.gov.uk/guidance/adjusted-net-income">HMRC guidance.</a>
 *
 * <br/>
 * <br/>
 *
 * Adjusted net income is total taxable income before any Personal Allowances and less certain tax
 * reliefs, for example:
 * <ul>
 *     <li>trading losses</li>
 *     <li>donations made to charities through Gift Aid - take off the ‘grossed-up’ amount</li>
 *     <li>pension contributions paid gross (before tax relief)</li>
 *     <li>pension contributions where your pension provider has already given you tax relief at
 *     the basic rate - take off the ‘grossed-up’ amount</li>
 * </ul>
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AdjustedNetIncomeCalculator {

  private final NetIncomeCalculator netIncomeCalculator = new NetIncomeCalculator();
  private final TotalSippCalculator totalSippCalculator = new TotalSippCalculator();
  private final GiftCalculator      giftCalculator      = new GiftCalculator();

  /**
   * Calculate the adjusted net income.
   *
   * @param taxPayer the taxpayer
   * @param rates    the rates used to calculate the adjusted net income
   * @return the adjusted net income amount
   */
  public BigDecimal calculate(final TaxPayer taxPayer, final UkTaxRates rates) {
    return calculate(netIncomeCalculator.calculate(taxPayer),
                     totalSippCalculator.calculate(taxPayer, rates),
                     giftCalculator.calculate(taxPayer, rates));

  }

  //TODO include allowable trading losses

  /**
   * Calculate the adjusted net income
   *
   * @param totalTaxableIncome       the taxpayer's total taxable income
   * @param totalPensionContribution the taxpayer's total pension contribution
   * @param totalGifts               the taxpayer's total amount of qualifying gifts
   * @return the adjusted net income amount
   */
  public BigDecimal calculate(final BigDecimal totalTaxableIncome,
                              final BigDecimal totalPensionContribution,
                              final BigDecimal totalGifts) {
    return totalTaxableIncome.subtract(totalPensionContribution).subtract(totalGifts);
  }

}