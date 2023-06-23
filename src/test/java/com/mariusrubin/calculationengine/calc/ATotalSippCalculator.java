package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.UkTaxRates;
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
public class ATotalSippCalculator {

  private TotalSippCalculator underTest = new TotalSippCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new TotalSippCalculator();
  }

  @Test
  public void shouldAddAndGrossUpIfNeeded() {
    final var pension1 = new KnownPension();
    pension1.setType(PensionType.EMPLOYER);
    pension1.setAmount(new BigDecimal("1520.25"));
    final var pension2 = new KnownPension();
    pension2.setType(PensionType.SIPP);
    pension2.setAmount(new BigDecimal("200.00"));
    pension2.setGrossedUp(false);
    final var pension3 = new KnownPension();
    pension3.setType(PensionType.SIPP);
    pension3.setAmount(new BigDecimal("810.00"));
    pension3.setGrossedUp(true);

    final var payer = new TestTaxPayerBuilder().setPensions(List.of(pension1, pension2, pension3))
                                               .createTestTaxPayer();

    final var expected = new BigDecimal("1060.00");

    assertThat(underTest.calculate(payer, UkTaxRates.FY23_24)).isEqualTo(expected);

  }

}