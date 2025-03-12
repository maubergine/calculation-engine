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
}