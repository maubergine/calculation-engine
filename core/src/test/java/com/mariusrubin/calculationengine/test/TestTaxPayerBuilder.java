package com.mariusrubin.calculationengine.test;

import com.mariusrubin.calculationengine.api.Gift;
import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.Pension;
import java.math.BigDecimal;
import java.util.List;

public class TestTaxPayerBuilder {

  private List<Income>  incomes;
  private BigDecimal    taxPaidOverride;
  private List<Pension> pensions;
  private List<Gift>    gifts;
  private BigDecimal    pensionAllowanceCarryOver;
  private BigDecimal    paymentsMade;

  public TestTaxPayerBuilder setIncomes(List<Income> incomes) {
    this.incomes = incomes;
    return this;
  }

  public TestTaxPayerBuilder setTaxPaidOverride(BigDecimal taxPaidOverride) {
    this.taxPaidOverride = taxPaidOverride;
    return this;
  }

  public TestTaxPayerBuilder setPensions(List<Pension> pensions) {
    this.pensions = pensions;
    return this;
  }

  public TestTaxPayerBuilder setGifts(List<Gift> gifts) {
    this.gifts = gifts;
    return this;
  }

  public TestTaxPayerBuilder setPensionAllowanceCarryOver(BigDecimal pensionAllowanceCarryOver) {
    this.pensionAllowanceCarryOver = pensionAllowanceCarryOver;
    return this;
  }

  public TestTaxPayerBuilder setPaymentsMade(BigDecimal paymentsMade) {
    this.paymentsMade = paymentsMade;
    return this;
  }

  public TestTaxPayer createTestTaxPayer() {
    return new TestTaxPayer(incomes,
                            taxPaidOverride,
                            pensions,
                            gifts,
                            pensionAllowanceCarryOver,
                            paymentsMade);
  }
}