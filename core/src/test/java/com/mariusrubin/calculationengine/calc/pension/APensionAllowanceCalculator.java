package com.mariusrubin.calculationengine.calc.pension;

import static com.mariusrubin.calculationengine.UkTaxRates.FY22_23;
import static com.mariusrubin.calculationengine.UkTaxRates.FY23_24;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.model.calc.DefaultPensionCalc;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class APensionAllowanceCalculator {

  private PensionAllowanceCalculator underTest;

  @BeforeEach
  public void setUp() {
    underTest = new PensionAllowanceCalculator();
  }

  @Test
  public void shouldCalculateMaximumUntaperedAllowanceAndAddCarryover() {

    final var expected = new DefaultPensionCalc(
        new BigDecimal("40000.00"),
        new BigDecimal("45000.00"),
        new BigDecimal("50000.00"),
        new BigDecimal("55000.00"),
        new BigDecimal("0.00"),
        new BigDecimal("41000.00")
    );

    assertThat(underTest.calculate(FY22_23,
                                   new BigDecimal("45000.00"),
                                   new BigDecimal("50000.00"),
                                   new BigDecimal("55000.00"),
                                   new BigDecimal("1000.00"))).isEqualTo(expected);

  }

  @Test
  public void shouldCalculateAllowanceForLowerIncomesAndIgnoreCarryover() {

    final var expected = new DefaultPensionCalc(
        new BigDecimal("27000.00"),
        new BigDecimal("27000.00"),
        new BigDecimal("30000.00"),
        new BigDecimal("33000.00"),
        ZERO,
        new BigDecimal("27000.00")
    );

    assertThat(underTest.calculate(FY22_23,
                                   new BigDecimal("27000.00"),
                                   new BigDecimal("30000.00"),
                                   new BigDecimal("33000.00"),
                                   new BigDecimal("1000.00"))).isEqualTo(expected);

  }

  @Test
  public void shouldNotGoBelowMinimumForLowIncome() {

    final var expected = new DefaultPensionCalc(
        new BigDecimal("3600.00"),
        new BigDecimal("2500.00"),
        new BigDecimal("2500.00"),
        new BigDecimal("2500.00"),
        ZERO,
        new BigDecimal("3600.00")
    );

    assertThat(underTest.calculate(FY23_24,
                                   new BigDecimal("2500.00"),
                                   new BigDecimal("2500.00"),
                                   new BigDecimal("2500.00"),
                                   ZERO)).isEqualTo(expected);

  }

  @Test
  public void shouldNotTaperWhenBelowThreshold() {

    final var expected = new DefaultPensionCalc(
        new BigDecimal("40000.00"),
        new BigDecimal("180000.00"),
        new BigDecimal("198000.00"),
        new BigDecimal("270000.00"),
        ZERO,
        new BigDecimal("40000.00")
    );

    assertThat(underTest.calculate(FY22_23,
                                   new BigDecimal("180000.00"),
                                   new BigDecimal("198000.00"),
                                   new BigDecimal("270000.00"),
                                   ZERO)).isEqualTo(expected);

  }

  @Test
  public void shouldTaperWhenAboveThreshold() {

    final var expected = new DefaultPensionCalc(
        new BigDecimal("40000.00"),
        new BigDecimal("190000.00"),
        new BigDecimal("209000.00"),
        new BigDecimal("270000.00"),
        new BigDecimal("15000.00"),
        new BigDecimal("25000.00")
    );

    assertThat(underTest.calculate(FY22_23,
                                   new BigDecimal("190000.00"),
                                   new BigDecimal("209000.00"),
                                   new BigDecimal("270000.00"),
                                   ZERO)).isEqualTo(expected);

  }

  @Test
  public void shouldNotTaperBeyondAllowanceAndShouldRespectCarryover() {

    final var expected = new DefaultPensionCalc(
        new BigDecimal("40000.00"),
        new BigDecimal("190000.00"),
        new BigDecimal("209000.00"),
        new BigDecimal("350000.00"),
        new BigDecimal("36000.00"),
        new BigDecimal("5000.00")
    );

    assertThat(underTest.calculate(FY22_23,
                                   new BigDecimal("190000.00"),
                                   new BigDecimal("209000.00"),
                                   new BigDecimal("350000.00"),
                                   new BigDecimal("1000.00"))).isEqualTo(expected);

  }

}