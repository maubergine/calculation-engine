package com.mariusrubin.calculationengine.model.calc;

import com.mariusrubin.calculationengine.api.calc.BasicRateAdjustmentCalc;
import java.math.BigDecimal;

/**
 * Simple default implementation for {@link BasicRateAdjustmentCalc}.
 *
 * @param giftAidPayments the gift aid payments amount
 * @param pensionPayments the pension payments amount
 * @param total           the total
 * @author Marius Rubin
 * @since 0.1.0
 */
public record DefaultBasicRateAdjustmentCalc(BigDecimal giftAidPayments,
                                             BigDecimal pensionPayments,
                                             BigDecimal total) implements
                                                               BasicRateAdjustmentCalc {

}
