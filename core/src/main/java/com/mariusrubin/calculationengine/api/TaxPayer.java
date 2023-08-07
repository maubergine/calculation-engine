package com.mariusrubin.calculationengine.api;


import java.math.BigDecimal;
import java.util.List;

/**
 * Top-level object wrapping all information about the taxpayer <i>provided</i> to the engine. This
 * allows the engine to be decoupled from any specific representations based on file-type, API etc.
 * which may require specific model objects.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface TaxPayer {

  /**
   * The taxpayer's incomes (this would include all dividend income, employment income etc.).
   *
   * @return the incomes (or an empty list if they have none)
   */
  List<Income> incomes();

  /**
   * All the taxpayer's incomes of a given type.
   *
   * @param ofType the type of income to find
   * @return the incomes (or an empty list if they have none of the given type)
   */
  List<Income> incomes(final IncomeType... ofType);

  /**
   * The taxpayer's contributions to pensions
   *
   * @return the pensions (or an empty list if they have none)
   */
  List<Pension> pensions();

  /**
   * Any gifts made by the taxpayer that are relevant for tax purposes.
   *
   * @return the gifts (or an empty list if there are none)
   */
  List<Gift> gifts();

  /**
   * Any amount of pension allowance that has been carried forward from previous years (see <a
   * href="https://www.gov.uk/guidance/check-if-you-have-unused-annual-allowances-on-your-pension-savings">
   * HMRC guidance</a> for more information on this.
   *
   * <br>
   * <br>
   * The engine (currently) has no concept of
   * previous years' allowance, so it is up to the user to inform the engine about how much
   * carry-forward is being consumed. This is important in years when the contribution to pension
   * may exceed the default calculated allowance. Failure to inform the engine about additional
   * carry-forward will result in it calculating pension tax charges for contributions over the
   * allowance.
   *
   * @return the amount of carry-forward being used
   */
  BigDecimal pensionAllowanceCarryForward();

  /**
   * Any payments on account made in the course of the year. This is important when calculating
   * the balances due.
   *
   * @return the amount
   */
  BigDecimal paymentsMade();

  /**
   * An override for the amount of tax paid in the tax year (typically through PAYE arrangements).
   * This can be needed in the common scenario that HMRC/payroll have deducted the incorrect amount
   * of tax. The amount is usually found on an eP60. Providing this number overrides the engine's
   * default behaviour which is to assume that employment income has been correctly had tax deducted
   * by the employer.
   *
   * @return the amount
   */
  BigDecimal taxPaidOverride();

}
