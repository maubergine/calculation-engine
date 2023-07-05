package com.mariusrubin.calculationengine.api.calc;

import java.math.BigDecimal;

/**
 * This is a wrapper for the amounts due for payment, as well as transparently pulling through,
 * amounts supplied in the input.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface PaymentDueCalc {

  /**
   * The tax paid in the year so far - this will either be a calculated amount or an explicit
   * amount provided as part of the input.
   *
   * @return the amount
   */
  BigDecimal taxPaid();

  /**
   * The total tax amount due for payment.
   *
   * @return the amount
   */
  BigDecimal totalTax();

  /**
   * The percentage of income tax already paid through PAYE arrangements. This is important because
   * payments on account are due when this amount is less than 80%. This is provided as the actual
   * number to four decimal places e.g. 0.7592. If you want to display the number as a percentage
   * you would need to scale it up.
   *
   * @return the percentage
   */
  BigDecimal incomeTaxAsPercentage();

  /**
   * Returns true if your tax percentage means that payments on account are due.
   *
   * @return true if payments on account are due
   */
  boolean paymentsOnAccountDue();

  /**
   * The balance due. This will be the total tax amount - the amount of tax already paid - any
   * payments on account already made.
   *
   * @return the amount
   */
  PaymentInstance balance();

  /**
   * The amount of the first payment on account due. If no payment on account is due, then this will
   * be a zero payment instance.
   *
   * @return the amount
   */
  PaymentInstance firstPayment();

  /**
   * The amount of the second payment on account due. If no payment on account is due, then this
   * will
   * be a zero payment instance.
   *
   * @return the amount
   */
  PaymentInstance secondPayment();

  /**
   * The amount due in the January payment, this is the combination of the balance and the first
   * payment.
   *
   * @return the amount
   */
  PaymentInstance januaryTotal();

  /**
   * The total payments on account due.
   *
   * @return the amount
   */
  BigDecimal totalPaymentsOnAccount();

}
