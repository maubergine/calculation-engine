package com.mariusrubin.calculationengine;

import static com.mariusrubin.calculationengine.api.RateLevel.ADDITIONAL;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER;
import static com.mariusrubin.calculationengine.api.RateLevel.LOWER;

import com.mariusrubin.calculationengine.api.BoundedRate;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.Rate;
import com.mariusrubin.calculationengine.api.RateLevel;
import com.mariusrubin.calculationengine.model.DefaultBoundedRate;
import com.mariusrubin.calculationengine.model.DefaultRate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Enumeration describing the tax rates used by the calculation for several recent financial years.
 * In future these will be better loaded in config.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public enum UkTaxRates {

  FY17_18(UkFinancialYear.starting(2017),
          new PersonalAllowanceRates(
              11_500,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 33_500),
              new DefaultBoundedRate(HIGHER, 40, 150_000),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              5_000,
              new DefaultRate(BASIC, 7.5f),
              new DefaultRate(HIGHER, 32.5f),
              new DefaultRate(ADDITIONAL, 38.1f)
          ),
          new SavingsAllowances(
              1_000,
              500,
              0
          ),
          new PensionRates(
              40_000,
              10_000,
              3_600,
              110_000,
              150_000,
              0.5f),
          new Class1NiRates(
              680,
              new DefaultBoundedRate(LOWER, 12, 3_750),
              new DefaultRate(HIGHER, 2),
              13.8f
          )
  ),

  FY18_19(UkFinancialYear.starting(2018),
          new PersonalAllowanceRates(
              11_850,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 34_500),
              new DefaultBoundedRate(HIGHER, 40, 150_000),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              2_000,
              new DefaultRate(BASIC, 7.5f),
              new DefaultRate(HIGHER, 32.5f),
              new DefaultRate(ADDITIONAL, 38.1f)
          ),
          new SavingsAllowances(
              1_000,
              500,
              0
          ),
          new PensionRates(
              40_000,
              10_000,
              3_600,
              110_000,
              150_000,
              0.5f),
          new Class1NiRates(
              702,
              new DefaultBoundedRate(LOWER, 12, 3_863),
              new DefaultRate(HIGHER, 2),
              13.8f
          )
  ),

  FY19_20(UkFinancialYear.starting(2019),
          new PersonalAllowanceRates(
              12_500,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 37_500),
              new DefaultBoundedRate(HIGHER, 40, 150_000),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              2_000,
              new DefaultRate(BASIC, 7.5f),
              new DefaultRate(HIGHER, 32.5f),
              new DefaultRate(ADDITIONAL, 38.1f)
          ),
          new SavingsAllowances(
              1_000,
              500,
              0
          ),
          new PensionRates(
              40_000,
              10_000,
              3_600,
              110_000,
              150_000,
              0.5f),
          new Class1NiRates(
              719,
              new DefaultBoundedRate(LOWER, 12, 4_167),
              new DefaultRate(HIGHER, 2),
              13.8f
          )
  ),

  FY20_21(UkFinancialYear.starting(2020),
          new PersonalAllowanceRates(
              12_500,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 37_500),
              new DefaultBoundedRate(HIGHER, 40, 150_000),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              2_000,
              new DefaultRate(BASIC, 7.5f),
              new DefaultRate(HIGHER, 32.5f),
              new DefaultRate(ADDITIONAL, 38.1f)
          ),
          new SavingsAllowances(
              1_000,
              500,
              0
          ),
          new PensionRates(
              40_000,
              4_000,
              3_600,
              200_000,
              240_000,
              0.5f),
          new Class1NiRates(
              792,
              new DefaultBoundedRate(LOWER, 12, 4_167),
              new DefaultRate(HIGHER, 2),
              13.80f
          )),

  FY21_22(UkFinancialYear.starting(2021),
          new PersonalAllowanceRates(
              12_570,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 37_700),
              new DefaultBoundedRate(HIGHER, 40, 150_000),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              2_000,
              new DefaultRate(BASIC, 7.5f),
              new DefaultRate(HIGHER, 32.5f),
              new DefaultRate(ADDITIONAL, 38.1f)
          ),
          new SavingsAllowances(
              1_000,
              500,
              0
          ),
          new PensionRates(
              40_000,
              4_000,
              3_600,
              200_000,
              240_000,
              0.5f),
          new Class1NiRates(
              797,
              new DefaultBoundedRate(LOWER, 12, 4_189),
              new DefaultRate(HIGHER, 2),
              13.80f
          )),

  FY22_23(UkFinancialYear.starting(2022),
          new PersonalAllowanceRates(
              12_570,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 37_700),
              new DefaultBoundedRate(HIGHER, 40, 150_000),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              2_000,
              new DefaultRate(BASIC, 8.75f),
              new DefaultRate(HIGHER, 33.75f),
              new DefaultRate(ADDITIONAL, 39.35f)
          ),
          new SavingsAllowances(
              //TODO sort starting rate for savings and associated taper
              1_000,
              500,
              0
          ),
          new PensionRates(
              40_000,
              4_000,
              3_600,
              200_000,
              240_000,
              0.5f),
          new Class1NiRates(
              823,
              new DefaultBoundedRate(LOWER, 13.25f, 4_189),
              //TODO deal with the pain of a rate that changed in a mid-year budget...
              new DefaultRate(HIGHER, 0.0325f),
              15.05f
          )),

  FY23_24(UkFinancialYear.starting(2023),
          new PersonalAllowanceRates(
              12_570,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 37_700),
              new DefaultBoundedRate(HIGHER, 40, 125_140),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              1_000,
              new DefaultRate(BASIC, 8.75f),
              new DefaultRate(HIGHER, 33.75f),
              new DefaultRate(ADDITIONAL, 39.35f)
          ),
          new SavingsAllowances(
              1_000,
              500,
              0
          ),
          new PensionRates(
              60_000,
              10_000,
              3_600,
              200_000,
              260_000,
              0.5f),
          new Class1NiRates(
              1_048,
              new DefaultBoundedRate(LOWER, 12, 4_189),
              new DefaultRate(HIGHER, 2),
              13.8f
          )),
  FY24_25(UkFinancialYear.starting(2024),
          new PersonalAllowanceRates(
              12_570,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 37_700),
              new DefaultBoundedRate(HIGHER, 40, 125_140),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              1_000,
              new DefaultRate(BASIC, 8.75f),
              new DefaultRate(HIGHER, 33.75f),
              new DefaultRate(ADDITIONAL, 39.35f)
          ),
          new SavingsAllowances(
              1_000,
              500,
              0
          ),
          new PensionRates(
              60_000,
              10_000,
              3_600,
              200_000,
              260_000,
              0.5f),
          new Class1NiRates(
              1_048,
              new DefaultBoundedRate(LOWER, 12, 4_189),
              new DefaultRate(HIGHER, 2),
              13.8f
          )),
  FY25_26(UkFinancialYear.starting(2025),
          new PersonalAllowanceRates(
              12_570,
              100_000,
              0.5f
          ),
          new IncomeTaxRates(
              new DefaultBoundedRate(BASIC, 20, 37_700),
              new DefaultBoundedRate(HIGHER, 40, 125_140),
              new DefaultRate(ADDITIONAL, 45)
          ),
          new DividendRates(
              1_000,
              new DefaultRate(BASIC, 8.75f),
              new DefaultRate(HIGHER, 33.75f),
              new DefaultRate(ADDITIONAL, 39.35f)
          ),
          new SavingsAllowances(
              1_000,
              500,
              0
          ),
          new PensionRates(
              60_000,
              10_000,
              3_600,
              200_000,
              260_000,
              0.5f),
          new Class1NiRates(
              1_048,
              new DefaultBoundedRate(LOWER, 12, 4_189),
              new DefaultRate(HIGHER, 2),
              13.8f
          ));

  private final FinancialYear          financialYear;
  private final PersonalAllowanceRates personalAllowance;
  private final IncomeTaxRates         incomeTaxRates;
  private final DividendRates          dividendRates;
  private final SavingsAllowances      savingsAllowances;
  private final PensionRates           pensionRates;
  private final Class1NiRates          niRates;


  /**
   * Build an overall tax configuration including all the details of the various taxes that the
   * engine can calculate.
   *
   * @param financialYear     the financial year associated with the tax rate
   * @param personalAllowance the personal allowance tax info
   * @param incomeTaxRates    the income tax rate info
   * @param dividendRates     the dividend rate info
   * @param savingsAllowances the savings allowance info
   * @param pensionRates      the pension rate info
   * @param niRates           the National Insurance rate info
   */
  UkTaxRates(final FinancialYear financialYear,
             final PersonalAllowanceRates personalAllowance,
             final IncomeTaxRates incomeTaxRates,
             final DividendRates dividendRates,
             final SavingsAllowances savingsAllowances,
             final PensionRates pensionRates,
             final Class1NiRates niRates) {
    this.financialYear = financialYear;
    this.personalAllowance = personalAllowance;
    this.incomeTaxRates = incomeTaxRates;
    this.dividendRates = dividendRates;
    this.savingsAllowances = savingsAllowances;
    this.pensionRates = pensionRates;
    this.niRates = niRates;
  }

  /**
   * Find the UK tax configuration that applies to the date provided. For example, a date between
   * 2023-04-06 and 2024-04-05 will be identified as belonging to UK FY 23 and the relevant
   * tax information will be returned accordingly.
   *
   * @param date the date for which to find the relevant tax year configuration
   * @return the tax configuration
   * @throws IllegalArgumentException when there is not a tax configuration for the provided date
   */
  public static UkTaxRates forDate(final LocalDate date) {

    return Stream.of(UkTaxRates.values())
                 .filter(r -> r.financialYear().getStartDate().compareTo(date) < 1 &&
                              r.financialYear().getEndDate().compareTo(date) > -1)
                 .findAny()
                 .orElseThrow(() -> new IllegalArgumentException(String.format(
                     "Could not find tax year for date %s",
                     date)));

  }

  /**
   * Get the tax rate applicable at the point of execution.
   *
   * @return the tax rate for now (if it is configured)
   */
  public static UkTaxRates current() {
    return forDate(LocalDate.now());
  }

  /**
   * Get the underlying financial year for this tax configuration,
   *
   * @return the financial year
   */
  public FinancialYear financialYear() {
    return financialYear;
  }

  /**
   * Get the personal allowance rates.
   *
   * @return the personal allowance rates
   */
  public PersonalAllowanceRates personalAllowanceRates() {
    return personalAllowance;
  }

  /**
   * Get the income tax rates.
   *
   * @return the income tax rates
   */
  public IncomeTaxRates incomeTaxRates() {
    return incomeTaxRates;
  }

  /**
   * Get the dividend rates.
   *
   * @return the rates.
   */
  public DividendRates dividendRates() {
    return dividendRates;
  }

  /**
   * Get the savings allowances.
   *
   * @return the allowances
   */
  public SavingsAllowances savingsAllowances() {
    return savingsAllowances;
  }

  /**
   * Get the pension rates.
   *
   * @return the pension rates
   */
  public PensionRates pensionRates() {
    return pensionRates;
  }

  /**
   * Get the Class 1 National Insurance rates.
   *
   * @return the NI rates
   */
  public Class1NiRates niRates() {
    return niRates;
  }

  /**
   * Describes the personal allowance and associate threshold/taper.
   */
  public static class PersonalAllowanceRates {

    private final BigDecimal amount;
    private final BigDecimal threshold;
    private final BigDecimal taperRate;

    /**
     * Build a personal allowance object.
     *
     * @param amount    the default personal allowance amount
     * @param threshold the threshold over which personal allowance starts to be tapered down
     * @param taperRate the amount by which the allowance gets tapered for every £1 of earnings
     *                  over the threshold
     */
    public PersonalAllowanceRates(final BigDecimal amount,
                                  final BigDecimal threshold,
                                  final BigDecimal taperRate) {
      this.amount = amount;
      this.threshold = threshold;
      this.taperRate = taperRate;
    }

    /**
     * Build a personal allowance object.
     *
     * @param amount    the default personal allowance amount
     * @param threshold the threshold over which personal allowance starts to be tapered down
     * @param taperRate the amount by which the allowance gets tapered for every £1 of earnings
     *                  over the threshold
     */
    public PersonalAllowanceRates(final int amount,
                                  final int threshold,
                                  final float taperRate) {
      this(new BigDecimal(amount), new BigDecimal(threshold), new BigDecimal(taperRate));
    }

    /**
     * Get the default personal allowance amount.
     *
     * @return the amount
     */
    public BigDecimal amount() {
      return amount;
    }

    /**
     * Get the personal allowance threshold.
     *
     * @return the threshold amount
     */
    public BigDecimal threshold() {
      return threshold;
    }

    /**
     * Get the rate of taper.
     *
     * @return the taper rate
     */
    public BigDecimal taperRate() {
      return taperRate;
    }
  }

  /**
   * Describes the tax rates and allowances that apply to "regular" income incl. employment.
   *
   * @param basicRate      the basic rate
   * @param higherRate     the higher rate
   * @param additionalRate the additional rate
   */
  public record IncomeTaxRates(BoundedRate basicRate,
                               BoundedRate higherRate,
                               Rate additionalRate) implements LeveledRates {

    /**
     * Get the basic rate of income tax.
     *
     * @return the rate
     */
    @Override
    public BoundedRate basicRate() {
      return basicRate;
    }

    /**
     * Get the higher rate of income tax.
     *
     * @return the rate
     */
    @Override
    public BoundedRate higherRate() {
      return higherRate;
    }

    /**
     * Get the additional rate of income tax.
     *
     * @return the rate
     */
    @Override
    public Rate additionalRate() {
      return additionalRate;
    }

    @Override
    public Rate forLevel(final RateLevel rateLevel) {
      return switch (rateLevel) {
        case BASIC -> basicRate();
        case HIGHER -> higherRate();
        case ADDITIONAL -> additionalRate();
        default -> throw new IllegalStateException("Unexpected value: " + rateLevel);
      };
    }
  }

  public LeveledRates forType(final IncomeType type) {
    return switch (type) {
      case EMPLOYMENT, INTEREST, PENSION, PENSION_CHARGE -> incomeTaxRates();
      case DIVIDENDS -> dividendRates();
      default -> throw new IllegalStateException("Unexpected value: " + type);
    };
  }

  /**
   * Describes the tax rates and allowances that apply to dividend income.
   */
  public static class DividendRates implements LeveledRates {

    private final BigDecimal allowance;
    private final Rate       basicRate;
    private final Rate       higherRate;
    private final Rate       additionalRate;


    /**
     * Build a dividend rates object
     *
     * @param allowance      the dividend allowance
     * @param basicRate      the basic rate of tax on dividend income
     * @param higherRate     the higher rate of tax on dividend income
     * @param additionalRate the additional rate of tax on dividend income
     */
    public DividendRates(final int allowance,
                         final Rate basicRate,
                         final Rate higherRate,
                         final Rate additionalRate) {
      this(new BigDecimal(allowance), basicRate, higherRate, additionalRate);
    }

    /**
     * Build a dividend rates object.
     *
     * @param allowance      the dividend allowance
     * @param basicRate      the basic rate of tax on dividend income
     * @param higherRate     the higher rate of tax on dividend income
     * @param additionalRate the additional rate of tax on dividend income
     */
    public DividendRates(final BigDecimal allowance,
                         final Rate basicRate,
                         final Rate higherRate,
                         final Rate additionalRate) {
      this.allowance = allowance;
      this.basicRate = basicRate;
      this.higherRate = higherRate;
      this.additionalRate = additionalRate;
    }

    /**
     * Get the dividend allowance
     *
     * @return the allowance
     */
    public BigDecimal allowance() {
      return allowance;
    }

    /**
     * Get the basic rate of dividend tax
     *
     * @return the rate
     */
    public Rate basicRate() {
      return basicRate;
    }

    /**
     * Get the higher rate of dividend tax
     *
     * @return the rate
     */
    public Rate higherRate() {
      return higherRate;
    }

    /**
     * Get the additional rate of dividend tax
     *
     * @return the rate
     */
    public Rate additionalRate() {
      return additionalRate;
    }

    @Override
    public Rate forLevel(final RateLevel rateLevel) {
      return switch (rateLevel) {
        case BASIC -> basicRate();
        case HIGHER -> higherRate();
        case ADDITIONAL -> additionalRate();
        default -> throw new IllegalStateException("Unexpected value: " + rateLevel);
      };
    }
  }

  /**
   * Describes the various allowances that apply to savings based on the tax band the taxpayer
   * falls in. More details can be found on the
   * <a href="https://www.gov.uk/apply-tax-free-interest-on-savings">HMRC website</a>.
   */
  public static class SavingsAllowances {

    private final BigDecimal basicAllowance;
    private final BigDecimal higherAllowance;
    private final BigDecimal additionalAllowance;

    /**
     * Build a savings allowance object.
     *
     * @param basicAllowance      the basic rate allowance
     * @param higherAllowance     the higher rate allowance
     * @param additionalAllowance the additional rate allowance
     */
    public SavingsAllowances(final BigDecimal basicAllowance,
                             final BigDecimal higherAllowance,
                             final BigDecimal additionalAllowance) {
      this.basicAllowance = basicAllowance;
      this.higherAllowance = higherAllowance;
      this.additionalAllowance = additionalAllowance;
    }

    /**
     * Build a savings allowance object.
     *
     * @param basicAllowance      the basic rate allowance
     * @param higherAllowance     the higher rate allowance
     * @param additionalAllowance the additional rate allowance
     */
    public SavingsAllowances(final int basicAllowance,
                             final int higherAllowance,
                             final int additionalAllowance) {
      this(new BigDecimal(basicAllowance),
           new BigDecimal(higherAllowance),
           new BigDecimal(additionalAllowance));

    }

    /**
     * The allowance for basic rate taxpayers.
     *
     * @return the allowance
     */
    public BigDecimal basicAllowance() {
      return basicAllowance;
    }

    /**
     * The allowance for higher rate taxpayers.
     *
     * @return the allowance
     */
    public BigDecimal higherAllowance() {
      return higherAllowance;
    }

    /**
     * The allowance for additional rate taxpayers.
     *
     * @return the allowance
     */
    public BigDecimal additionalAllowance() {
      return additionalAllowance;
    }
  }

  /**
   * Describes the various rates/thresholds that apply to pension contributions, and also drive
   * whether or not pension charges are due.
   */
  public static class PensionRates {

    private final BigDecimal annualAllowance;
    private final BigDecimal minimumAllowance;
    private final BigDecimal earningsLowerLimit;
    private final BigDecimal thresholdIncome;
    private final BigDecimal adjustedIncome;
    private final BigDecimal taperRate;

    /**
     * Build a pension rates object.
     *
     * @param annualAllowance    the pension annual allowance
     * @param minimumAllowance   the minimum pension allowance in a given year i.e. the lowest
     *                           amount to which a pension can be tapered
     * @param earningsLowerLimit the minimum pension contribution where relevant earnings are low
     * @param thresholdIncome    the threshold income amount
     * @param adjustedIncome     the adjusted income threshold
     * @param taperRate          the amount by which the pension allowance gets tapered down for
     *                           every £1 of adjusted income above the adjusted income threshold
     */
    public PensionRates(final BigDecimal annualAllowance,
                        final BigDecimal minimumAllowance,
                        final BigDecimal earningsLowerLimit,
                        final BigDecimal thresholdIncome,
                        final BigDecimal adjustedIncome,
                        final BigDecimal taperRate) {
      this.annualAllowance = annualAllowance;
      this.minimumAllowance = minimumAllowance;
      this.earningsLowerLimit = earningsLowerLimit;
      this.thresholdIncome = thresholdIncome;
      this.adjustedIncome = adjustedIncome;
      this.taperRate = taperRate;
    }

    /**
     * Build a pension rates object.
     *
     * @param annualAllowance    the pension annual allowance
     * @param minimumAllowance   the minimum pension allowance in a given year i.e. the lowest
     *                           amount to which a pension can be tapered
     * @param earningsLowerLimit the minimum pension contribution where relevant earnings are low
     * @param thresholdIncome    the threshold income amount
     * @param adjustedIncome     the adjusted income threshold
     * @param taperRate          the amount by which the pension allowance gets tapered down for
     *                           every £1 of adjusted income above the adjusted income threshold
     */
    public PensionRates(final int annualAllowance,
                        final int minimumAllowance,
                        final int earningsLowerLimit,
                        final int thresholdIncome,
                        final int adjustedIncome,
                        final float taperRate) {
      this(new BigDecimal(annualAllowance),
           new BigDecimal(minimumAllowance),
           new BigDecimal(earningsLowerLimit),
           new BigDecimal(thresholdIncome),
           new BigDecimal(adjustedIncome),
           new BigDecimal(taperRate));

    }

    /**
     * Get the pension annual allowance.
     *
     * @return the allowance
     */
    public BigDecimal annualAllowance() {
      return annualAllowance;
    }

    /**
     * Get the minimum pension allowance in a given year i.e. the lowest amount to which a pension
     * can be tapered.
     *
     * @return the minimum allowance
     */
    public BigDecimal minimumAllowance() {
      return minimumAllowance;
    }

    /**
     * Get the minimum pension allowance in a given year i.e. the lowest amount to which a pension
     * can be tapered.
     *
     * @return the minimum allowance
     */
    public BigDecimal earningsLowerLimit() {
      return earningsLowerLimit;
    }

    /**
     * Get the threshold income.
     *
     * @return the threshold income.
     */
    public BigDecimal thresholdIncome() {
      return thresholdIncome;
    }

    /**
     * Get the adjusted income threshold
     *
     * @return the adjusted income
     */
    public BigDecimal adjustedIncome() {
      return adjustedIncome;
    }

    /**
     * Get the pension taper rate income threshold i.e. the amount by which the pension allowance
     * gets tapered down for every £1 of adjusted income above the adjusted income threshold.
     *
     * @return the adjusted income
     */
    public BigDecimal taperRate() {
      return taperRate;
    }

  }

  /**
   * Contains information relating to
   * <a href="https://www.gov.uk/national-insurance/national-insurance-classes">Class 1 National
   * Insurance contributions.</a>
   * <br>
   * <br>
   * //TODO NI tax more complex than reflected in this class, needs proper implementation
   */
  public static class Class1NiRates {

    private final BigDecimal  primaryThreshold;
    private final BoundedRate rateToUpperEarnings;
    private final Rate        rateAboveUpperEarnings;
    private final BigDecimal  employersRate;

    /**
     * Build the National Insurance rates object.
     *
     * @param primaryThreshold       the primary threshold
     * @param rateToUpperEarnings    the rate paid above the primary threshold but below the upper
     *                               earnings limit
     * @param rateAboveUpperEarnings the rate paid above the upper earnings limit
     * @param employersRate          the employer's NI rate
     */
    public Class1NiRates(final int primaryThreshold,
                         final BoundedRate rateToUpperEarnings,
                         final Rate rateAboveUpperEarnings,
                         final float employersRate) {
      this(new BigDecimal(primaryThreshold),
           rateToUpperEarnings,
           rateAboveUpperEarnings,
           new BigDecimal(employersRate));
    }

    /**
     * Build the National Insurance rates object.
     *
     * @param primaryThreshold       the primary threshold
     * @param rateToUpperEarnings    the rate paid above the primary threshold but below the upper
     *                               earnings limit
     * @param rateAboveUpperEarnings the rate paid above the upper earnings limit
     * @param employersRate          the employer's NI rate
     */
    public Class1NiRates(final BigDecimal primaryThreshold,
                         final BoundedRate rateToUpperEarnings,
                         final Rate rateAboveUpperEarnings,
                         final BigDecimal employersRate) {
      this.primaryThreshold = primaryThreshold;
      this.rateToUpperEarnings = rateToUpperEarnings;
      this.rateAboveUpperEarnings = rateAboveUpperEarnings;
      this.employersRate = employersRate;
    }

    /**
     * Get the primary threshold.
     *
     * @return the threshold
     */
    public BigDecimal primaryThreshold() {
      return primaryThreshold;
    }

    /**
     * Get the rate paid above the primary threshold but below the upper earnings
     *
     * @return the rate
     */
    public BoundedRate rateToUpperEarnings() {
      return rateToUpperEarnings;
    }

    /**
     * Get the rate paid above upper earnings
     *
     * @return the rate
     */
    public Rate rateAboveUpperEarnings() {
      return rateAboveUpperEarnings;
    }

    /**
     * Get the employers rate of NI (percentage)
     *
     * @return the rate percentage
     */
    public BigDecimal employersRate() {
      return employersRate;
    }


  }

  /**
   * Tax categories that have levels (BASIC, HIGHER etc.) should implement this so the right
   * rate can be consistently retrieved.
   */
  public interface LeveledRates {

    /**
     * Get the rate for the relevant level.
     *
     * @param rateLevel the level
     * @return the rate
     */
    Rate forLevel(final RateLevel rateLevel);

  }

}
