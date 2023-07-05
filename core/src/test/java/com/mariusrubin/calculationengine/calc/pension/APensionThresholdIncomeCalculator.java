package com.mariusrubin.calculationengine.calc.pension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class APensionThresholdIncomeCalculator {

  private PensionThresholdIncomeCalculator underTest = new PensionThresholdIncomeCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new PensionThresholdIncomeCalculator();
  }

  @Test
  public void shouldCalculateThresholdIncome() {

    final var adjustedNetIncome = new BigDecimal("100000.00");
    final var salarySacrifice   = new BigDecimal("20000.00");
    final var expected          = new BigDecimal("120000.00");

    assertThat(underTest.calculate(adjustedNetIncome, salarySacrifice)).isEqualTo(expected);

  }

}