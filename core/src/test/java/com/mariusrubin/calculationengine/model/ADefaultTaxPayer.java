package com.mariusrubin.calculationengine.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.serde.TaxPayerInfo;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ADefaultTaxPayer {

  @Test
  public void shouldScaleTaxPaidOverride() {
    final var info = new TaxPayerInfo();
    info.setTaxPaidOverride(new BigDecimal("123"));
    final var taxPayer = new DefaultTaxPayer(info);
    assertThat(taxPayer.taxPaidOverride()).isEqualTo("123.00");
  }

  @Test
  public void shouldReflectNoOverride() {
    final var info     = new TaxPayerInfo();
    final var taxPayer = new DefaultTaxPayer(info);
    assertThat(taxPayer.taxPaidOverride()).isNull();
  }

  @Test
  public void shouldScalePaymentsMade() {
    final var info = new TaxPayerInfo();
    info.setPaymentsMade(new BigDecimal("456"));
    final var taxPayer = new DefaultTaxPayer(info);
    assertThat(taxPayer.paymentsMade()).isEqualTo("456.00");
  }

  @Test
  public void shouldZeroPaymentsMade() {
    final var info     = new TaxPayerInfo();
    final var taxPayer = new DefaultTaxPayer(info);
    assertThat(taxPayer.paymentsMade()).isEqualTo("0.00");

  }

  @Test
  public void shouldScaleCarryForward() {
    final var info = new TaxPayerInfo();
    info.setPensionAllowanceCarryForward(new BigDecimal("789"));
    final var taxPayer = new DefaultTaxPayer(info);
    assertThat(taxPayer.pensionAllowanceCarryForward()).isEqualTo("789.00");
  }

  @Test
  public void shouldZeroCarryForward() {
    final var info     = new TaxPayerInfo();
    final var taxPayer = new DefaultTaxPayer(info);
    assertThat(taxPayer.pensionAllowanceCarryForward()).isEqualTo("0.00");
  }


}