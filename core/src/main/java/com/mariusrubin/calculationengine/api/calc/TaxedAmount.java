package com.mariusrubin.calculationengine.api.calc;

import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.Rate;
import java.math.BigDecimal;

/**
 * Describes an instance of tax (or nil-rated tax) being applied to a given amount of a type of
 * income at a given rate.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface TaxedAmount {

  /**
   * The amount which has been taxed.
   *
   * @return the amount
   */
  BigDecimal amount();

  /**
   * The amount of tax that has been applied.
   *
   * @return the amount
   */
  BigDecimal tax();

  /**
   * The type of income that has been taxed (which affects the rate, any allowances etc.).
   *
   * @return the type
   */
  IncomeType incomeType();

  /**
   * The rate that has been applied (including nil-rates).
   *
   * @return the rate
   */
  Rate rate();

}
