package com.mariusrubin.calculationengine.calc.pension;

import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.calc.NetIncomeCalculator;
import com.mariusrubin.calculationengine.calc.SalarySacrificeCalculator;
import java.math.BigDecimal;

/**
 * Calculates adjusted income as per
 * <a
 * href="https://www.gov.uk/guidance/pension-schemes-work-out-your-tapered-annual-allowance#adjusted">HMRC
 * guidance.</a>
 *
 * <ol>
 * <li>Start with your net income for the tax year.</li>
 * <li>Add the amounts of claims made for tax relief on pension savings where they were paid before
 * tax relief was given.</li>
 * <li>Add pension savings made to your pension schemes where tax relief was given (because your
 * employer took them out of your pay before deducting Income Tax).</li>
 * <li>If you are a non-domicile individual (your permanent home is outside the UK), add any relief
 * claimed on pension savings you made to overseas pension schemes.</li>
 * <li>Add the amount of pension savings your employer made for you.</li>
 * <li>Deduct the amount of any lump sum death benefits you received from registered pension schemes.</li>
 * </ol>
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AdjustedIncomeCalculator {

  private final NetIncomeCalculator            netIncomeCalculator       = new NetIncomeCalculator();
  private final SalarySacrificeCalculator      salarySacrificeCalculator = new SalarySacrificeCalculator();
  private final TotalEmployerPensionCalculator employerPensionCalculator = new TotalEmployerPensionCalculator();

  //TODO lump sum death benefits, claims for tax relief on pension savings, non-dom relief on overseas pensions

  /**
   * Calculate the adjusted income.
   *
   * @param totalNetIncome              the total net income
   * @param totalSalarySacrifice        the total amount given to salary sacrifice
   * @param employerPensionContribution the total amount the employer has contributed to pension
   * @return the adjusted income
   */
  public BigDecimal calculate(final BigDecimal totalNetIncome,
                              final BigDecimal totalSalarySacrifice,
                              final BigDecimal employerPensionContribution) {

    //TODO cope with the employer adding back NI scenario.
    return totalNetIncome.add(totalSalarySacrifice).add(employerPensionContribution);
  }

  /**
   * Calculate the adjusted income
   *
   * @param taxPayer the taxpayer
   * @return the adjusted income
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {
    return calculate(netIncomeCalculator.calculate(taxPayer),
                     salarySacrificeCalculator.calculate(taxPayer),
                     employerPensionCalculator.calculate(taxPayer));

  }

}