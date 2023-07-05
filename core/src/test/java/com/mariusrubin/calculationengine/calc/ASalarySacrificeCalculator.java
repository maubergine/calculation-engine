package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.serde.PredictedEmploymentInfo;
import com.mariusrubin.calculationengine.model.PredictedEmploymentIncome;
import com.mariusrubin.calculationengine.test.TestTaxPayerBuilder;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ASalarySacrificeCalculator {

  private SalarySacrificeCalculator underTest = new SalarySacrificeCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new SalarySacrificeCalculator();
  }

  @Test
  public void shouldAddUpSalarySacrifice() {
    final var info1 = new PredictedEmploymentInfo();
    info1.setSalarySacrifice(new BigDecimal("0.05"));
    info1.setBase(new BigDecimal("60000.00"));

    final var inc1 = new PredictedEmploymentIncome(info1);

    final var expected = new BigDecimal("3000.00");

    final var payer = new TestTaxPayerBuilder().setIncomes(List.of(inc1)).createTestTaxPayer();

    assertThat(underTest.calculate(payer)).isEqualTo(expected);

  }

}