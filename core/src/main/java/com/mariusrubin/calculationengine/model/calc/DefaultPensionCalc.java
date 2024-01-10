package com.mariusrubin.calculationengine.model.calc;

import com.mariusrubin.calculationengine.api.calc.PensionCalc;
import java.math.BigDecimal;

/**
 * Simple default implementation of {@link PensionCalc}.
 *
 * @param baseAllowance        the base allowance (i.e. before tapers etc. have been applied).
 * @param relevantEarnings     the relevant earnings used to calculate this
 * @param thresholdIncome      the calculated threshold income
 * @param adjustedIncome       the calculated adjusted income
 * @param allowanceTaperAmount the amount by which the allowance has been tapered
 * @param allowance            the allowance after tapering has been applied
 * @param contributionsToRars  the total amount of contributions made to annuity contracts
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultPensionCalc(BigDecimal baseAllowance,
                                 BigDecimal relevantEarnings,
                                 BigDecimal thresholdIncome,
                                 BigDecimal adjustedIncome,
                                 BigDecimal allowanceTaperAmount,
                                 BigDecimal allowance,
                                 BigDecimal contributionsToRars) implements PensionCalc {

}
