package com.mariusrubin.calculationengine.calc;

import static com.mariusrubin.calculationengine.api.PensionType.DEFINED_BENEFIT;
import static com.mariusrubin.calculationengine.api.PensionType.DEFINED_BENEFIT_LUMP;

import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.model.DefinedLumpPension;
import com.mariusrubin.calculationengine.util.TaxMathUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the total value of amounts given to defined benefit schemes.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class DefinedBenefitValueCalculator {

  private static final BigDecimal ASSUMED_YEARS_PAID_OUT = new BigDecimal("16");

  /**
   * Calculate the total defined benefit amount.
   *
   * @param taxPayer the taxpayer
   * @return the amount
   */
  public BigDecimal calculate(final TaxPayer taxPayer) {
    //TODO handle a scenario where we want to predict defined benefit from employment income
    //rather than a known amount of defined benefit accrued during a year.

    return taxPayer.pensions()
                   .stream()
                   .filter(p -> p.type() == DEFINED_BENEFIT || p.type() == DEFINED_BENEFIT_LUMP)
                   .map(DefinedBenefitValueCalculator::calculateValue)
                   .reduce(BigDecimal::add)
                   .orElse(TaxMathUtils.ZERO);

  }

  private static BigDecimal calculateValue(final Pension pension) {

    return switch (pension.type()) {
      case DEFINED_BENEFIT -> pension.amount().multiply(ASSUMED_YEARS_PAID_OUT);
      case DEFINED_BENEFIT_LUMP -> calculateLump(pension);
      default ->
          throw new IllegalArgumentException("This method should not be invoked on pension of type "
                                             + pension.type());
    };
  }

  private static BigDecimal calculateLump(final Pension pension) {

    if (pension instanceof final DefinedLumpPension lump) {
      final var amount = TaxMathUtils.twoDec(lump.amount());
      final var toDivide = amount.multiply(new BigDecimal("100"))
                                 .setScale(0, RoundingMode.UNNECESSARY);
      final var costPerHundred = lump.getCostPerHundredYearlyPay();
      if (costPerHundred == null) {
        throw new IllegalArgumentException("Defined lump contributions must have a cost per 100");
      }
      return toDivide.divide(costPerHundred, 2, RoundingMode.HALF_EVEN)
                     .multiply(ASSUMED_YEARS_PAID_OUT);
    }

    throw new IllegalStateException(String.format("Pension of type %s was not of expected class",
                                                  pension.type()));

  }

}