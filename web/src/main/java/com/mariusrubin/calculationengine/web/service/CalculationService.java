package com.mariusrubin.calculationengine.web.service;

import com.mariusrubin.calculationengine.TaxCalculator;
import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.PensionType;
import com.mariusrubin.calculationengine.api.RateLevel;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.TaxCalc;
import com.mariusrubin.calculationengine.calc.AdjustedNetIncomeCalculator;
import com.mariusrubin.calculationengine.calc.AllowableExpenseCalculator;
import com.mariusrubin.calculationengine.calc.BasicRateAdjustmentCalculator;
import com.mariusrubin.calculationengine.calc.DefaultTaxCalculator;
import com.mariusrubin.calculationengine.calc.DefinedBenefitValueCalculator;
import com.mariusrubin.calculationengine.calc.GiftCalculator;
import com.mariusrubin.calculationengine.calc.GrossUpper;
import com.mariusrubin.calculationengine.calc.IncomeCalculator;
import com.mariusrubin.calculationengine.calc.IncomeTaxCalculator;
import com.mariusrubin.calculationengine.calc.NetIncomeCalculator;
import com.mariusrubin.calculationengine.calc.PaymentDueCalculator;
import com.mariusrubin.calculationengine.calc.PersonalAllowanceCalculator;
import com.mariusrubin.calculationengine.calc.RarTotalCalculator;
import com.mariusrubin.calculationengine.calc.SalarySacrificeCalculator;
import com.mariusrubin.calculationengine.calc.TotalBenefitsCalculator;
import com.mariusrubin.calculationengine.calc.TotalIncomeCalculator;
import com.mariusrubin.calculationengine.calc.TotalSippCalculator;
import com.mariusrubin.calculationengine.calc.pension.AdjustedIncomeCalculator;
import com.mariusrubin.calculationengine.calc.pension.PensionAllowanceCalculator;
import com.mariusrubin.calculationengine.calc.pension.PensionThresholdIncomeCalculator;
import com.mariusrubin.calculationengine.calc.pension.RelevantEarningsCalculator;
import com.mariusrubin.calculationengine.calc.pension.TotalEmployerPensionCalculator;
import com.mariusrubin.calculationengine.model.DefaultTaxPayer;
import com.mariusrubin.calculationengine.model.DividendIncome;
import com.mariusrubin.calculationengine.model.KnownEmploymentIncome;
import com.mariusrubin.calculationengine.model.KnownPension;
import com.mariusrubin.calculationengine.serde.TaxPayerInfo;
import com.mariusrubin.calculationengine.web.dto.AdjustedNetIncomeRequest;
import com.mariusrubin.calculationengine.web.dto.AllowableExpenseRequest;
import com.mariusrubin.calculationengine.web.dto.BasicRateAdjustmentRequest;
import com.mariusrubin.calculationengine.web.dto.CalculationRequest;
import com.mariusrubin.calculationengine.web.dto.CalculationResponse;
import com.mariusrubin.calculationengine.web.dto.DefinedBenefitValueRequest;
import com.mariusrubin.calculationengine.web.dto.GiftRequest;
import com.mariusrubin.calculationengine.web.dto.GrossUpperRequest;
import com.mariusrubin.calculationengine.web.dto.pension.AdjustedIncomeRequest;
import com.mariusrubin.calculationengine.web.dto.pension.PensionAllowanceRequest;
import com.mariusrubin.calculationengine.web.dto.pension.ThresholdIncomeRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

  private final TaxCalculator calculator;
  
  // Individual calculators
  private final AdjustedNetIncomeCalculator adjustedNetIncomeCalculator;
  private final AllowableExpenseCalculator allowableExpenseCalculator;
  private final BasicRateAdjustmentCalculator basicRateAdjustmentCalculator;
  private final DefinedBenefitValueCalculator definedBenefitValueCalculator;
  private final GiftCalculator giftCalculator;
  private final GrossUpper grossUpper;
  private final IncomeCalculator incomeCalculator;
  private final IncomeTaxCalculator incomeTaxCalculator;
  private final NetIncomeCalculator netIncomeCalculator;
  private final PaymentDueCalculator paymentDueCalculator;
  private final PersonalAllowanceCalculator personalAllowanceCalculator;
  private final RarTotalCalculator rarTotalCalculator;
  private final SalarySacrificeCalculator salarySacrificeCalculator;
  private final TotalBenefitsCalculator totalBenefitsCalculator;
  private final TotalIncomeCalculator totalIncomeCalculator;
  private final TotalSippCalculator totalSippCalculator;
  
  // Pension calculators
  private final AdjustedIncomeCalculator adjustedIncomeCalculator;
  private final PensionAllowanceCalculator pensionAllowanceCalculator;
  private final PensionThresholdIncomeCalculator pensionThresholdIncomeCalculator;
  private final RelevantEarningsCalculator relevantEarningsCalculator;
  private final TotalEmployerPensionCalculator totalEmployerPensionCalculator;

  public CalculationService() {
    this.calculator = new DefaultTaxCalculator(UkTaxRates.FY24_25);
    
    // Initialize individual calculators
    this.adjustedNetIncomeCalculator = new AdjustedNetIncomeCalculator();
    this.allowableExpenseCalculator = new AllowableExpenseCalculator();
    this.basicRateAdjustmentCalculator = new BasicRateAdjustmentCalculator();
    this.definedBenefitValueCalculator = new DefinedBenefitValueCalculator();
    this.giftCalculator = new GiftCalculator();
    this.grossUpper = new GrossUpper();
    this.incomeCalculator = new IncomeCalculator();
    this.incomeTaxCalculator = new IncomeTaxCalculator();
    this.netIncomeCalculator = new NetIncomeCalculator();
    this.paymentDueCalculator = new PaymentDueCalculator();
    this.personalAllowanceCalculator = new PersonalAllowanceCalculator();
    this.rarTotalCalculator = new RarTotalCalculator();
    this.salarySacrificeCalculator = new SalarySacrificeCalculator();
    this.totalBenefitsCalculator = new TotalBenefitsCalculator();
    this.totalIncomeCalculator = new TotalIncomeCalculator();
    this.totalSippCalculator = new TotalSippCalculator();
    
    // Initialize pension calculators
    this.adjustedIncomeCalculator = new AdjustedIncomeCalculator();
    this.pensionAllowanceCalculator = new PensionAllowanceCalculator();
    this.pensionThresholdIncomeCalculator = new PensionThresholdIncomeCalculator();
    this.relevantEarningsCalculator = new RelevantEarningsCalculator();
    this.totalEmployerPensionCalculator = new TotalEmployerPensionCalculator();
  }

  public CalculationResponse calculateTax(CalculationRequest request) {
    // Convert web request to TaxPayer object
    TaxPayer taxPayer = convertToTaxPayer(request);
    
    // Perform calculation using the engine
    TaxCalc result = calculator.calculate(taxPayer);
    
    // Convert result to web response
    return convertToResponse(result);
  }

  private TaxPayer convertToTaxPayer(CalculationRequest request) {
    // Create a TaxPayerInfo from the request
    TaxPayerInfo info = new TaxPayerInfo();

    // Set employment income
    if (request.getEmploymentIncome() != null) {
      final var empIncome = new KnownEmploymentIncome();
      empIncome.setAmount(request.getEmploymentIncome());
      info.setKnownEmployments(List.of(empIncome));
    }

    // Set dividend income
    if (request.getDividendIncome() != null) {
      final var divIncome = new DividendIncome();
      divIncome.setAmount(request.getDividendIncome());
      info.setDividends(List.of(divIncome));
    }

    // Set interest income
    if (request.getInterestIncome() != null) {
      info.setUntaxedInterest(request.getInterestIncome());
    }

    final var pensions = new ArrayList<KnownPension>();

    // Set pension contributions
    if (request.getPersonalPensionContribution() != null) {
      final var pension = new KnownPension();
      pension.setAmount(request.getPersonalPensionContribution());
      pension.setType(PensionType.SIPP);
      pensions.add(pension);
    }

    if (request.getEmployerPensionContribution() != null) {
      final var pension = new KnownPension();
      pension.setAmount(request.getEmployerPensionContribution());
      pension.setType(PensionType.EMPLOYER);
      pensions.add(pension);
    }

    info.setKnownPensions(pensions);

    // Convert TaxPayerInfo to TaxPayer
    return new DefaultTaxPayer(info);
  }

  private CalculationResponse convertToResponse(TaxCalc result) {
    CalculationResponse response = new CalculationResponse();

    // Extract basic calculation results
    response.setTotalIncome(result.income().netIncome());
    response.setTaxableIncome(result.incomeTax().total().amount());
    response.setIncomeTax(result.incomeTax().totalTaxOn(IncomeType.EMPLOYMENT).tax());
    response.setDividendTax(result.incomeTax().totalTaxOn(IncomeType.DIVIDENDS).tax());
    response.setTotalTaxDue(result.incomeTax().total().tax());

    // Extract income tax breakdown by band
    response.setBasicRateIncomeTax(result.incomeTax()
                                         .taxOn(IncomeType.EMPLOYMENT, RateLevel.BASIC)
                                         .tax());

    response.setHigherRateIncomeTax(result.incomeTax()
                                          .taxOn(IncomeType.EMPLOYMENT, RateLevel.HIGHER)
                                          .tax());

    response.setAdditionalRateIncomeTax(result.incomeTax()
                                              .taxOn(IncomeType.EMPLOYMENT, RateLevel.ADDITIONAL)
                                              .tax());

    // Extract dividend tax breakdown by band
    response.setBasicRateDividendTax(result.incomeTax()
                                           .taxOn(IncomeType.DIVIDENDS, RateLevel.BASIC)
                                           .tax());

    response.setHigherRateDividendTax(result.incomeTax()
                                            .taxOn(IncomeType.DIVIDENDS, RateLevel.HIGHER)
                                            .tax());

    response.setAdditionalRateDividendTax(result.incomeTax()
                                                .taxOn(IncomeType.DIVIDENDS, RateLevel.ADDITIONAL)
                                                .tax());

    // Set dividend information
    response.setDividendIncome(result.income().totalDividends());
    response.setDividendAllowance(UkTaxRates.FY24_25.dividendRates().allowance());
    
    // Set pension information
    response.setPensionCharge(result.incomeTax().totalTaxOn(IncomeType.PENSION_CHARGE).tax());
    response.setPensionAnnualAllowance(result.pension().allowance() );

    // Set additional information
    response.setPersonalAllowance(result.personalAllowance().allowance());
    response.setTaxYear(UkTaxRates.FY24_25.financialYear());

    return response;
  }
  
  // Individual calculator methods
  
  public BigDecimal calculateAdjustedNetIncome(AdjustedNetIncomeRequest request) {
    return adjustedNetIncomeCalculator.calculate(
        request.getTotalTaxableIncome(),
        request.getTotalPensionContribution(),
        request.getTotalGifts());
  }
  
  public BigDecimal calculateAllowableExpense(AllowableExpenseRequest request) {
    return request.getExpenseAmount(); // Simplified implementation
  }
  
