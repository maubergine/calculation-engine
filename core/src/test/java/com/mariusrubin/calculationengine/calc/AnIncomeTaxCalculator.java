package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.UkTaxRates.FY22_23;
import static com.mariusrubin.calculationengine.api.IncomeType.DIVIDENDS;
import static com.mariusrubin.calculationengine.api.IncomeType.EMPLOYMENT;
import static com.mariusrubin.calculationengine.api.IncomeType.INTEREST;
import static com.mariusrubin.calculationengine.api.RateLevel.ADDITIONAL;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC_NIL;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER_NIL;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.model.DividendIncome;
import com.mariusrubin.calculationengine.model.InterestIncome;
import com.mariusrubin.calculationengine.model.KnownEmploymentIncome;
import com.mariusrubin.calculationengine.model.calc.DefaultBasicRateAdjustmentCalc;
import com.mariusrubin.calculationengine.model.calc.DefaultPersonalAllowanceCalc;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AnIncomeTaxCalculator {

  private IncomeTaxCalculator underTest = new IncomeTaxCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new IncomeTaxCalculator();
  }

  @Test
  public void shouldCalculateBasicIncomeTaxWithPA() {

    final var inc1 = new KnownEmploymentIncome();
    inc1.setAmount(new BigDecimal("33000.00"));

    final var paCalc = new DefaultPersonalAllowanceCalc(new BigDecimal("12570.00"),
                                                        new BigDecimal("0.00"),
                                                        new BigDecimal("0.00"));

    final var bRACalc = new DefaultBasicRateAdjustmentCalc(ZERO, ZERO, ZERO);

    final var taxes = underTest.calculate(FY22_23, List.of(inc1), paCalc, bRACalc, ZERO);

    //Income tax should be
    //(income - personal allowance) * 0.2
    assertThat(taxes.total().tax()).isEqualTo("4086.00");
    assertThat(taxes.total().amount()).isEqualTo("20430.00");

  }

  @Test
  public void shouldCalculateSavingsTax() {

    final var inc1 = new KnownEmploymentIncome();
    inc1.setAmount(new BigDecimal("33000.00"));

    final var inc2 = new InterestIncome(new BigDecimal("1200.50"));

    final var paCalc = new DefaultPersonalAllowanceCalc(new BigDecimal("12570.00"),
                                                        new BigDecimal("0.00"),
                                                        new BigDecimal("0.00"));

    final var bRACalc = new DefaultBasicRateAdjustmentCalc(ZERO, ZERO, ZERO);

    final var taxes = underTest.calculate(FY22_23, List.of(inc1, inc2), paCalc, bRACalc, ZERO);

    //Income tax should be
    //(income - personal allowance) * 0.2, with a £1000 reduction for savings allowance at basic
    assertThat(taxes.taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("4086.00");
    assertThat(taxes.taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("20430.00");

    //Savings should have a £1000 reduction applied to it, with the remainder falling in basic.
    assertThat(taxes.taxOn(INTEREST, BASIC_NIL).tax()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, BASIC_NIL).amount()).isEqualTo("1000.00");
    assertThat(taxes.taxOn(INTEREST, BASIC).tax()).isEqualTo("40.00");
    assertThat(taxes.taxOn(INTEREST, BASIC).amount()).isEqualTo("200.00");

    assertThat(taxes.total().tax()).isEqualTo("4126.00");
    assertThat(taxes.total().amount()).isEqualTo("21630.00");

  }

  @Test
  public void shouldCalculateSavingsTaxAcrossBands() {

    /*
    This makes sure that:
    Savings allowance takes account of the highest band only.
    Nil adjustment is spread across available tax.
     */
    final var inc1 = new KnownEmploymentIncome();
    inc1.setAmount(new BigDecimal("50000.00"));

    //This interest income pushes over into the higher rate.
    final var inc2 = new InterestIncome(new BigDecimal("601.50"));

    final var paCalc = new DefaultPersonalAllowanceCalc(new BigDecimal("12570.00"),
                                                        new BigDecimal("0.00"),
                                                        new BigDecimal("0.00"));

    final var bRACalc = new DefaultBasicRateAdjustmentCalc(ZERO, ZERO, ZERO);

    final var taxes = underTest.calculate(FY22_23, List.of(inc1, inc2), paCalc, bRACalc, ZERO);

    //Employment income tax should be
    //(income - personal allowance) * 0.2 (it all falls within basic).
    assertThat(taxes.taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("7486.00");
    assertThat(taxes.taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("37430.00");

    //Interest income tax should be
    //£0 at basic as all of it is consumed by allowance
    //A further £230 consumed at higher nil, and then the last £101 taxed at higher.
    assertThat(taxes.taxOn(INTEREST, BASIC).tax()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, BASIC).amount()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, BASIC_NIL).tax()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, BASIC_NIL).amount()).isEqualTo("270.00");
    assertThat(taxes.taxOn(INTEREST, HIGHER).tax()).isEqualTo("40.40");
    assertThat(taxes.taxOn(INTEREST, HIGHER).amount()).isEqualTo("101.00");
    assertThat(taxes.taxOn(INTEREST, HIGHER_NIL).tax()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, HIGHER_NIL).amount()).isEqualTo("230.00");

    assertThat(taxes.total().tax()).isEqualTo("7526.40");
    assertThat(taxes.total().amount()).isEqualTo("38031.00");

  }

  @Test
  public void shouldRemoveSavingsAllowanceForAdditionalRatePayers() {

    final var inc1 = new KnownEmploymentIncome();
    inc1.setAmount(new BigDecimal("160000.00"));

    //This interest income pushes over into the higher rate.
    final var inc2 = new InterestIncome(new BigDecimal("601.50"));

    final var paCalc = new DefaultPersonalAllowanceCalc(new BigDecimal("0.00"),
                                                        new BigDecimal("60601.00"),
                                                        new BigDecimal("12570.00"));

    final var bRACalc = new DefaultBasicRateAdjustmentCalc(ZERO, ZERO, ZERO);

    final var taxes = underTest.calculate(FY22_23, List.of(inc1, inc2), paCalc, bRACalc, ZERO);

    //Employment income tax should be
    //Consume all of basic (minus personal allowance)
    //Consume all of higher at 0.4
    //Remainder additional at 0.45
    assertThat(taxes.taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("7540.00");
    assertThat(taxes.taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("37700.00");
    assertThat(taxes.taxOn(EMPLOYMENT, HIGHER).tax()).isEqualTo("44920.00");
    assertThat(taxes.taxOn(EMPLOYMENT, HIGHER).amount()).isEqualTo("112300.00");
    assertThat(taxes.taxOn(EMPLOYMENT, ADDITIONAL).tax()).isEqualTo("4500.00");
    assertThat(taxes.taxOn(EMPLOYMENT, ADDITIONAL).amount()).isEqualTo("10000.00");

    //Interest income tax should be against £601.00 at additional with no allowance.
    assertThat(taxes.taxOn(INTEREST, ADDITIONAL).tax()).isEqualTo("270.45");
    assertThat(taxes.taxOn(INTEREST, ADDITIONAL).amount()).isEqualTo("601.00");

    assertThat(taxes.total().tax()).isEqualTo("57230.45");
    assertThat(taxes.total().amount()).isEqualTo("160601.00");

  }

  @Test
  public void shouldCalculateAcrossEmploymentSavingsAndDividends() {

    final var inc1 = new KnownEmploymentIncome();
    inc1.setAmount(new BigDecimal("50000.00"));

    //This interest income pushes over into the higher rate.
    final var inc2 = new InterestIncome(new BigDecimal("601.50"));

    //This dividend income pushes over into the higher rate.
    final var inc3 = new DividendIncome();
    inc3.setAmount(new BigDecimal("2500.20"));

    final var paCalc = new DefaultPersonalAllowanceCalc(new BigDecimal("12570.00"),
                                                        new BigDecimal("0.00"),
                                                        new BigDecimal("0.00"));

    final var bRACalc = new DefaultBasicRateAdjustmentCalc(ZERO, ZERO, ZERO);

    final var taxes = underTest.calculate(FY22_23, List.of(inc1, inc2, inc3), paCalc, bRACalc, ZERO);

    //Employment income tax should be
    //(income - personal allowance) * 0.2 (it all falls within basic).
    assertThat(taxes.taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("7486.00");
    assertThat(taxes.taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("37430.00");

    //Interest income tax should be
    //£0 at basic as all of it is consumed by allowance
    //A further £230 consumed at higher nil, and then the last £101 taxed at higher.
    assertThat(taxes.taxOn(INTEREST, BASIC).tax()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, BASIC).amount()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, BASIC_NIL).tax()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, BASIC_NIL).amount()).isEqualTo("270.00");
    assertThat(taxes.taxOn(INTEREST, HIGHER).tax()).isEqualTo("40.40");
    assertThat(taxes.taxOn(INTEREST, HIGHER).amount()).isEqualTo("101.00");
    assertThat(taxes.taxOn(INTEREST, HIGHER_NIL).tax()).isEqualTo("0.00");
    assertThat(taxes.taxOn(INTEREST, HIGHER_NIL).amount()).isEqualTo("230.00");

    //Dividend income tax should be
    //£2500 at higher, of which £2000 is consumed by allowance
    assertThat(taxes.taxOn(DIVIDENDS, HIGHER_NIL).tax()).isEqualTo("0.00");
    assertThat(taxes.taxOn(DIVIDENDS, HIGHER_NIL).amount()).isEqualTo("2000.00");
    assertThat(taxes.taxOn(DIVIDENDS, HIGHER).tax()).isEqualTo("168.75");
    assertThat(taxes.taxOn(DIVIDENDS, HIGHER).amount()).isEqualTo("500.00");

    assertThat(taxes.total().tax()).isEqualTo("7695.15");
    assertThat(taxes.total().amount()).isEqualTo("40531.00");

  }


}