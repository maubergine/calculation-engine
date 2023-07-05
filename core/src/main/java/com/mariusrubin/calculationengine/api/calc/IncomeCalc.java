package com.mariusrubin.calculationengine.api.calc;

import java.math.BigDecimal;

/**
 * Income is summed up and rounded in various ways in order to calculate taxes. This wraps the
 * various calculated amounts used for <i>input</i> to the calculations.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface IncomeCalc {

  /**
   * The net income (sometimes referred to in HMRC docs as total income).
   *
   * @return the amount
   */
  BigDecimal netIncome();

  /**
   * The <a href="https://www.gov.uk/guidance/adjusted-net-income">adjusted net income</a>
   *
   * @return the amount
   */
  BigDecimal adjustedNetIncome();

  /**
   * The total amount of pay from employments - note this does not include benefits/expenses.
   *
   * @return the amount
   */
  BigDecimal totalEmploymentPay();

  /**
   * The total amount of benefits and (unallowable) expenses.
   *
   * @return the amount
   */
  BigDecimal totalBenefitsAndExpenses();

  /**
   * The total amount of allowable expenses.
   *
   * @return the amount
   */
  BigDecimal totalAllowableExpenses();

  /**
   * The total from all employments after adjusting for benefits and expenses.
   *
   * @return the amount
   */
  BigDecimal totalFromAllEmployments();

  /**
   * The total amount of income from dividends.
   *
   * @return the amount
   */
  BigDecimal totalDividends();

  /**
   * The total income from interest.
   *
   * @return the amount.
   */
  BigDecimal totalInterest();

}
