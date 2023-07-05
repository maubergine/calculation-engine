package com.mariusrubin.calculationengine.model.calc;

import com.mariusrubin.calculationengine.api.calc.PersonalAllowanceCalc;
import java.math.BigDecimal;

/**
 * Simple default implementation of the {@link PersonalAllowanceCalc}.
 *
 * @param allowance           the final allowance
 * @param incomeOverThreshold the amount of income over the personal allowance threshold
 * @param taperAmount         the amount by which the allowance has been tapered
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultPersonalAllowanceCalc(BigDecimal allowance,
                                           BigDecimal incomeOverThreshold,
                                           BigDecimal taperAmount) implements
                                                                   PersonalAllowanceCalc {

}
