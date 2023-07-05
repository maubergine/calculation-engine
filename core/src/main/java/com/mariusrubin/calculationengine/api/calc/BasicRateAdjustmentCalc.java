package com.mariusrubin.calculationengine.api.calc;

import java.math.BigDecimal;

/**
 * Wraps the result of calculating adjustments to be applied to the basic rate.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface BasicRateAdjustmentCalc {

  /**
   * The amount of the adjustment made for gift aid payments.
   *
   * @return the amount
   */
  BigDecimal giftAidPayments();

  /**
   * The amount of the adjustment made for pension payments.
   *
   * @return the amount
   */
  BigDecimal pensionPayments();

  /**
   * The total adjustment amount.
   *
   * @return the amount
   */
  BigDecimal total();

}
