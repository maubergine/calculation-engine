package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.model.DefaultBenefit;
import com.mariusrubin.calculationengine.serde.PredictedEmploymentInfo;
import com.mariusrubin.calculationengine.model.KnownEmploymentIncome;
import com.mariusrubin.calculationengine.model.PredictedEmploymentIncome;
import com.mariusrubin.calculationengine.test.TestTaxPayerBuilder;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ATotalBenefitsCalculator {

  private TotalBenefitsCalculator underTest = new TotalBenefitsCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new TotalBenefitsCalculator();
  }

  @Test
  public void shouldAddUpBenefitsAcrossEmployments() {
    //Benefits should also be rounded down.

    final var info1 = new PredictedEmploymentInfo();
    info1.setBase(new BigDecimal("60000.00"));
    final var ben1 = new DefaultBenefit();
    ben1.setAmount(new BigDecimal("112.55"));
    info1.setBenefits(List.of(ben1));
    final var inc1 = new PredictedEmploymentIncome(info1);

    final var inc2 = new KnownEmploymentIncome();
    final var ben2 = new DefaultBenefit();
    ben2.setAmount(new BigDecimal("141.67"));
    inc2.setBenefits(List.of(ben2));

    final var expected = new BigDecimal("253.00");

    final var payer = new TestTaxPayerBuilder().setIncomes(List.of(inc1, inc2))
                                               .createTestTaxPayer();

    assertThat(underTest.calculate(payer)).isEqualTo(expected);

  }

}