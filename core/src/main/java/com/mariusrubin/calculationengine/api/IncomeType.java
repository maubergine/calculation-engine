package com.mariusrubin.calculationengine.api;

/**
 * The various types of income that this calculator can handle. For the purposes of taxation this
 * also includes items that (effectively) end up being treated as income
 * (e.g. {@link #PENSION_CHARGE}), or are synthetic and required in order to do calculations
 * (e.g. {@link #TOTAL}).
 *
 * <br>
 * <br>
 *
 * This list is not exhaustive (e.g. it is missing pension income, various state benefits etc.) -
 * because the calculator itself does not handle every scenario.
 *
 * <br>
 * <br>
 *
 * ⚠️WARNING! ⚠️
 * <br>
 * Taxes are applied according to the
 * <a href="https://techzone.abrdn.com/public/personal-taxation/intro-guide-income-tax">order of
 * taxation</a>. The declarations in this enum are deliberately ordered according to this - and the
 * {@link com.mariusrubin.calculationengine.calc.IncomeTaxCalculator IncomeTaxCalculator} relies
 * upon this ordering to correctly calculate tax amounts.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public enum IncomeType {

  EMPLOYMENT,
  PENSION,
  INTEREST,
  PENSION_CHARGE,
  DIVIDENDS,
  TOTAL

}
