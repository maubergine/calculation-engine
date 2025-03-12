package com.mariusrubin.calculationengine.web.controller;

import com.mariusrubin.calculationengine.UkTaxRates;
import com.mariusrubin.calculationengine.web.dto.CalculationRequest;
import com.mariusrubin.calculationengine.web.dto.CalculationResponse;
import com.mariusrubin.calculationengine.web.service.CalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
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
}