//  public BigDecimal calculateBasicRateAdjustment(BasicRateAdjustmentRequest request) {
//    return basicRateAdjustmentCalculator.calculate(
//        request.getPensionContribution(),
//        request.getUkTaxRates());
//  }
//
//  public BigDecimal calculateDefinedBenefitValue(DefinedBenefitValueRequest request) {
//    return definedBenefitValueCalculator.calculate(
//        request.getIncrease(),
//        request.getUkTaxRates());
//  }
//
//  public BigDecimal calculateGift(GiftRequest request) {
//    return giftCalculator.calculate(
//        request.getGiftAmount(),
//        request.getUkTaxRates());
//  }
//
//  public BigDecimal calculateGrossUpper(GrossUpperRequest request) {
//    return grossUpper.grossUp(
//        request.getNetAmount(),
//        request.getRate());
//  }
//
//  // Pension calculator methods
//
//  public BigDecimal calculatePensionAdjustedIncome(AdjustedIncomeRequest request) {
//    return adjustedIncomeCalculator.calculate(
//        request.getNetIncome(),
//        request.getEmployerPension());
//  }
//
//  public BigDecimal calculatePensionAllowance(PensionAllowanceRequest request) {
//    return pensionAllowanceCalculator.calculate(
//        request.getAdjustedIncome(),
//        request.getThresholdIncome(),
//        request.getUkTaxRates());
//  }
  
  public BigDecimal calculatePensionThresholdIncome(ThresholdIncomeRequest request) {
    return pensionThresholdIncomeCalculator.calculate(
        request.getNetIncome(),
        request.getEmployeePension());
  }
}
  
