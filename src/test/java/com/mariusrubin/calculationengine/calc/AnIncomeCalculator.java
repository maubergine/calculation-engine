package com.mariusrubin.calculationengine.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.PensionType;
import com.mariusrubin.calculationengine.model.DefaultBenefit;
import com.mariusrubin.calculationengine.model.DefaultExpense;
import com.mariusrubin.calculationengine.model.DefaultGift;
import com.mariusrubin.calculationengine.model.DividendIncome;
import com.mariusrubin.calculationengine.serde.PredictedEmploymentInfo;
import com.mariusrubin.calculationengine.model.InterestIncome;
import com.mariusrubin.calculationengine.model.KnownEmploymentIncome;
import com.mariusrubin.calculationengine.model.KnownPension;
import com.mariusrubin.calculationengine.model.PredictedEmploymentIncome;
import com.mariusrubin.calculationengine.model.calc.DefaultIncomeCalc;
import com.mariusrubin.calculationengine.test.TestTaxPayerBuilder;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class AnIncomeCalculator {

  private IncomeCalculator underTest = new IncomeCalculator();

  @BeforeEach
  public void setUp() {
    underTest = new IncomeCalculator();
  }

  @Test
  public void shouldCalculateIncome() {

    final var inc1 = new KnownEmploymentIncome();
    inc1.setAmount(new BigDecimal("95000.40"));
    final var exp1 = new DefaultExpense();
    exp1.setAmount(new BigDecimal("145.40"));
    inc1.setExpenses(List.of(exp1));
    final var ben1 = new DefaultBenefit();
    ben1.setAmount(new BigDecimal("120.3"));
    inc1.setBenefits(List.of(ben1));

    final var empInfo = new PredictedEmploymentInfo();
    empInfo.setBase(new BigDecimal("20000"));
    empInfo.setPredictedBonus(new BigDecimal("0.1"));
    empInfo.setSalarySacrifice(new BigDecimal("0.05"));

    final var inc2 = new PredictedEmploymentIncome(empInfo);

    final var dividend = new DividendIncome();
    dividend.setAmount(new BigDecimal("4000"));

    final var gift1 = new DefaultGift(new BigDecimal("100.00"));
    final var gift2 = new DefaultGift(new BigDecimal("76.40"));

    final var pension1 = new KnownPension();
    pension1.setType(PensionType.SIPP);
    pension1.setAmount(new BigDecimal("5000"));
    pension1.setGrossedUp(false);

    final var pension2 = new KnownPension();
    pension2.setType(PensionType.SIPP);
    pension2.setAmount(new BigDecimal("3000"));
    pension2.setGrossedUp(true);

    final var interest = new InterestIncome(new BigDecimal("400.70"));

    final var payer = new TestTaxPayerBuilder().setIncomes(List.of(inc1, inc2, dividend, interest))
                                               .setGifts(List.of(gift1, gift2))
                                               .setPensions(List.of(pension1, pension2))
                                               .createTestTaxPayer();

    //Net income = total income + benefits - expenses, ignoring reliefs - with rounding applied.
    //Net income = 95k+21k+4k+400+ 120      - 146 =
    final var netIncome = new BigDecimal("120374.00");

    //Adjusted net income = net income - grossed up pension contribs - losses - gifts.
    //Adjusted net income = 120374     - 9250 - 0 - 222
    final var adjustedNetIncome = new BigDecimal("110902.00");

    //Total employment income = total employment income (excluding benefits and expenses).
    //Total employment income = 95000 + 21000
    final var totalEmploymentPay = new BigDecimal("116000.00");

    //Total benefits and disallowed expenses = just total across all incomes and round.
    //Total benefits and disallowed expenses = 120
    final var totalBenefitsAndExpenses = new BigDecimal("120.00");

    //Total allowable expenses = just total across all incomes and round.
    //Total allowable expenses = 146
    final var totalAllowableExpenses = new BigDecimal("146.00");

    //Total from employments = total income + benefits - expenses, ignoring reliefs - with rounding.
    //Net income = 95k + 21k + 120      - 146 =
    final var totalFromAllEmployments = new BigDecimal("115974.00");

    //Total dividends = add up all dividends and round.
    //Total dividends = 4k
    final var totalDividends = new BigDecimal("4000.00");

    //Total interest = add up all untaxed interest and round
    //Total interest =
    final var totalInterest = new BigDecimal("400.00");

    final var expected = new DefaultIncomeCalc(
        netIncome,
        adjustedNetIncome,
        totalEmploymentPay,
        totalBenefitsAndExpenses,
        totalAllowableExpenses,
        totalFromAllEmployments,
        totalDividends,
        totalInterest
    );

    final var result = underTest.calculate(payer, UkTaxRates.FY21_22);

    assertThat(result).isEqualTo(expected);
  }
}