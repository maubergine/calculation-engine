package com.mariusrubin.calculationengine.model.calc;

import com.mariusrubin.calculationengine.api.calc.IncomeCalc;
import java.math.BigDecimal;

/**
 * Simple default implementation for {@link IncomeCalc}.
 *
 * @param netIncome                the net income
 * @param adjustedNetIncome        the adjusted net income
 * @param totalEmploymentPay       the total employment pay
 * @param totalBenefitsAndExpenses the total benefits and expenses
 * @param totalAllowableExpenses   the total allowable expenses
 * @param totalFromAllEmployments  the total from all employments
 * @param totalDividends           the total dividends
 * @param totalInterest            the total interest
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultIncomeCalc(BigDecimal netIncome,
                                BigDecimal adjustedNetIncome,
                                BigDecimal totalEmploymentPay,
                                BigDecimal totalBenefitsAndExpenses,
                                BigDecimal totalAllowableExpenses,
                                BigDecimal totalFromAllEmployments,
                                BigDecimal totalDividends,
                                BigDecimal totalInterest) implements IncomeCalc {

}
