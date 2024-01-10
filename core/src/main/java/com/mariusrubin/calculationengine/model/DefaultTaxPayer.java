package com.mariusrubin.calculationengine.model;

import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.twoDec;

import com.mariusrubin.calculationengine.api.EmploymentIncome;
import com.mariusrubin.calculationengine.api.Gift;
import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.serde.TaxPayerInfo;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Default implementation of {@link TaxPayer}. Due to the pain of managing interface types etc.
 * when serialising to/from YAML (and other formats) this wraps the class used for that
 * {@link TaxPayerInfo}, and provides the mapping methods over the top to meet the interface spec.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class DefaultTaxPayer implements TaxPayer {

  private final TaxPayerInfo info;

  /**
   * Construct a taxpayer with the necessary information.
   *
   * @param info the taxpayer information.
   */
  public DefaultTaxPayer(final TaxPayerInfo info) {
    this.info = info;
  }

  @Override
  public List<Income> incomes() {
    return incomeStream().toList();
  }

  @Override
  public List<Income> incomes(final IncomeType... ofType) {
    final var filterList = EnumSet.copyOf(Arrays.asList(ofType));
    return incomeStream().filter(inc -> filterList.contains(inc.type())).toList();
  }

  @Override
  public List<Pension> pensions() {
    return Stream.of(info.getPredictedPensions(),
                     info.getKnownPensions(),
                     info.getDefinedLumpPensions())
                 .filter(Objects::nonNull)
                 .flatMap(Collection::stream)
                 .map(Pension.class::cast)
                 .toList();
  }

  @Override
  public List<Gift> gifts() {
    return Collections.unmodifiableList(info.getGifts());
  }

  @Override
  public BigDecimal pensionAllowanceCarryForward() {
    return info.getPensionAllowanceCarryForward() == null
           ? ZERO
           : twoDec(info.getPensionAllowanceCarryForward());
  }

  @Override
  public BigDecimal paymentsMade() {
    return info.getPaymentsMade() == null ? ZERO : twoDec(info.getPaymentsMade());
  }

  @Override
  public BigDecimal taxPaidOverride() {
    return info.getTaxPaidOverride() == null ? null : twoDec(info.getTaxPaidOverride());
  }

  private Stream<Income> employmentIncomes() {
    return Stream.concat(info.getKnownEmployments().stream(), predictedIncomes());
  }

  private Stream<Income> incomeStream() {

    return Stream.concat(Stream.concat(employmentIncomes(), info.getDividends().stream()),
                         Stream.of(info.getUntaxedInterest())
                               .filter(Objects::nonNull)
                               .map(InterestIncome::new));

  }

  private Stream<EmploymentIncome> predictedIncomes() {
    return info.getPredictedEmployments().stream().map(PredictedEmploymentIncome::new);
  }

}
