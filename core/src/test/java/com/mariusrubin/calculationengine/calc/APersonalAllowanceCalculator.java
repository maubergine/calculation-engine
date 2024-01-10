package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.UkTaxRates.FY18_19;
import static com.mariusrubin.calculationengine.UkTaxRates.FY22_23;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.model.calc.DefaultPersonalAllowanceCalc;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class APersonalAllowanceCalculator {

  private PersonalAllowanceCalculator underTest = new PersonalAllowanceCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new PersonalAllowanceCalculator();
  }

  @Test
  public void shouldTaperPersonalAllowanceWhenOverThreshold() {

    final var adjustedNetIncome = new BigDecimal("125000.00");

    final var expected = new DefaultPersonalAllowanceCalc(
        new BigDecimal("70.00"),
        new BigDecimal("25000.00"),
        new BigDecimal("12500.00")
    );

    assertThat(underTest.calculate(adjustedNetIncome, ZERO, FY22_23.personalAllowanceRates()))
        .isEqualTo(expected);

  }

  @Test
  public void shouldOffsetRarContribsAgainstIncomeAndRoundUpAllowance() {

    final var adjustedNetIncome = new BigDecimal("125000.00");

    final var expected = new DefaultPersonalAllowanceCalc(
        new BigDecimal("632.00"),
        new BigDecimal("23877.00"),
        new BigDecimal("11938.50")
    );

    final var rarContribs = new BigDecimal("1123");

    assertThat(underTest.calculate(adjustedNetIncome,
                                   rarContribs,
                                   FY22_23.personalAllowanceRates()))
        .isEqualTo(expected);

  }

  @Test
  public void shouldLeaveAllowanceIntactWhenUnderThreshold() {

    final var adjustedNetIncome = new BigDecimal("100000.00");

    final var expected = new DefaultPersonalAllowanceCalc(
        new BigDecimal("11850.00"),
        new BigDecimal("0.00"),
        new BigDecimal("0.00")
    );

    assertThat(underTest.calculate(adjustedNetIncome, ZERO, FY18_19.personalAllowanceRates()))
        .isEqualTo(expected);

  }

  @Test
  public void shouldNotTaperBelowZero() {

    final var adjustedNetIncome = new BigDecimal("140000.00");

    final var expected = new DefaultPersonalAllowanceCalc(
        new BigDecimal("0.00"),
        new BigDecimal("40000.00"),
        new BigDecimal("12570.00")
    );

    assertThat(underTest.calculate(adjustedNetIncome, ZERO, FY22_23.personalAllowanceRates()))
        .isEqualTo(expected);

  }

}