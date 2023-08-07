package com.mariusrubin.calculationengine.api.calc;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.TaxPayer;

/**
 * Top-level wrapper class for all the calculations from the calculation engine.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface TaxCalc {

  /**
   * The calculated amounts for income e.g. sums of employment. Mostly used as inputs to the tax
   * calculations.
   *
   * @return the income calculation
   */
  IncomeCalc income();

  /**
   * The calculated outputs for the impact that pension has on tax.
   *
   * @return the pension calculation
   */
  PensionCalc pension();

  /**
   * The various taxes and allowances for all the types of income.
   *
   * @return the income tax calculation
   */
  IncomeTaxCalc incomeTax();

  /**
   * Details on what payments are due when and whether or not payments on account are due (in
   * addition to the balance).
   *
   * @return the payment due calculation
   */
  PaymentDueCalc paymentDue();

  /**
   * The calculated personal allowance (which can vary based on income).
   *
   * @return the personal allowance calculation
   */
  PersonalAllowanceCalc personalAllowance();

  /**
   * Any adjustments that are due to the basic rate of income tax based on pension contributions,
   * charitable donations etc.
   *
   * @return the basic rate adjustment calculation
   */
  BasicRateAdjustmentCalc basicRateAdjustment();

  /**
   * The taxpayer - i.e. the information that has been provided to the calculation engine.
   *
   * @return the taxpayer
   */
  TaxPayer taxPayer();

  /**
   * The tax rates that have been applied in this run of the calculation.
   *
   * @return the tax rates
   */
  UkTaxRates ukTaxRates();

}