//  public BigDecimal calculateBasicRateAdjustment(BasicRateAdjustmentRequest request) {
//    return basicRateAdjustmentCalculator.calculate(
//        request.getPensionContribution(),
//        request.getPensionContribution());
//  }
//
//  public BigDecimal calculateDefinedBenefitValue(DefinedBenefitValueRequest request) {
//    return definedBenefitValueCalculator.calculate(
//        request.getIncrease(),
//        request.getUkTaxRates());
//  }
//
//  public BigDecimal calculateGift(GiftRequest request) {
//    return giftCalculator.calculate(
//        request.getGiftAmount(),
//        request.getUkTaxRates());
//  }
//
//  public BigDecimal calculateGrossUpper(GrossUpperRequest request) {
//    return grossUpper.grossUp(
//        request.getNetAmount(),
//        request.getRate());
//  }
//
//  public BigDecimal calculateIncome(IncomeRequest request) {
//    return request.getIncomeAmount(); // Simplified implementation
//  }
//
//  public BigDecimal calculateIncomeTax(IncomeTaxRequest request) {
//    return request.getTaxableAmount().multiply(request.getRate()); // Simplified implementation
//  }
//
//  public BigDecimal calculateNetIncome(NetIncomeRequest request) {
//    return netIncomeCalculator.calculate(request.getTaxPayer()); // Simplified
//  }
//
//  public BigDecimal calculatePaymentDue(PaymentDueRequest request) {
//    return request.getTaxDue(); // Simplified implementation
//  }
//
//  public BigDecimal calculatePersonalAllowance(PersonalAllowanceRequest request) {
//    return personalAllowanceCalculator.calculate(
//        request.getAdjustedNetIncome(),
//        request.getUkTaxRates());
//  }
//
//  public BigDecimal calculateRarTotal(RarTotalRequest request) {
//    return rarTotalCalculator.calculate(
//        request.getTaxPayer(),
//        request.getUkTaxRates());
//  }
//
//  public BigDecimal calculateSalarySacrifice(SalarySacrificeRequest request) {
//    return salarySacrificeCalculator.calculate(
//        request.getSalary(),
//        request.getEmployeePension(),
//        request.getEmployerPension());
//  }
//
//  public BigDecimal calculateTotalBenefits(TotalBenefitsRequest request) {
//    return totalBenefitsCalculator.calculate(request.getTaxPayer());
//  }
//
//  public BigDecimal calculateTotalIncome(TotalIncomeRequest request) {
//    return totalIncomeCalculator.calculate(request.getTaxPayer());
//  }
//
//  public BigDecimal calculateTotalSipp(TotalSippRequest request) {
//    return totalSippCalculator.calculate(
//        request.getTaxPayer(),
//        request.getUkTaxRates());
//  }
//
//  // Pension calculator methods
//
//  public BigDecimal calculatePensionAdjustedIncome(AdjustedIncomeRequest request) {
//    return adjustedIncomeCalculator.calculate(
//        request.getNetIncome(),
//        request.getEmployerPension());
//  }
//
//  public BigDecimal calculatePensionAllowance(PensionAllowanceRequest request) {
//    return pensionAllowanceCalculator.calculate(
//        request.getAdjustedIncome(),
//        request.getThresholdIncome(),
//        request.getUkTaxRates());
//  }
//
//  public BigDecimal calculatePensionThresholdIncome(ThresholdIncomeRequest request) {
//    return pensionThresholdIncomeCalculator.calculate(
//        request.getNetIncome(),
//        request.getEmployeePension());
//  }
//
//  public BigDecimal calculateRelevantEarnings(RelevantEarningsRequest request) {
//    return relevantEarningsCalculator.calculate(request.getTaxPayer());
//  }
//
//  public BigDecimal calculateTotalEmployerPension(TotalEmployerPensionRequest request) {
//    return totalEmployerPensionCalculator.calculate(request.getTaxPayer());
//  }
//}