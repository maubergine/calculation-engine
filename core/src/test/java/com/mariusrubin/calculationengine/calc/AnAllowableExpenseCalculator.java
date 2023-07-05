package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.model.DefaultExpense;
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
public class AnAllowableExpenseCalculator {

  private AllowableExpenseCalculator underTest;

  @BeforeEach
  public void setUp() {
    underTest = new AllowableExpenseCalculator();
  }

  @Test
  public void shouldCalculateAllowableExpenses() {

    final var inc1 = new KnownEmploymentIncome();
    final var exp1 = new DefaultExpense();
    exp1.setAmount(new BigDecimal("123.00"));
    inc1.setExpenses(List.of(exp1));

    final var inc2 = new KnownEmploymentIncome();
    final var exp2 = new DefaultExpense();
    exp2.setAmount(new BigDecimal("456.00"));
    inc2.setExpenses(List.of(exp2));

    final var ignore = new DividendIncome();
    ignore.setAmount(new BigDecimal("222.00"));

    final List<Income> incomes = List.of(inc1, inc2, ignore);

    final var payer = new TestTaxPayerBuilder().setIncomes(incomes).createTestTaxPayer();

    assertThat(underTest.calculate(payer)).isEqualTo("579.00");

  }

}