package com.mariusrubin.calculationengine.calc.pension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnAdjustedIncomeCalculator {

  private AdjustedIncomeCalculator underTest;

  @BeforeEach
  public void setUp() {
    underTest = new AdjustedIncomeCalculator();
  }

  @Test
  public void shouldCalculateAdjustedIncome() {

    final var netIncome       = new BigDecimal("2.00");
    final var salarySacrifice = new BigDecimal("4.00");
    final var pensionSavings  = new BigDecimal("8.00");

    final var expected = new BigDecimal("14.00");

    assertThat(underTest.calculate(netIncome, salarySacrifice, pensionSavings)).isEqualTo(expected);

  }

}