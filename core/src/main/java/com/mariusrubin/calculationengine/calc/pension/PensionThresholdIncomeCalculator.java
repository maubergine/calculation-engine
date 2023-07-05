package com.mariusrubin.calculationengine.calc.pension;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.calc.AdjustedNetIncomeCalculator;
import com.mariusrubin.calculationengine.calc.SalarySacrificeCalculator;
import java.math.BigDecimal;

/**
 * Calculates threshold income as per
 * <a
 * href="https://www.gov.uk/guidance/pension-schemes-work-out-your-tapered-annual-allowance#threshold">HMRC
 * guidance.</a>
 *
 * <ol>
 * <li>Start with your net income for the tax year.</li>
 * <li>Deduct the gross amount of your pension contributions to all schemes where you had
 * ‘relief at source’. Relief at source usually applies to personal and stakeholder pension schemes,
 * and some workplace pension schemes. They are contributions made by you or someone else on your
 * behalf, but exclude contributions made by your employer.</li>
 * <li>Deduct the amount of any lump sum death benefits you received from registered pension
 * schemes.</li>
 * <li>Add any reduction of employment income for pension provision through any relevant salary
 * sacrifice arrangements made after 8 July 2015.</li>
 * <li>Add any reduction of employment income for pension provision through any relevant flexible
 * remuneration arrangements made after 8 July 2015.</li>
 * </ol>
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class PensionThresholdIncomeCalculator {

  private final AdjustedNetIncomeCalculator adjustedNetIncomeCalculator = new AdjustedNetIncomeCalculator();
  private final SalarySacrificeCalculator   salarySacrificeCalculator   = new SalarySacrificeCalculator();

  //TODO add concept of flexible remuneration arrangements, and lump sum death benefits.

  /**
   * Calculate the pension threshold.
   *
   * @param taxPayer the taxpayer
   * @param rates    the rates used for the calculation
   * @return the pension threshold amount
   */
  public BigDecimal calculate(final TaxPayer taxPayer, final UkTaxRates rates) {

    return calculate(adjustedNetIncomeCalculator.calculate(taxPayer, rates),
                     salarySacrificeCalculator.calculate(taxPayer));

  }

  /**
   * Calculate the pension threshold.
   *
   * @param adjustedNetIncome    the taxpayer's adjusted net income
   * @param totalSalarySacrifice the amount of salary sacrifice
   * @return the pension threshold amount
   */
  public BigDecimal calculate(final BigDecimal adjustedNetIncome,
                              final BigDecimal totalSalarySacrifice) {

    return adjustedNetIncome.add(totalSalarySacrifice);
  }

}