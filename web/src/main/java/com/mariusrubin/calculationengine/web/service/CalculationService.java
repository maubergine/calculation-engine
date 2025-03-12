package com.mariusrubin.calculationengine.web.service;

import com.mariusrubin.calculationengine.TaxCalculator;
import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.PensionType;
import com.mariusrubin.calculationengine.api.RateLevel;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.api.calc.TaxCalc;
import com.mariusrubin.calculationengine.calc.DefaultTaxCalculator;
import com.mariusrubin.calculationengine.model.DefaultTaxPayer;
import com.mariusrubin.calculationengine.model.DividendIncome;
import com.mariusrubin.calculationengine.model.InterestIncome;
import com.mariusrubin.calculationengine.model.KnownEmploymentIncome;
import com.mariusrubin.calculationengine.model.KnownPension;
import com.mariusrubin.calculationengine.serde.TaxPayerInfo;
import com.mariusrubin.calculationengine.web.dto.CalculationRequest;
import com.mariusrubin.calculationengine.web.dto.CalculationResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

  private final TaxCalculator calculator;

  public CalculationService() {
    this.calculator = new DefaultTaxCalculator(UkTaxRates.FY24_25);
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

    // Set basic information
//    info.setName(request.getName());

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
}