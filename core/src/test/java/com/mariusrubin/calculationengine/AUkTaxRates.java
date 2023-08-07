package com.mariusrubin.calculationengine;

import static com.mariusrubin.calculationengine.UkTaxRates.FY22_23;
import static com.mariusrubin.calculationengine.UkTaxRates.FY23_24;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AUkTaxRates {

  @Test
  public void shouldRetrieveRatesByDate() {

    final var fy2022Simple   = LocalDate.of(2022, 6, 1);
    final var fy2022Boundary = LocalDate.of(2023, 4, 5);

    final var fy2023Simple   = LocalDate.of(2023, 9, 30);
    final var fy2023Boundary = LocalDate.of(2023, 4, 6);

    final var unconfigured = LocalDate.of(2025, 5, 20);

    assertThat(UkTaxRates.forDate(fy2022Simple)).isEqualTo(FY22_23);
    assertThat(UkTaxRates.forDate(fy2022Boundary)).isEqualTo(FY22_23);
    assertThat(UkTaxRates.forDate(fy2023Simple)).isEqualTo(FY23_24);
    assertThat(UkTaxRates.forDate(fy2023Boundary)).isEqualTo(FY23_24);

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> UkTaxRates.forDate(unconfigured));

  }

  @Test
  public void shouldReturnRatesForAGivenLevel() {
    assertThat(FY22_23.dividendRates().forLevel(HIGHER).rate()).isEqualTo("33.75");
  }
}