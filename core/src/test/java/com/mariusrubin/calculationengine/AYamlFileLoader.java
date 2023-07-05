package com.mariusrubin.calculationengine;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AYamlFileLoader {

  @Test
  public void shouldLoadTheSample() {
    //This is mainly to test that the sample yaml stays in-sync with code changes.
    //So avoiding testing any of the inner logic of the mapping.

    final var loader   = new YamlFileLoader("src/test/resources/sample.yml");
    final var taxPayer = loader.taxPayer();

    assertThat(taxPayer.paymentsMade()).isEqualTo("2200.00");
    assertThat(taxPayer.taxPaidOverride()).isEqualTo("1123.22");

  }
}