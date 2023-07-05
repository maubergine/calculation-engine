package com.mariusrubin.calculationengine.util;

import static com.mariusrubin.calculationengine.api.RateLevel.BASIC;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.equal;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.greaterThan;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.greaterThanOrEqual;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.isZero;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.lessThan;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.lessThanOrEqual;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.max;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.min;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.negative;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.positive;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.roundDownInt;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.roundUpInt;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.sum;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.toPercent;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.twoDec;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mariusrubin.calculationengine.model.DefaultRate;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ATaxMathUtils {


  @Test
  public void shouldRoundDownToTheNearestInt() {
    assertThat(roundDownInt(new BigDecimal("3.2"))).isEqualTo("3.00");
    assertThat(roundDownInt(new BigDecimal("2.888"))).isEqualTo("2.00");
    assertThat(roundDownInt(new BigDecimal("4"))).isEqualTo("4.00");
  }

  @Test
  public void shouldRoundUpToNearestInt() {
    assertThat(roundUpInt(new BigDecimal("3.2"))).isEqualTo("4.00");
    assertThat(roundUpInt(new BigDecimal("2.888"))).isEqualTo("3.00");
    assertThat(roundUpInt(new BigDecimal("4"))).isEqualTo("4.00");
  }

  @Test
  public void shouldFindMin() {
    assertThat(min(new BigDecimal("2.33"),
                   new BigDecimal("1.30"),
                   new BigDecimal("1.3"))).isEqualTo("1.30");
  }

  @Test
  public void shouldRejectLargeDecimalsWhenFindingMin() {
    assertThatThrownBy(() -> min(new BigDecimal("2.33"),
                                 new BigDecimal("1.303")))
        .isInstanceOf(ArithmeticException.class);
  }

  @Test
  public void shouldFindMax() {
    assertThat(max(new BigDecimal("2.40"),
                   new BigDecimal("2.4"),
                   new BigDecimal("1.33"))).isEqualTo("2.40");
  }

  @Test
  public void shouldRejectLargeDecimalsWhenFindingMax() {
    assertThatThrownBy(() -> min(new BigDecimal("2.334"),
                                 new BigDecimal("4.30")))
        .isInstanceOf(ArithmeticException.class);
  }

  @Test
  public void shouldSum() {
    assertThat(sum(new BigDecimal("123.55"), new BigDecimal("332.1"))).isEqualTo("455.65");
  }

  @Test
  public void shouldRejectLargeDecimalsWhenSumming() {
    assertThatThrownBy(() -> sum(new BigDecimal("2.334"),
                                 new BigDecimal("4.30")))
        .isInstanceOf(ArithmeticException.class);
  }

  @Test
  public void shouldScaleToTwoDecimals() {
    assertThat(twoDec(new BigDecimal("2"))).isEqualTo("2.00");
  }

  @Test
  public void shouldFailWhenScalingDecimalsWithMoreThanTwoDecimals() {
    assertThatThrownBy(() -> twoDec(new BigDecimal("1.334")))
        .isInstanceOf(ArithmeticException.class);
  }

  @Test
  public void canCheckEqualityRegardlessOfScale() {
    assertThat(equal(new BigDecimal("1.10"), new BigDecimal("1.1"))).isTrue();
    assertThat(equal(new BigDecimal("1.45"), new BigDecimal("1.46"))).isFalse();
  }

  @Test
  public void canCheckLessThanRegardlessOfScale() {
    assertThat(lessThan(new BigDecimal("1.1"), new BigDecimal("2"))).isTrue();
    assertThat(lessThan(new BigDecimal("1.1"), new BigDecimal("1.05"))).isFalse();
    assertThat(lessThan(new BigDecimal("1.1"), new BigDecimal("1.10"))).isFalse();
  }

  @Test
  public void canCheckLessThanOrEqualRegardlessOfScale() {
    assertThat(lessThanOrEqual(new BigDecimal("1.1"), new BigDecimal("1.10"))).isTrue();
    assertThat(lessThanOrEqual(new BigDecimal("1.1"), new BigDecimal("1.05"))).isFalse();
    assertThat(lessThanOrEqual(new BigDecimal("1.1"), new BigDecimal("1.10"))).isTrue();
  }

  @Test
  public void canCheckGreaterThanRegardlessOfScale() {
    assertThat(greaterThan(new BigDecimal("1.1"), new BigDecimal("1.05"))).isTrue();
    assertThat(greaterThan(new BigDecimal("1.1"), new BigDecimal("2"))).isFalse();
    assertThat(greaterThan(new BigDecimal("1.1"), new BigDecimal("1.10"))).isFalse();
  }

  @Test
  public void canCheckGreaterThanOrEqualRegardlessOfScale() {
    assertThat(greaterThanOrEqual(new BigDecimal("1.1"), new BigDecimal("1.05"))).isTrue();
    assertThat(greaterThanOrEqual(new BigDecimal("1.1"), new BigDecimal("2"))).isFalse();
    assertThat(greaterThanOrEqual(new BigDecimal("1.1"), new BigDecimal("1.10"))).isTrue();
  }

  @Test
  public void canCheckPositivity() {
    assertThat(positive(new BigDecimal("2"))).isTrue();
    assertThat(positive(new BigDecimal("-3.23"))).isFalse();
  }

  @Test
  public void canCheckNegativity() {
    assertThat(negative(new BigDecimal("-3.23"))).isTrue();
    assertThat(negative(new BigDecimal("2"))).isFalse();
  }

  @Test
  public void canCheckWhetherNumberIsEqualToZero() {
    assertThat(isZero(new BigDecimal("0.0"))).isTrue();
    assertThat(isZero(new BigDecimal("2"))).isFalse();
  }

  @Test
  public void canConvertRatePercentsToActualPercent() {
    final var rate = new DefaultRate(BASIC, 12.34f);
    assertThat(toPercent(rate)).isEqualTo("0.1234");
  }
}