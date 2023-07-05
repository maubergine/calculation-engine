package com.mariusrubin.calculationengine.api;

import java.math.BigDecimal;
import java.util.List;

/**
 * Income earned from employment, which in addition to having an income amount, has specific
 * additional sorts of earnings that need to be treated differently.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface EmploymentIncome extends Income {

  /**
   * The taxable benefits associated with this employment.
   *
   * @return the benefits
   */
  List<Benefit> benefits();

  /**
   * The allowable expenses associated with this employment.
   *
   * @return the expenses
   */
  List<Expense> expenses();

  /**
   * Any amount given away in the form of salary sacrifice (into a pension scheme).
   *
   * @return the amount
   */
  BigDecimal salarySacrifice();

}
