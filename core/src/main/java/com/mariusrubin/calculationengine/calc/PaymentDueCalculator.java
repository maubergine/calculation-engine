package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.IncomeType.EMPLOYMENT;

import com.mariusrubin.calculationengine.FinancialYear;
import com.mariusrubin.calculationengine.api.calc.IncomeTaxCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultPaymentDueCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultPaymentInstance;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Calculates the amounts due to HMRC and when. These factor in tax already paid, tax due and any
 * <a href="https://www.gov.uk/understand-self-assessment-bill/payments-on-account"> payments on
 * account</a> that have been made or are due.
 * <br>
 * <br>
 * Payments on account are due when tax paid through PAYE (or similar) in a given year is <80% of
 * tax due. At this point HMRC require advance payment.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class PaymentDueCalculator {

  private static final BigDecimal POA_PERCENT_THRESHOLD = new BigDecimal("0.8");

  /**
   * Calculate the payments due.
   *
   * @param itc             income tax that has been calculated
   * @param paymentsMade    any payments made already
   * @param taxPaidOverride the amount of tax paid (which may differ from the default calculation)
   * @param fy              the financial year in which this calculation takes place
   * @return the payments due
   */
  public DefaultPaymentDueCalc calculate(final IncomeTaxCalc itc,
                                         final BigDecimal paymentsMade,
                                         final BigDecimal taxPaidOverride,
                                         final FinancialYear fy) {

    final var presumedTaxPaid = taxPaidOverride == null
                                ? itc.totalTaxOn(EMPLOYMENT).tax()
                                : taxPaidOverride;

    final var totalTax = itc.total().tax();

    final var incomeAsPercentage = presumedTaxPaid
        .setScale(4, RoundingMode.UNNECESSARY)
        .divide(itc.total().tax(), RoundingMode.HALF_UP);

    final var poaDue = TaxMathUtils.lessThanOrEqual(incomeAsPercentage, POA_PERCENT_THRESHOLD);

    final var balance = totalTax.subtract(presumedTaxPaid).subtract(paymentsMade);

    final BigDecimal poaFirstPayment;
    final BigDecimal poaSecondPayment;
    final BigDecimal poaTotal;

    if (poaDue) {
      poaTotal = totalTax.subtract(presumedTaxPaid);
      poaFirstPayment = poaTotal.divide(new BigDecimal(2), RoundingMode.HALF_DOWN);
      poaSecondPayment = poaTotal.subtract(poaFirstPayment);
    } else {
      poaTotal = TaxMathUtils.ZERO;
      poaFirstPayment = TaxMathUtils.ZERO;
      poaSecondPayment = TaxMathUtils.ZERO;
    }

    final var janTotal = balance.add(poaFirstPayment);

    final var janDate = LocalDate.of(fy.getEndDate().getYear() + 1, 1, 31);
    final var julDate = LocalDate.of(fy.getEndDate().getYear() + 1, 7, 31);

    return new DefaultPaymentDueCalc(presumedTaxPaid,
                                     totalTax,
                                     incomeAsPercentage,
                                     poaDue,
                                     new DefaultPaymentInstance(balance, janDate),
                                     new DefaultPaymentInstance(poaFirstPayment, janDate),
                                     new DefaultPaymentInstance(poaSecondPayment, julDate),
                                     new DefaultPaymentInstance(janTotal, janDate),
                                     poaTotal);

  }
}