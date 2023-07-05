package com.mariusrubin.calculationengine.calc.pension;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.api.PensionType;
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
public class ATotalEmployerPensionCalculator {

  private TotalEmployerPensionCalculator underTest = new TotalEmployerPensionCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new TotalEmployerPensionCalculator();
  }

  @Test
  public void shouldAddUpEmployerPensions() {

    final var pension1 = new KnownPension();
    pension1.setType(PensionType.EMPLOYER);
    pension1.setAmount(new BigDecimal("1520.25"));
    final var pension2 = new KnownPension();
    pension2.setType(PensionType.SIPP);
    pension2.setAmount(new BigDecimal("200.00"));
    final var pension3 = new KnownPension();
    pension3.setType(PensionType.EMPLOYER);
    pension3.setAmount(new BigDecimal("810.15"));

    final var payer = new TestTaxPayerBuilder().setPensions(List.of(pension1, pension2, pension3))
                                               .createTestTaxPayer();

    final var expected = new BigDecimal("2330.40");

    assertThat(underTest.calculate(payer)).isEqualTo(expected);

  }

}