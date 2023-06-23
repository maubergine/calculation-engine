package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.Gift;
import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.PensionType;
import com.mariusrubin.calculationengine.model.DefaultGift;
import com.mariusrubin.calculationengine.model.KnownPension;
import com.mariusrubin.calculationengine.model.calc.DefaultBasicRateAdjustmentCalc;
import com.mariusrubin.calculationengine.test.TestTaxPayerBuilder;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ABasicRateAdjustmentCalculator {

  private BasicRateAdjustmentCalculator underTest = new BasicRateAdjustmentCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new BasicRateAdjustmentCalculator();
  }

  @Test
  public void shouldCalculateTheAdjustmentInBasicRates() {

    final var totalGifts   = new BigDecimal("20.00");
    final var totalPension = new BigDecimal("40.00");

    final var adjustment = new BigDecimal("60.00");

    final var expected = new DefaultBasicRateAdjustmentCalc(totalGifts, totalPension, adjustment);

    assertThat(underTest.calculate(totalGifts, totalPension)).isEqualTo(expected);

  }


  @Test
  public void shouldCalculateBasedOnPayerAndRates() {

    final List<Gift> gifts = List.of(new DefaultGift(new BigDecimal("13.25")),
                                     new DefaultGift(new BigDecimal("9.00")));

    final var expectedGifts = new BigDecimal("29.00");

    final var pension = new KnownPension();
    pension.setType(PensionType.SIPP);
    pension.setAmount(new BigDecimal("120.00"));
    pension.setGrossedUp(false);

    final var expectedPension = new BigDecimal("150.00");

    final List<Pension> pensions = List.of(pension);

    final var payer = new TestTaxPayerBuilder().setPensions(pensions)
                                               .setGifts(gifts)
                                               .createTestTaxPayer();

    final var expectedAdjustment = new BigDecimal("179.00");

    final var expected = new DefaultBasicRateAdjustmentCalc(expectedGifts,
                                                            expectedPension,
                                                            expectedAdjustment);

    assertThat(underTest.calculate(payer, UkTaxRates.FY20_21)).isEqualTo(expected);

  }


}