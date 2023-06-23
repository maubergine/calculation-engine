package com.mariusrubin.calculationengine.calc.pension;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.model.DefaultBenefit;
import com.mariusrubin.calculationengine.model.DividendIncome;
import com.mariusrubin.calculationengine.model.KnownEmploymentIncome;
import com.mariusrubin.calculationengine.test.TestTaxPayerBuilder;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ARelevantEarningsCalculator {

  private RelevantEarningsCalculator underTest = new RelevantEarningsCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new RelevantEarningsCalculator();
  }

  @Test
  public void shouldCalculateRelevantEarnings() {

    final var inc1 = new KnownEmploymentIncome();
    final var ben1 = new DefaultBenefit();
    ben1.setAmount(new BigDecimal("150.00"));
    inc1.setAmount(new BigDecimal("1000.00"));
    inc1.setBenefits(List.of(ben1));
    final var inc2 = new DividendIncome();
    inc2.setAmount(new BigDecimal("2000.00"));

    final var payer = new TestTaxPayerBuilder().setIncomes(List.of(inc1, inc2))
                                               .createTestTaxPayer();

    //Under most circumstances only employment incomes count as relevant earnings.
    final var expected = new BigDecimal("1150.00");

    assertThat(underTest.calculate(payer)).isEqualTo(expected);

  }

}