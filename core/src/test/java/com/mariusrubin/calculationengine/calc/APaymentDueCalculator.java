package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.RateLevel.BASIC;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.UkFinancialYear;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.model.DefaultRate;
import com.mariusrubin.calculationengine.model.calc.DefaultIncomeTaxCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultPaymentDueCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultPaymentInstance;
import com.mariusrubin.calculationengine.model.calc.DefaultTaxedAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class APaymentDueCalculator {

  private PaymentDueCalculator underTest = new PaymentDueCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new PaymentDueCalculator();
  }

  @Test
  public void shouldCalculateNoPaymentsDueWhenFullyThroughEmployment() {

    final var tax1 = new DefaultTaxedAmount(new BigDecimal("20000.00"),
                                            new BigDecimal("4000.00"),
                                            IncomeType.EMPLOYMENT,
                                            new DefaultRate(BASIC, new BigDecimal("0.2")));

    final var itc = new DefaultIncomeTaxCalc(List.of(tax1), BigDecimal.ZERO);

    final var janDate  = LocalDate.of(2022, 1, 31);
    final var julyDate = LocalDate.of(2022, 7, 31);

    final var expected = new DefaultPaymentDueCalc(
        new BigDecimal("4000.00"),
        new BigDecimal("4000.00"),
        new BigDecimal("1.0000"),
        false,
        new DefaultPaymentInstance(ZERO, janDate),
        new DefaultPaymentInstance(ZERO, janDate),
        new DefaultPaymentInstance(ZERO, julyDate),
        new DefaultPaymentInstance(ZERO, janDate),
        ZERO
    );

    assertThat(underTest.calculate(itc, ZERO, null, UkFinancialYear.starting(2020))).isEqualTo(
        expected);

  }

  @Test
  public void shouldCalculateNoPaymentsDueWhenUnderPoAThreshold() {

    final var tax1 = new DefaultTaxedAmount(new BigDecimal("20000.00"),
                                            new BigDecimal("4000.00"),
                                            IncomeType.EMPLOYMENT,
                                            new DefaultRate(BASIC, new BigDecimal("0.2")));

    final var tax2 = new DefaultTaxedAmount(new BigDecimal("10000.00"),
                                            new BigDecimal("700.00"),
                                            IncomeType.DIVIDENDS,
                                            new DefaultRate(BASIC, new BigDecimal("0.07")));

    final var itc = new DefaultIncomeTaxCalc(List.of(tax1, tax2), BigDecimal.ZERO);

    final var janDate  = LocalDate.of(2021, 1, 31);
    final var julyDate = LocalDate.of(2021, 7, 31);

    final var expected = new DefaultPaymentDueCalc(
        new BigDecimal("4000.00"),
        new BigDecimal("4700.00"),
        new BigDecimal("0.8511"),
        false,
        new DefaultPaymentInstance(new BigDecimal("700.00"), janDate),
        new DefaultPaymentInstance(ZERO, janDate),
        new DefaultPaymentInstance(ZERO, julyDate),
        new DefaultPaymentInstance(new BigDecimal("700.00"), janDate),
        ZERO
    );

    assertThat(underTest.calculate(itc, ZERO, null, UkFinancialYear.starting(2019))).isEqualTo(
        expected);

  }

  @Test
  public void shouldCalculatePaymentsDueWhenOverPoAThreshold() {

    final var tax1 = new DefaultTaxedAmount(new BigDecimal("20000.00"),
                                            new BigDecimal("4000.00"),
                                            IncomeType.EMPLOYMENT,
                                            new DefaultRate(BASIC, new BigDecimal("0.2")));

    final var tax2 = new DefaultTaxedAmount(new BigDecimal("10000.00"),
                                            new BigDecimal("700.00"),
                                            IncomeType.DIVIDENDS,
                                            new DefaultRate(BASIC, new BigDecimal("0.07")));

    final var tax3 = new DefaultTaxedAmount(new BigDecimal("1000.00"),
                                            new BigDecimal("200.00"),
                                            IncomeType.INTEREST,
                                            new DefaultRate(BASIC, new BigDecimal("0.2")));

    final var itc = new DefaultIncomeTaxCalc(List.of(tax1, tax2, tax3), BigDecimal.ZERO);

    final var janDate  = LocalDate.of(2021, 1, 31);
    final var julyDate = LocalDate.of(2021, 7, 31);

    final var expected = new DefaultPaymentDueCalc(
        new BigDecimal("3800.25"),
        new BigDecimal("4900.00"),
        new BigDecimal("0.7756"),
        true,
        new DefaultPaymentInstance(new BigDecimal("1099.75"), janDate),
        new DefaultPaymentInstance(new BigDecimal("549.87"), janDate),
        new DefaultPaymentInstance(new BigDecimal("549.88"), julyDate),
        new DefaultPaymentInstance(new BigDecimal("1649.62"), janDate),
        new BigDecimal("1099.75")
    );

    //We are overriding the amount of tax paid to be below the PoA threshold
    assertThat(underTest.calculate(itc,
                                   BigDecimal.ZERO,
                                   new BigDecimal("3800.25"),
                                   UkFinancialYear.starting(2019))).isEqualTo(expected);

  }

  @Test
  public void shouldCalculateBalanceWhenPaymentsMade() {

    final var tax1 = new DefaultTaxedAmount(new BigDecimal("20000.00"),
                                            new BigDecimal("4000.00"),
                                            IncomeType.EMPLOYMENT,
                                            new DefaultRate(BASIC, new BigDecimal("0.2")));

    final var tax2 = new DefaultTaxedAmount(new BigDecimal("40000.00"),
                                            new BigDecimal("2800.00"),
                                            IncomeType.DIVIDENDS,
                                            new DefaultRate(BASIC, new BigDecimal("0.07")));

    final var tax3 = new DefaultTaxedAmount(new BigDecimal("1000.00"),
                                            new BigDecimal("200.00"),
                                            IncomeType.INTEREST,
                                            new DefaultRate(BASIC, new BigDecimal("0.2")));

    final var itc = new DefaultIncomeTaxCalc(List.of(tax1, tax2, tax3), BigDecimal.ZERO);

    final var janDate  = LocalDate.of(2021, 1, 31);
    final var julyDate = LocalDate.of(2021, 7, 31);

    final var expected = new DefaultPaymentDueCalc(
        new BigDecimal("4000.00"),
        new BigDecimal("7000.00"),
        new BigDecimal("0.5714"),
        true,
        new DefaultPaymentInstance(new BigDecimal("1000.00"), janDate),
        new DefaultPaymentInstance(new BigDecimal("1500.00"), janDate),
        new DefaultPaymentInstance(new BigDecimal("1500.00"), julyDate),
        new DefaultPaymentInstance(new BigDecimal("2500.00"), janDate),
        new BigDecimal("3000.00")
    );

    //We are overriding the amount of tax paid to be below the PoA threshold
    assertThat(underTest.calculate(itc,
                                   new BigDecimal("2000.00"),
                                   null,
                                   UkFinancialYear.starting(2019))).isEqualTo(expected);

  }

}