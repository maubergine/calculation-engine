package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ANetIncomeCalculator {

  private NetIncomeCalculator underTest = new NetIncomeCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new NetIncomeCalculator();
  }

  @Test
  public void shouldCalculateNetIncome() {

    final var totalIncome            = new BigDecimal("20000.00");
    final var totalBenefits          = new BigDecimal("1150.00");
    final var totalAllowableExpenses = new BigDecimal("110.00");

    final var expected = new BigDecimal("21040.00");

    assertThat(underTest.calculate(totalIncome, totalBenefits, totalAllowableExpenses)).isEqualTo(
        expected);


  }
}