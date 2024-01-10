package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.PensionType.DEFINED_BENEFIT;
import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.model.DefinedLumpPension;
import com.mariusrubin.calculationengine.model.KnownPension;
import com.mariusrubin.calculationengine.test.TestTaxPayerBuilder;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ADefinedBenefitValueCalculator {

  private DefinedBenefitValueCalculator underTest = new DefinedBenefitValueCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new DefinedBenefitValueCalculator();
  }

  @Test
  public void shouldCalculateDefinedBenefitValue() {

    final var definedBenefit = new KnownPension();
    definedBenefit.setType(DEFINED_BENEFIT);
    definedBenefit.setAmount(new BigDecimal("810.22"));

    final var payer = new TestTaxPayerBuilder().setPensions(List.of(definedBenefit))
                                               .createTestTaxPayer();

    final var expected = new BigDecimal("12963.52");

    assertThat(underTest.calculate(payer)).isEqualTo(expected);

  }

  @Test
  public void shouldCalculateLumpSumValue() {

    final var lump = new DefinedLumpPension();
    lump.setAmount(new BigDecimal("12100.22"));
    lump.setCostPerHundredYearlyPay(new BigDecimal("722.00"));

    final var payer = new TestTaxPayerBuilder().setPensions(List.of(lump))
                                               .createTestTaxPayer();

    final var expected = new BigDecimal("26814.88");

    assertThat(underTest.calculate(payer)).isEqualTo(expected);

  }

}