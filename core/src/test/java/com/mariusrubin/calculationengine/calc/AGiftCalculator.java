package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.Gift;
import com.mariusrubin.calculationengine.model.DefaultGift;
import com.mariusrubin.calculationengine.test.TestTaxPayerBuilder;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AGiftCalculator {

  private GiftCalculator underTest = new GiftCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new GiftCalculator();
  }

  @Test
  public void shouldCalculateTotalGrossedUpValueForGifts() {
    final List<Gift> gifts = List.of(new DefaultGift(new BigDecimal("11.25")),
                                     new DefaultGift(new BigDecimal("9.00")));

    //Note that the total gift value is 20.25. We would expect this to be rounded up to 21.00 before
    //it gets grossed up.
    final var payer = new TestTaxPayerBuilder().setGifts(gifts).createTestTaxPayer();

    //We would also expect the grossed up amount to be rounded up as well.
    final var expected = new BigDecimal("27.00");

    assertThat(underTest.calculate(payer, UkTaxRates.FY20_21)).isEqualTo(expected);

  }


}