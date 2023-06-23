package com.mariusrubin.calculationengine.model.calc;

import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.Rate;
import com.mariusrubin.calculationengine.api.calc.TaxedAmount;
import java.math.BigDecimal;

/**
 * Simple default implementation of {@link TaxedAmount}.
 *
 * @param amount     the amount that has been taxed
 * @param tax        the amount of tax applied
 * @param incomeType the type of income that has been taxed
 * @param rate       the rate at which the income has been taxed
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultTaxedAmount(BigDecimal amount,
                                 BigDecimal tax,
                                 IncomeType incomeType,
                                 Rate rate) implements TaxedAmount {

}
