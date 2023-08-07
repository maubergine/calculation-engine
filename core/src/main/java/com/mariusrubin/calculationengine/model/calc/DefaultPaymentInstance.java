package com.mariusrubin.calculationengine.model.calc;

import com.mariusrubin.calculationengine.api.calc.PaymentInstance;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Simple default implementation of {@link PaymentInstance}.
 *
 * @param amount the amount
 * @param date   the date the payment is due
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultPaymentInstance(BigDecimal amount, LocalDate date) implements PaymentInstance {

}
