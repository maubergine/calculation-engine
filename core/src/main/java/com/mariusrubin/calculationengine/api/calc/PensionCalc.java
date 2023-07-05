package com.mariusrubin.calculationengine.api.calc;

import java.math.BigDecimal;

/**
 * Wraps the various outputs of the pension calculations.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public interface PensionCalc {

  /**
   * The base allowance someone is entitled to, this varies based on their income.
   *
   * @return the amount
   */
  BigDecimal baseAllowance();

  /**
   * The relevant
   * <a href="https://www.gov.uk/hmrc-internal-manuals/pensions-tax-manual/ptm044100#earnings">
   * earnings that attract tax relief</a>.
   *
   * @return the amount
   */
  BigDecimal relevantEarnings();

  /**
   * The adjusted income per
   * <a
   * href="https://www.gov.uk/guidance/pension-schemes-work-out-your-tapered-annual-allowance#adjusted">HMRC
   * * guidance</a>.
   *
   * @return the amount
   */
  BigDecimal adjustedIncome();

  /**
   * The threshold income
   * <a
   * href="https://www.gov.uk/guidance/pension-schemes-work-out-your-tapered-annual-allowance#threshold">
   * HMRC guidance</a>.
   */
  BigDecimal thresholdIncome();

  /**
   * The amount of taper that has been applied to the base allowance in high-earning scenarios.
   *
   * @return the amount
   */
  BigDecimal allowanceTaperAmount();

  /**
   * The pension allowance after tapering has been applied.
   *
   * @return the amount
   */
  BigDecimal allowance();

}
