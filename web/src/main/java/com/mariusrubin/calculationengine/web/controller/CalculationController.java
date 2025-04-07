package com.mariusrubin.calculationengine.web.controller;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.web.dto.CalculationRequest;
import com.mariusrubin.calculationengine.web.dto.CalculationResponse;
import com.mariusrubin.calculationengine.web.dto.AdjustedNetIncomeRequest;
import com.mariusrubin.calculationengine.web.dto.AllowableExpenseRequest;
import com.mariusrubin.calculationengine.web.dto.BasicRateAdjustmentRequest;
import com.mariusrubin.calculationengine.web.dto.DefinedBenefitValueRequest;
import com.mariusrubin.calculationengine.web.dto.GiftRequest;
import com.mariusrubin.calculationengine.web.dto.GrossUpperRequest;
import com.mariusrubin.calculationengine.web.dto.pension.AdjustedIncomeRequest;
import com.mariusrubin.calculationengine.web.dto.pension.PensionAllowanceRequest;
import com.mariusrubin.calculationengine.web.dto.pension.ThresholdIncomeRequest;
import com.mariusrubin.calculationengine.web.service.CalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculation")
public class CalculationController {

  private final CalculationService calculationService;

  @Autowired
  public CalculationController(CalculationService calculationService) {
    this.calculationService = calculationService;
  }

  @PostMapping
  public ResponseEntity<CalculationResponse> calculateTax(@Valid @RequestBody CalculationRequest request) {
    CalculationResponse response = calculationService.calculateTax(request);
    return ResponseEntity.ok(response);
  }
  
  @GetMapping("/tax-years")
  public ResponseEntity<List<UkTaxRates>> getTaxYears() {
    return ResponseEntity.ok(Arrays.asList(UkTaxRates.values()));
  }
  
  // Individual calculator endpoints
  
  @PostMapping("/adjusted-net-income")
  public ResponseEntity<Map<String, BigDecimal>> calculateAdjustedNetIncome(
      @Valid @RequestBody AdjustedNetIncomeRequest request) {
    BigDecimal result = calculationService.calculateAdjustedNetIncome(request);
    return ResponseEntity.ok(Map.of("adjustedNetIncome", result));
  }
  
