package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.IncomeType.EMPLOYMENT;
import static com.mariusrubin.calculationengine.api.IncomeType.INTEREST;
import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.model.DefaultBenefit;
import com.mariusrubin.calculationengine.model.DividendIncome;
import com.mariusrubin.calculationengine.serde.PredictedEmploymentInfo;
import com.mariusrubin.calculationengine.model.InterestIncome;
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
public class ATotalIncomeCalculator {

  private TotalIncomeCalculator underTest = new TotalIncomeCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new TotalIncomeCalculator();
  }

  @Test
  public void shouldAddUpAllIncomes() {

    final var info1 = new PredictedEmploymentInfo();
    info1.setBase(new BigDecimal("60000.25"));
    final var ben1 = new DefaultBenefit();
    ben1.setAmount(new BigDecimal("112.55"));
    info1.setBenefits(List.of(ben1));
    final var inc1 = new PredictedEmploymentIncome(info1);

    final var inc2 = new DividendIncome();
    inc2.setAmount(new BigDecimal("25000.00"));

    final var inc3 = new InterestIncome(new BigDecimal("200.40"));

    final var payer = new TestTaxPayerBuilder().setIncomes(List.of(inc1, inc2, inc3))
                                               .createTestTaxPayer();

    final var expected = new BigDecimal("85200.00");

    assertThat(underTest.calculate(payer)).isEqualTo(expected);

  }

  @Test
  public void shouldFilterIncomes() {

    final var info1 = new PredictedEmploymentInfo();
    info1.setBase(new BigDecimal("60000.25"));
    final var ben1 = new DefaultBenefit();
    ben1.setAmount(new BigDecimal("112.55"));
    info1.setBenefits(List.of(ben1));
    final var inc1 = new PredictedEmploymentIncome(info1);

    final var inc2 = new DividendIncome();
    inc2.setAmount(new BigDecimal("25000.00"));

    final var inc3 = new InterestIncome(new BigDecimal("200.00"));

    final var payer = new TestTaxPayerBuilder().setIncomes(List.of(inc1, inc2, inc3))
                                               .createTestTaxPayer();

    final var expected = new BigDecimal("60200.00");

    assertThat(underTest.calculate(payer, EMPLOYMENT, INTEREST)).isEqualTo(expected);

  }

}