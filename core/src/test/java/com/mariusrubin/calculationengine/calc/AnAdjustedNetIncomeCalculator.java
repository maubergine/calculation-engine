package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnAdjustedNetIncomeCalculator {

  private AdjustedNetIncomeCalculator undertest;

  @BeforeEach
  void setUp() {
    undertest = new AdjustedNetIncomeCalculator();
  }

  @Test
  void shouldCalculatedAdjustedNetIncome() {
    final var totalSipp  = new BigDecimal("2.00");
    final var totalGifts = new BigDecimal("4.00");
    final var netIncome  = new BigDecimal("10.00");

    final var expected = new BigDecimal("4.00");

    assertThat(undertest.calculate(netIncome, totalSipp, totalGifts)).isEqualTo(expected);

  }

}