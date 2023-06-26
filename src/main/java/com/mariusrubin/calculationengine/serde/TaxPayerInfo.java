package com.mariusrubin.calculationengine.serde;

import com.mariusrubin.calculationengine.model.DefaultGift;
import com.mariusrubin.calculationengine.model.DividendIncome;
import com.mariusrubin.calculationengine.model.KnownEmploymentIncome;
import com.mariusrubin.calculationengine.model.KnownPension;
import com.mariusrubin.calculationengine.model.PredictedEmployerPension;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Used to serialise/deserialise information about a taxpayer employment. This information is then
 * used by {@link com.mariusrubin.calculationengine.model.DefaultTaxPayer DefaultTaxPayer}.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class TaxPayerInfo {

  private List<PredictedEmploymentInfo>  predictedEmployments = Collections.emptyList();
  private List<KnownEmploymentIncome>    knownEmployments     = Collections.emptyList();
  private List<DividendIncome>           dividends            = Collections.emptyList();
  private List<PredictedEmployerPension> predictedPensions    = Collections.emptyList();
  private List<KnownPension>             knownPensions        = Collections.emptyList();
  private List<DefaultGift>              gifts                = Collections.emptyList();
  private BigDecimal                     untaxedInterest;
  private BigDecimal                     pensionAllowanceCarryForward;
  private BigDecimal                     paymentsMade;
  private BigDecimal                     taxPaidOverride;

  /**
   * Get the list of predicted employment information.
   *
   * @return the predicted employment info
   */
  public List<PredictedEmploymentInfo> getPredictedEmployments() {
    return Collections.unmodifiableList(predictedEmployments);
  }

  /**
   * Set the list of predicted employment information. Note this overwrites any previously set
   * info.
   *
   * @param predictedEmployments the predicted incomes
   */
  public void setPredictedEmployments(final List<PredictedEmploymentInfo> predictedEmployments) {
    this.predictedEmployments = Collections.unmodifiableList(predictedEmployments);
  }

  /**
   * Get the list of known employment income.
   *
   * @return the incomes
   */
  public List<KnownEmploymentIncome> getKnownEmployments() {
    return Collections.unmodifiableList(knownEmployments);
  }

  /**
   * Set the list of known employment incomes. Note this overwrites any previously set incomes.
   *
   * @param knownEmployments the employment incomes
   */
  public void setKnownEmployments(final List<KnownEmploymentIncome> knownEmployments) {
    this.knownEmployments = Collections.unmodifiableList(knownEmployments);
  }

  /**
   * Get the list of dividend incomes.
   *
   * @return the incomes
   */
  public List<DividendIncome> getDividends() {
    return Collections.unmodifiableList(dividends);
  }

  /**
   * Set the list of dividend incomes. Note this overwrites any previously set incomes.
   *
   * @param dividends the dividend incomes
   */
  public void setDividends(final List<DividendIncome> dividends) {
    this.dividends = Collections.unmodifiableList(dividends);
  }

  /**
   * Get the list of predicted pension contributions.
   *
   * @return the predicted pensions
   */
  public List<PredictedEmployerPension> getPredictedPensions() {
    return Collections.unmodifiableList(predictedPensions);
  }

  /**
   * Set the list of predicted pension contributions. Note this overwrites any previously set data.
   *
   * @param predictedPensions the dividend incomes
   */
  public void setPredictedPensions(final List<PredictedEmployerPension> predictedPensions) {
    this.predictedPensions = Collections.unmodifiableList(predictedPensions);
  }

  /**
   * Get the list of known pension contributions.
   *
   * @return the pension contributions
   */
  public List<KnownPension> getKnownPensions() {
    return Collections.unmodifiableList(knownPensions);
  }

  /**
   * Set the list of known pension contributions. Note this overwrites previously supplied data.
   *
   * @param knownPensions the contributions
   */
  public void setKnownPensions(final List<KnownPension> knownPensions) {
    this.knownPensions = Collections.unmodifiableList(knownPensions);
  }

  /**
   * Get the list of gifts.
   *
   * @return the gifts.
   */
  public List<DefaultGift> getGifts() {
    return Collections.unmodifiableList(gifts);
  }

  /**
   * Set the list of gifts. Note this overwrite the previously supplied data.
   *
   * @param gifts the gifts
   */
  public void setGifts(final List<DefaultGift> gifts) {
    this.gifts = Collections.unmodifiableList(gifts);
  }

  /**
   * Get the amount of untaxed interest in the year that should attract tax.
   *
   * @return the amount.
   */
  public BigDecimal getUntaxedInterest() {
    return untaxedInterest;
  }

  /**
   * Set the amount of untaxed interest in the year that should attract tax.
   *
   * @param untaxedInterest the amount
   */
  public void setUntaxedInterest(final BigDecimal untaxedInterest) {
    this.untaxedInterest = untaxedInterest;
  }

  /**
   * Get the amount of pension allowance being carried forward.
   *
   * @return the amount
   */
  public BigDecimal getPensionAllowanceCarryForward() {
    return pensionAllowanceCarryForward;
  }

  /**
   * Set the amount of pension allowance being carried forward.
   *
   * @param pensionAllowanceCarryForward the amount
   */
  public void setPensionAllowanceCarryForward(final BigDecimal pensionAllowanceCarryForward) {
    this.pensionAllowanceCarryForward = pensionAllowanceCarryForward;
  }

  /**
   * Get the amount of payments on account payments already made.
   *
   * @return the amount
   */
  public BigDecimal getPaymentsMade() {
    return paymentsMade;
  }

  /**
   * Set the amount of payments on account payments already made.
   *
   * @param paymentsMade the amount
   */
  public void setPaymentsMade(final BigDecimal paymentsMade) {
    this.paymentsMade = paymentsMade;
  }

  /**
   * Get the amount of tax paid in the year - which overrides the calculated amount
   *
   * @return the amount
   */
  public BigDecimal getTaxPaidOverride() {
    return taxPaidOverride;
  }

  /**
   * Set the amount of tax paid in the year - which overrides the calculated amount
   *
   * @param taxPaidOverride the amount
   */
  public void setTaxPaidOverride(final BigDecimal taxPaidOverride) {
    this.taxPaidOverride = taxPaidOverride;
  }

}
