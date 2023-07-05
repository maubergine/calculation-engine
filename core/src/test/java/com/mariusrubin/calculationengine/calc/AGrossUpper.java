package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.RateLevel;
import com.mariusrubin.calculationengine.model.DefaultRate;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AGrossUpper {

  private GrossUpper underTest;

  @BeforeEach
  public void setUp() {
    underTest = new GrossUpper();
  }

  @Test
  public void shouldGrossUpAnAmountOnStandardRates() {
    final var input    = new BigDecimal("8");
    final var expected = new BigDecimal("10.00");

    assertThat(underTest.grossUp(input, UkTaxRates.FY19_20)).isEqualTo(expected);

  }

  @Test
  public void shouldGrossUpAnAmountOnCustomRates() {
    final var input    = new BigDecimal("8");
    final var expected = new BigDecimal("9.88");
    final var testRate = new DefaultRate(RateLevel.BASIC, 19);

    assertThat(underTest.grossUp(input, testRate)).isEqualTo(expected);

  }
}