package com.mariusrubin.calculationengine.model.calc;

import static com.mariusrubin.calculationengine.api.IncomeType.TOTAL;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.ZERO;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.equal;

import com.mariusrubin.calculationengine.api.IncomeType;
import com.mariusrubin.calculationengine.api.Rate;
import com.mariusrubin.calculationengine.api.RateLevel;
import com.mariusrubin.calculationengine.api.calc.IncomeTaxCalc;
import com.mariusrubin.calculationengine.api.calc.TaxedAmount;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class DefaultIncomeTaxCalc implements IncomeTaxCalc {

  private final Collection<TaxedAmount> taxes;
  private final BigDecimal              basicRateLimit;

  public DefaultIncomeTaxCalc(final Collection<TaxedAmount> taxes, BigDecimal basicRateLimit) {
    this.taxes = Collections.unmodifiableCollection(taxes);
    this.basicRateLimit = basicRateLimit;
  }

  @Override
  public TaxedAmount total() {
    return breakDown().stream()
                      .reduce(CombinedTaxedAmount::new)
                      .orElse(new DefaultTaxedAmount(ZERO, ZERO, TOTAL, Rate.zero()));
  }

  @Override
  public Collection<TaxedAmount> breakDown() {
    return taxes;
  }

  @Override
  public TaxedAmount taxOn(final IncomeType type, final RateLevel rateLevel) {
    return breakDown().stream()
                      .filter(tax -> type == tax.incomeType() && tax.rate().isLevel(rateLevel))
                      .findAny()
                      .orElse(new DefaultTaxedAmount(ZERO, ZERO, type, Rate.zero()));
  }

  @Override
  public TaxedAmount totalTaxOn(final IncomeType... types) {
    final var check = EnumSet.copyOf(Set.of(types));
    return breakDown().stream()
                      .filter(tax -> check.contains(tax.incomeType()))
                      .reduce(CombinedTaxedAmount::new)
                      .orElse(new DefaultTaxedAmount(ZERO, ZERO, TOTAL, Rate.zero()));
  }

  @Override
  public TaxedAmount totalTaxExcluding(final IncomeType... toExclude) {
    final var check = EnumSet.copyOf(Set.of(toExclude));
    return breakDown().stream()
                      .filter(tax -> !check.contains(tax.incomeType()))
                      .reduce(CombinedTaxedAmount::new)
                      .orElse(new DefaultTaxedAmount(ZERO, ZERO, TOTAL, Rate.zero()));
  }

  public BigDecimal basicRateLimit() {
    return basicRateLimit;
  }

  private static class CombinedTaxedAmount implements TaxedAmount {

    private final BigDecimal amount;
    private final BigDecimal tax;
    private final Rate       rate;
    private final IncomeType incomeType;

    public CombinedTaxedAmount(final TaxedAmount t1, final TaxedAmount t2) {
      this.amount = t1.amount().add(t2.amount());
      this.tax = t1.tax().add(t2.tax());
      this.rate = new CombinedTaxedAmount.InferredRate(amount, tax);
      this.incomeType = t1.incomeType() == t2.incomeType() ? t1.incomeType() : TOTAL;
    }

    @Override
    public BigDecimal amount() {
      return amount;
    }

    @Override
    public BigDecimal tax() {
      return tax;
    }

    @Override
    public IncomeType incomeType() {
      return incomeType;
    }

    @Override
    public Rate rate() {
      return rate;
    }

    private static class InferredRate implements Rate {

      private final BigDecimal rate;

      public InferredRate(final BigDecimal amount, final BigDecimal tax) {
        this.rate = equal(tax, ZERO)
                    ? new BigDecimal("0.0000")
                    : tax.setScale(4, RoundingMode.UNNECESSARY)
                         .divide(amount, RoundingMode.HALF_UP);
      }

      @Override
      public RateLevel level() {
        return RateLevel.TOTAL;
      }

      @Override
      public BigDecimal rate() {
        return rate;
      }

    }
  }

}