  @PostMapping("/allowable-expense")
  public ResponseEntity<Map<String, BigDecimal>> calculateAllowableExpense(
      @Valid @RequestBody AllowableExpenseRequest request) {
    BigDecimal result = calculationService.calculateAllowableExpense(request);
    return ResponseEntity.ok(Map.of("allowableExpense", result));
  }
  
//  @PostMapping("/basic-rate-adjustment")
//  public ResponseEntity<Map<String, BigDecimal>> calculateBasicRateAdjustment(
//      @Valid @RequestBody BasicRateAdjustmentRequest request) {
//    BigDecimal result = calculationService.calculateBasicRateAdjustment(request);
//    return ResponseEntity.ok(Map.of("basicRateAdjustment", result));
//  }
//
//  @PostMapping("/defined-benefit-value")
//  public ResponseEntity<Map<String, BigDecimal>> calculateDefinedBenefitValue(
//      @Valid @RequestBody DefinedBenefitValueRequest request) {
//    BigDecimal result = calculationService.calculateDefinedBenefitValue(request);
//    return ResponseEntity.ok(Map.of("definedBenefitValue", result));
//  }
//
//  @PostMapping("/gift")
//  public ResponseEntity<Map<String, BigDecimal>> calculateGift(
//      @Valid @RequestBody GiftRequest request) {
//    BigDecimal result = calculationService.calculateGift(request);
//    return ResponseEntity.ok(Map.of("giftAmount", result));
//  }
//
//  @PostMapping("/gross-upper")
//  public ResponseEntity<Map<String, BigDecimal>> calculateGrossUpper(
//      @Valid @RequestBody GrossUpperRequest request) {
//    BigDecimal result = calculationService.calculateGrossUpper(request);
//    return ResponseEntity.ok(Map.of("grossUpper", result));
//  }
//
//  @PostMapping("/income")
//  public ResponseEntity<Map<String, BigDecimal>> calculateIncome(
//      @Valid @RequestBody IncomeRequest request) {
//    BigDecimal result = calculationService.calculateIncome(request);
//    return ResponseEntity.ok(Map.of("income", result));
//  }
//
//  @PostMapping("/income-tax")
//  public ResponseEntity<Map<String, BigDecimal>> calculateIncomeTax(
//      @Valid @RequestBody IncomeTaxRequest request) {
//    BigDecimal result = calculationService.calculateIncomeTax(request);
//    return ResponseEntity.ok(Map.of("incomeTax", result));
//  }
//
//  @PostMapping("/net-income")
//  public ResponseEntity<Map<String, BigDecimal>> calculateNetIncome(
//      @Valid @RequestBody NetIncomeRequest request) {
//    BigDecimal result = calculationService.calculateNetIncome(request);
//    return ResponseEntity.ok(Map.of("netIncome", result));
//  }
//
//  @PostMapping("/payment-due")
//  public ResponseEntity<Map<String, BigDecimal>> calculatePaymentDue(
//      @Valid @RequestBody PaymentDueRequest request) {
//    BigDecimal result = calculationService.calculatePaymentDue(request);
//    return ResponseEntity.ok(Map.of("paymentDue", result));
//  }
//
//  @PostMapping("/personal-allowance")
//  public ResponseEntity<Map<String, BigDecimal>> calculatePersonalAllowance(
//      @Valid @RequestBody PersonalAllowanceRequest request) {
//    BigDecimal result = calculationService.calculatePersonalAllowance(request);
//    return ResponseEntity.ok(Map.of("personalAllowance", result));
//  }
//
//  @PostMapping("/rar-total")
//  public ResponseEntity<Map<String, BigDecimal>> calculateRarTotal(
//      @Valid @RequestBody RarTotalRequest request) {
//    BigDecimal result = calculationService.calculateRarTotal(request);
//    return ResponseEntity.ok(Map.of("rarTotal", result));
//  }
//
//  @PostMapping("/salary-sacrifice")
//  public ResponseEntity<Map<String, BigDecimal>> calculateSalarySacrifice(
//      @Valid @RequestBody SalarySacrificeRequest request) {
//    BigDecimal result = calculationService.calculateSalarySacrifice(request);
//    return ResponseEntity.ok(Map.of("salarySacrifice", result));
//  }
//
//  @PostMapping("/total-benefits")
//  public ResponseEntity<Map<String, BigDecimal>> calculateTotalBenefits(
//      @Valid @RequestBody TotalBenefitsRequest request) {
//    BigDecimal result = calculationService.calculateTotalBenefits(request);
//    return ResponseEntity.ok(Map.of("totalBenefits", result));
//  }
//
//  @PostMapping("/total-income")
//  public ResponseEntity<Map<String, BigDecimal>> calculateTotalIncome(
//      @Valid @RequestBody TotalIncomeRequest request) {
//    BigDecimal result = calculationService.calculateTotalIncome(request);
//    return ResponseEntity.ok(Map.of("totalIncome", result));
//  }
//
//  @PostMapping("/total-sipp")
//  public ResponseEntity<Map<String, BigDecimal>> calculateTotalSipp(
//      @Valid @RequestBody TotalSippRequest request) {
//    BigDecimal result = calculationService.calculateTotalSipp(request);
//    return ResponseEntity.ok(Map.of("totalSipp", result));
//  }
//
//  // Pension calculator endpoints
//
//  @PostMapping("/pension/adjusted-income")
//  public ResponseEntity<Map<String, BigDecimal>> calculatePensionAdjustedIncome(
//      @Valid @RequestBody AdjustedIncomeRequest request) {
//    BigDecimal result = calculationService.calculatePensionAdjustedIncome(request);
//    return ResponseEntity.ok(Map.of("adjustedIncome", result));
//  }
//
//  @PostMapping("/pension/allowance")
//  public ResponseEntity<Map<String, BigDecimal>> calculatePensionAllowance(
//      @Valid @RequestBody PensionAllowanceRequest request) {
//    BigDecimal result = calculationService.calculatePensionAllowance(request);
//    return ResponseEntity.ok(Map.of("pensionAllowance", result));
//  }
//
//  @PostMapping("/pension/threshold-income")
//  public ResponseEntity<Map<String, BigDecimal>> calculatePensionThresholdIncome(
//      @Valid @RequestBody ThresholdIncomeRequest request) {
//    BigDecimal result = calculationService.calculatePensionThresholdIncome(request);
//    return ResponseEntity.ok(Map.of("thresholdIncome", result));
//  }
//
//  @PostMapping("/pension/relevant-earnings")
//  public ResponseEntity<Map<String, BigDecimal>> calculateRelevantEarnings(
//      @Valid @RequestBody RelevantEarningsRequest request) {
//    BigDecimal result = calculationService.calculateRelevantEarnings(request);
//    return ResponseEntity.ok(Map.of("relevantEarnings", result));
//  }
//
//  @PostMapping("/pension/total-employer")
//  public ResponseEntity<Map<String, BigDecimal>> calculateTotalEmployerPension(
//      @Valid @RequestBody TotalEmployerPensionRequest request) {
//    BigDecimal result = calculationService.calculateTotalEmployerPension(request);
//    return ResponseEntity.ok(Map.of("totalEmployerPension", result));
//  }
}