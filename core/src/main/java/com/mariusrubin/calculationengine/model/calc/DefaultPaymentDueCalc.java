package com.mariusrubin.calculationengine.model.calc;

import com.mariusrubin.calculationengine.api.calc.PaymentDueCalc;
import com.mariusrubin.calculationengine.api.calc.PaymentInstance;
import java.math.BigDecimal;

/**
 * Simple default implementation of {@link PaymentDueCalc}.
 *
 * @param taxPaid                the tax paid (calculated or overridden)
 * @param totalTax               the total tax
 * @param incomeTaxAsPercentage  income tax as a percentage of total tax
 * @param paymentsOnAccountDue   whether or not payments on account are due
 * @param balance                the balance amount
 * @param firstPayment           the first payment on account amount
 * @param secondPayment          the second payment on account amount
 * @param januaryTotal           the January total amount due
 * @param totalPaymentsOnAccount the total payments on account amount
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultPaymentDueCalc(BigDecimal taxPaid,
                                    BigDecimal totalTax,
                                    BigDecimal incomeTaxAsPercentage,
                                    boolean paymentsOnAccountDue,
                                    PaymentInstance balance,
                                    PaymentInstance firstPayment,
                                    PaymentInstance secondPayment,
                                    PaymentInstance januaryTotal,
                                    BigDecimal totalPaymentsOnAccount) implements PaymentDueCalc {

}