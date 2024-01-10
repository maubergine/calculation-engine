package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.PensionType.RAR;
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
public class ARarTotalCalculator {

  private RarTotalCalculator underTest = new RarTotalCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new RarTotalCalculator();
  }

  @Test
  public void shouldCalculateRarTotal() {

    final var pension1 = new DefinedLumpPension();
    pension1.setAmount(new BigDecimal("123.22"));

    final var pension2 = new DefinedLumpPension();
    pension2.setAmount(new BigDecimal("124.22"));

    final var pension3 = new KnownPension();
    pension3.setType(RAR);
    pension3.setAmount(new BigDecimal("133.85"));

    final var payer = new TestTaxPayerBuilder().setPensions(List.of(pension1, pension2, pension3))
                                               .createTestTaxPayer();

    final var expected = new BigDecimal("382.00");

    assertThat(underTest.calculate(payer)).isEqualTo(expected);

  }

}