package com.mariusrubin.calculationengine.model.calc;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.BasicRateAdjustmentCalc;
import com.mariusrubin.calculationengine.api.calc.IncomeCalc;
import com.mariusrubin.calculationengine.api.calc.IncomeTaxCalc;
import com.mariusrubin.calculationengine.api.calc.PaymentDueCalc;
import com.mariusrubin.calculationengine.api.calc.PensionCalc;
import com.mariusrubin.calculationengine.api.calc.PersonalAllowanceCalc;
import com.mariusrubin.calculationengine.api.calc.TaxCalc;

/**
 * Simple default implementation of {@link TaxCalc}.
 *
 * @param income              the income calculation
 * @param pension             the pension calculation
 * @param incomeTax           the income tax calculation
 * @param paymentDue          the payment due calculation
 * @param personalAllowance   the personal allowance calculation
 * @param basicRateAdjustment the basic rate adjustment calculation
 * @param taxPayer            the taxpayer i.e. the information provided as input
 * @param ukTaxRates          the tax rates used in the calculation
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultTaxCalc(IncomeCalc income,
                             PensionCalc pension,
                             IncomeTaxCalc incomeTax,
                             PaymentDueCalc paymentDue,
                             PersonalAllowanceCalc personalAllowance,
                             BasicRateAdjustmentCalc basicRateAdjustment,
                             TaxPayer taxPayer,
                             UkTaxRates ukTaxRates) implements TaxCalc {

}
