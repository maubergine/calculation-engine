package com.mariusrubin.calculationengine.web.controller;

import com.mariusrubin.calculationengine.UkFinancialYear;
import com.mariusrubin.calculationengine.UkTaxRates;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "index";
  }
  
  @GetMapping("/about")
  public String about() {
    return "about";
  }
  
  @GetMapping("/calculators")
  public String calculators() {
    return "calculators/index";
  }
  
  // Calculators
  @GetMapping("/calculators/adjusted-net-income")
  public String adjustedNetIncome(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/adjusted-net-income";
  }
  
  @GetMapping("/calculators/allowable-expense")
  public String allowableExpense(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/allowable-expense";
  }
  
  @GetMapping("/calculators/basic-rate-adjustment")
  public String basicRateAdjustment(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/basic-rate-adjustment";
  }
  
  @GetMapping("/calculators/defined-benefit-value")
  public String definedBenefitValue(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/defined-benefit-value";
  }
  
  @GetMapping("/calculators/gift")
  public String gift(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/gift";
  }
  
  @GetMapping("/calculators/gross-upper")
  public String grossUpper(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/gross-upper";
  }
  
  @GetMapping("/calculators/income")
  public String income(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/income";
  }
  
  @GetMapping("/calculators/income-tax")
  public String incomeTax(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/income-tax";
  }
  
  @GetMapping("/calculators/net-income")
  public String netIncome(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/net-income";
  }
  
  @GetMapping("/calculators/payment-due")
  public String paymentDue(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/payment-due";
  }
  
  @GetMapping("/calculators/personal-allowance")
  public String personalAllowance(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/personal-allowance";
  }
  
  @GetMapping("/calculators/rar-total")
  public String rarTotal(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/rar-total";
  }
  
  @GetMapping("/calculators/salary-sacrifice")
  public String salarySacrifice(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/salary-sacrifice";
  }
  
  @GetMapping("/calculators/total-benefits")
  public String totalBenefits(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/total-benefits";
  }
  
  @GetMapping("/calculators/total-income")
  public String totalIncome(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/total-income";
  }
  
  @GetMapping("/calculators/total-sipp")
  public String totalSipp(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/total-sipp";
  }
  
  // Pension calculators
  @GetMapping("/calculators/pension/adjusted-income")
  public String pensionAdjustedIncome(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/pension/adjusted-income";
  }
  
  @GetMapping("/calculators/pension/allowance")
  public String pensionAllowance(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/pension/allowance";
  }
  
  @GetMapping("/calculators/pension/threshold-income")
  public String pensionThresholdIncome(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/pension/threshold-income";
  }
  
  @GetMapping("/calculators/pension/relevant-earnings")
  public String pensionRelevantEarnings(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/pension/relevant-earnings";
  }
  
  @GetMapping("/calculators/pension/total-employer")
  public String pensionTotalEmployer(Model model) {
    model.addAttribute("taxYears", UkTaxRates.values());
    return "calculators/pension/total-employer";
  }
}