package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.UkTaxRates.FY17_18;
import static com.mariusrubin.calculationengine.UkTaxRates.FY18_19;
import static com.mariusrubin.calculationengine.UkTaxRates.FY19_20;
import static com.mariusrubin.calculationengine.UkTaxRates.FY20_21;
import static com.mariusrubin.calculationengine.UkTaxRates.FY21_22;
import static com.mariusrubin.calculationengine.UkTaxRates.FY23_24;
import static com.mariusrubin.calculationengine.api.IncomeType.DIVIDENDS;
import static com.mariusrubin.calculationengine.api.IncomeType.EMPLOYMENT;
import static com.mariusrubin.calculationengine.api.IncomeType.INTEREST;
import static com.mariusrubin.calculationengine.api.IncomeType.PENSION_CHARGE;
import static com.mariusrubin.calculationengine.api.RateLevel.ADDITIONAL;
import static com.mariusrubin.calculationengine.api.RateLevel.ADDITIONAL_NIL;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC_NIL;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER_NIL;
import static org.assertj.core.api.Assertions.assertThat;

import com.mariusrubin.calculationengine.CalcPrinter;
import com.mariusrubin.calculationengine.YamlFileLoader;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class ADefaultTaxCalculator {

  @Test
  public void shouldCalculateWithJustKnownEmployment() {

    final var loader = new YamlFileLoader("src/test/resources/knownEmployment.yml");

    final var taxPayer = loader.taxPayer();

    final var result = new DefaultTaxCalculator(FY17_18).calculate(taxPayer);

    CalcPrinter.print(result);

    assertThat(result.income().totalEmploymentPay()).isEqualTo("75335.00");
    assertThat(result.income().totalBenefitsAndExpenses()).isEqualTo("242.00");
    assertThat(result.income().netIncome()).isEqualTo("75577.00");
    assertThat(result.personalAllowance().allowance()).isEqualTo("11500.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("33500.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("6700.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).amount()).isEqualTo("30577.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).tax()).isEqualTo("12230.80");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().total().amount()).isEqualTo("64077.00");
    assertThat(result.incomeTax().total().tax()).isEqualTo("18930.80");


  }

  @Test
  public void shouldCalculateWithPensionContribution() {

    final var loader = new YamlFileLoader("src/test/resources/pensionContribsAndAllowanceLoss.yml");

    final var taxPayer = loader.taxPayer();

    final var result = new DefaultTaxCalculator(FY18_19).calculate(taxPayer);

    CalcPrinter.print(result);

    assertThat(result.income().totalEmploymentPay()).isEqualTo("106223.00");
    assertThat(result.income().totalBenefitsAndExpenses()).isEqualTo("441.00");
    assertThat(result.income().netIncome()).isEqualTo("106664.00");
    assertThat(result.personalAllowance().allowance()).isEqualTo("11850.00");

    assertThat(result.basicRateAdjustment().pensionPayments()).isEqualTo("15000.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("49500.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("9900.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).amount()).isEqualTo("45314.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).tax()).isEqualTo("18125.60");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().total().amount()).isEqualTo("94814.00");
    assertThat(result.incomeTax().total().tax()).isEqualTo("28025.60");

  }

  @Test
  public void shouldCalculateWithGiftsAndCarryForward() {

    final var loader = new YamlFileLoader("src/test/resources/giftsAndCarryForward.yml");

    final var taxPayer = loader.taxPayer();

    final var result = new DefaultTaxCalculator(FY19_20).calculate(taxPayer);

    CalcPrinter.print(result);

    assertThat(result.income().totalEmploymentPay()).isEqualTo("178948.00");
    assertThat(result.income().totalBenefitsAndExpenses()).isEqualTo("122.00");
    assertThat(result.income().netIncome()).isEqualTo("179070.00");
    assertThat(result.personalAllowance().allowance()).isEqualTo("0.00");

    assertThat(result.basicRateAdjustment().pensionPayments()).isEqualTo("51250.00");
    assertThat(result.basicRateAdjustment().giftAidPayments()).isEqualTo("150.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("88900.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("17780.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).amount()).isEqualTo("90170.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).tax()).isEqualTo("36068.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().total().amount()).isEqualTo("179070.00");
    assertThat(result.incomeTax().total().tax()).isEqualTo("53848.00");

  }

  @Test
  public void shouldCalculateUpToAdditionalIncludingDividendsAndInterest() {

    final var loader = new YamlFileLoader(
        "src/test/resources/additionalWithDividendsAndInterest.yml");

    final var taxPayer = loader.taxPayer();

    final var result = new DefaultTaxCalculator(FY20_21).calculate(taxPayer);

    CalcPrinter.print(result);

    assertThat(result.income().totalEmploymentPay()).isEqualTo("122311.00");
    assertThat(result.income().totalBenefitsAndExpenses()).isEqualTo("445.00");
    assertThat(result.income().totalFromAllEmployments()).isEqualTo("122756.00");
    assertThat(result.income().totalDividends()).isEqualTo("223500.00");
    assertThat(result.income().totalInterest()).isEqualTo("57.00");
    assertThat(result.income().netIncome()).isEqualTo("346313.00");

    assertThat(result.personalAllowance().allowance()).isEqualTo("0.00");

    assertThat(result.basicRateAdjustment().pensionPayments()).isEqualTo("0.00");
    assertThat(result.basicRateAdjustment().giftAidPayments()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("37500.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("7500.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).amount()).isEqualTo("85256.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).tax()).isEqualTo("34102.40");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(INTEREST, BASIC).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(INTEREST, BASIC).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(INTEREST, HIGHER).amount()).isEqualTo("57.00");
    assertThat(result.incomeTax().taxOn(INTEREST, HIGHER).tax()).isEqualTo("22.80");

    assertThat(result.incomeTax().taxOn(INTEREST, ADDITIONAL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(INTEREST, ADDITIONAL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, BASIC).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, BASIC).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, HIGHER_NIL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, HIGHER_NIL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, HIGHER).amount()).isEqualTo("27187.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, HIGHER).tax()).isEqualTo("8835.77");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL_NIL).amount()).isEqualTo("2000.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL_NIL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL).amount()).isEqualTo("194313.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL).tax()).isEqualTo("74033.25");

    assertThat(result.incomeTax().total().amount()).isEqualTo("346313.00");
    assertThat(result.incomeTax().total().tax()).isEqualTo("124494.22");

  }


  @Test
  public void shouldCalculatePaymentsOnAccountAndExpenses() {

    final var loader = new YamlFileLoader("src/test/resources/paymentsOnAccountExpenses.yml");

    final var taxPayer = loader.taxPayer();

    final var result = new DefaultTaxCalculator(FY21_22).calculate(taxPayer);

    CalcPrinter.print(result);

    assertThat(result.income().totalEmploymentPay()).isEqualTo("32254.00");
    assertThat(result.income().totalBenefitsAndExpenses()).isEqualTo("0.00");
    assertThat(result.income().totalAllowableExpenses()).isEqualTo("180.00");
    assertThat(result.income().totalFromAllEmployments()).isEqualTo("32074.00");
    assertThat(result.income().totalDividends()).isEqualTo("25123.00");
    assertThat(result.income().totalInterest()).isEqualTo("620.00");
    assertThat(result.income().netIncome()).isEqualTo("57817.00");

    assertThat(result.personalAllowance().allowance()).isEqualTo("12570.00");

    assertThat(result.basicRateAdjustment().pensionPayments()).isEqualTo("0.00");
    assertThat(result.basicRateAdjustment().giftAidPayments()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("19504.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("3900.80");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(INTEREST, BASIC_NIL).amount()).isEqualTo("500.00");
    assertThat(result.incomeTax().taxOn(INTEREST, BASIC_NIL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(INTEREST, BASIC).amount()).isEqualTo("120.00");
    assertThat(result.incomeTax().taxOn(INTEREST, BASIC).tax()).isEqualTo("24.00");

    assertThat(result.incomeTax().taxOn(INTEREST, HIGHER).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(INTEREST, HIGHER).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(INTEREST, ADDITIONAL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(INTEREST, ADDITIONAL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, BASIC).amount()).isEqualTo("17576.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, BASIC).tax()).isEqualTo("1318.20");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, HIGHER_NIL).amount()).isEqualTo("2000.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, HIGHER_NIL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, HIGHER).amount()).isEqualTo("5547.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, HIGHER).tax()).isEqualTo("1802.77");

    assertThat(result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL).amount()).isEqualTo("0.00");
    assertThat(result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL).tax()).isEqualTo("0.00");

    assertThat(result.incomeTax().total().amount()).isEqualTo("45247.00");
    assertThat(result.incomeTax().total().tax()).isEqualTo("7045.77");

    final var expectedJanDate = LocalDate.of(2023, 1, 31);
    final var expectedJulDate = LocalDate.of(2023, 7, 31);

    assertThat(result.paymentDue().totalPaymentsOnAccount()).isEqualTo("2363.57");
    assertThat(result.paymentDue().incomeTaxAsPercentage()).isEqualTo("0.6645");
    assertThat(result.paymentDue().paymentsOnAccountDue()).isTrue();
    assertThat(result.paymentDue().balance().amount()).isEqualTo("1133.02");
    assertThat(result.paymentDue().balance().date()).isEqualTo(expectedJanDate);
    assertThat(result.paymentDue().firstPayment().amount()).isEqualTo("1181.78");
    assertThat(result.paymentDue().firstPayment().date()).isEqualTo(expectedJanDate);
    assertThat(result.paymentDue().secondPayment().amount()).isEqualTo("1181.79");
    assertThat(result.paymentDue().secondPayment().date()).isEqualTo(expectedJulDate);
    assertThat(result.paymentDue().januaryTotal().amount()).isEqualTo("2314.80");
    assertThat(result.paymentDue().januaryTotal().date()).isEqualTo(expectedJanDate);
  }

  @Test
  public void shouldCalculatePredictedEarningsAndPensionTapers() {

    final var loader = new YamlFileLoader(
        "src/test/resources/predictedWithPensionTaperAndCharge.yml");

    final var taxPayer = loader.taxPayer();

    final var result = new DefaultTaxCalculator(FY23_24).calculate(taxPayer);

    CalcPrinter.print(result);

    assertThat(result.income().totalEmploymentPay()).isEqualTo("244581.00");
    assertThat(result.income().totalBenefitsAndExpenses()).isEqualTo("700.00");
    assertThat(result.income().totalFromAllEmployments()).isEqualTo("245281.00");
    assertThat(result.income().netIncome()).isEqualTo("245281.00");

    assertThat(result.personalAllowance().allowance()).isEqualTo("0.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).amount()).isEqualTo("37700.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, BASIC).tax()).isEqualTo("7540.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).amount()).isEqualTo("87440.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, HIGHER).tax()).isEqualTo("34976.00");

    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).amount()).isEqualTo("120141.00");
    assertThat(result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).tax()).isEqualTo("54063.45");

    assertThat(result.incomeTax().taxOn(PENSION_CHARGE, ADDITIONAL).amount()).isEqualTo("11640.00");
    assertThat(result.incomeTax().taxOn(PENSION_CHARGE, ADDITIONAL).tax()).isEqualTo("5238.00");

    assertThat(result.pension().allowance()).isEqualTo("34359.50");
    assertThat(result.pension().adjustedIncome()).isEqualTo("291281.00");
    assertThat(result.pension().thresholdIncome()).isEqualTo("268281.00");
    assertThat(result.pension().allowanceTaperAmount()).isEqualTo("25640.50");

  }


}
