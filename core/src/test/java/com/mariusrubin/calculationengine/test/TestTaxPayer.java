package com.mariusrubin.calculationengine.test;

import com.mariusrubin.calculationengine.api.Gift;
import com.mariusrubin.calculationengine.api.Income;
import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.Pension;
import com.mariusrubin.calculationengine.api.TaxPayer;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Convenience class to be overridden in other tests.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
public class TestTaxPayer implements TaxPayer {

  private List<Income>  incomes;
  private BigDecimal    taxPaidOverride;
  private List<Pension> pensions;
  private List<Gift>    gifts;
  private BigDecimal    pensionAllowanceCarryOver;
  private BigDecimal    paymentsMade;

  public TestTaxPayer(final List<Income> incomes,
                      final BigDecimal taxPaidOverride,
                      final List<Pension> pensions,
                      final List<Gift> gifts,
                      final BigDecimal pensionAllowanceCarryOver,
                      final BigDecimal paymentsMade) {

    this.incomes = incomes;
    this.taxPaidOverride = taxPaidOverride;
    this.pensions = pensions;
    this.gifts = gifts;
    this.pensionAllowanceCarryOver = pensionAllowanceCarryOver;
    this.paymentsMade = paymentsMade;
  }

  @Override
  public List<Income> incomes() {
    return orEmpty(incomes);
  }

  @Override
  public List<Income> incomes(final IncomeType... ofType) {
    final var filter = EnumSet.copyOf(Set.of(ofType));
    return incomes().stream().filter(inc -> filter.contains(inc.type())).toList();
  }

  @Override
  public List<Pension> pensions() {
    return orEmpty(pensions);
  }

  @Override
  public List<Gift> gifts() {
    return orEmpty(gifts);
  }

  @Override
  public BigDecimal pensionAllowanceCarryForward() {
    return pensionAllowanceCarryOver;
  }

  @Override
  public BigDecimal paymentsMade() {
    return paymentsMade;
  }

  @Override
  public BigDecimal taxPaidOverride() {
    return taxPaidOverride;
  }

  private static <T> List<T> orEmpty(final List<T> items) {
    return items == null ? Collections.emptyList() : Collections.unmodifiableList(items);
  }
}
