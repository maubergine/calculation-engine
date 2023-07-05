package com.mariusrubin.calculationengine.util;

import static java.math.RoundingMode.DOWN;
import static java.math.RoundingMode.UNNECESSARY;
import static java.math.RoundingMode.UP;

import com.mariusrubin.calculationengine.api.Rate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

/**
 * Utility class containing mathematical functions repeatedly used throughout the engine. Due to
 * their widespread use, any changes here should be made with care, and a proper check of any
 * impacted invocations.
 * <br>
 * <br>
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public final class TaxMathUtils {

  public static final BigDecimal ZERO         = twoDec(BigDecimal.ZERO);
  public static final BigDecimal HUNDRED      = new BigDecimal(100);
  public static final BigDecimal WORKING_DAYS = new BigDecimal("260.25");

  private TaxMathUtils() {
  }

  /**
   * Round down the provided amount to the nearest integer and return it with two decimal places.
   * For example providing 2.7 will yield 2.00. This will fail if the provided amount has any more
   * than two decimal places.
   *
   * @param amount the amount to be rounded down
   * @return the rounded amount
   */
  public static BigDecimal roundDownInt(final BigDecimal amount) {
    return twoDec(amount.setScale(0, DOWN));
  }

  /**
   * Round up the provided amount to the nearest integer and return it with two decimal places.
   * For example providing 2.2 will yield 3.00. This will fail if the provided amount has any more
   * than two decimal places.
   *
   * @param amount the amount to be rounded up
   * @return the rounded amount
   */
  public static BigDecimal roundUpInt(final BigDecimal amount) {
    return twoDec(amount.setScale(0, UP));
  }

  /**
   * Find the lowest amount of the ones provided. Where there are amounts that are equal lowest,
   * this value will be returned. This will fail if any of the provided amounts have more than two
   * decimal places.
   * <br>
   * <br>
   * The value returned will always be to two decimal places to avoid unpredictable scaled responses
   * arising from {@link BigDecimal#compareTo(BigDecimal)}'s handling of equality regardless of
   * scale.
   *
   * @param amounts the amounts to be compared
   * @return the lowest amount
   */
  public static BigDecimal min(final BigDecimal... amounts) {
    return twoDec(Stream.of(amounts).min(BigDecimal::compareTo).orElse(ZERO));
  }

  /**
   * Find the highest amount of the ones provided. Where there are amounts that are equal highest,
   * this value will be returned.
   * <br>
   * <br>
   * The value returned will always be to two decimal places to avoid unpredictable scaled responses
   * arising from {@link BigDecimal#compareTo(BigDecimal)}'s handling of equality regardless of
   * scale.
   *
   * @param amounts the amounts to be compared
   * @return the highest amount
   */
  public static BigDecimal max(final BigDecimal... amounts) {
    return twoDec(Stream.of(amounts).max(BigDecimal::compareTo).orElse(ZERO));
  }

  /**
   * Sum the provided amounts and return the result to two decimal places. This will fail if any of
   * the amounts have more than two (significant) decimal places.
   *
   * @param amounts the amounts to be summed
   * @return the summed amount
   */
  public static BigDecimal sum(final Stream<BigDecimal> amounts) {
    return twoDec(amounts.reduce(BigDecimal::add).orElse(ZERO));
  }

  /**
   * Sum the provided amounts and return the result to two decimal places. This will fail if any of
   * the amounts have more than two (significant) decimal places.
   *
   * @param amounts the amounts to be summed
   * @return the summed amount
   */
  public static BigDecimal sum(final BigDecimal... amounts) {
    return sum(Stream.of(amounts));
  }

  /**
   * Return the provided amount to two decimal places. This will succeed for any amounts with two
   * (significant decimal places <b>or fewer</b>. It does not round, so will fail for any amounts
   * with >2 places.
   *
   * @param amount the amount to return to two decimal places
   * @return the scaled amount
   */
  public static BigDecimal twoDec(final BigDecimal amount) {
    return amount.setScale(2, UNNECESSARY);
  }

  /**
   * {@link BigDecimal#equals(Object)} considers scale in its handling of equality. In other words
   * {@code new BigDecimal("10.0").equals(new BigDecimal("10.00")} will return {@code false}.
   * {@link BigDecimal#compareTo(BigDecimal)} ignores scale, so this method provides a convenience
   * mechanism to use this.
   *
   * @param b0 the first BigDecimal to compare
   * @param b1 the second BigDecimal to compare
   * @return true if the amounts are equal (regardless of scale)
   */
  public static boolean equal(final BigDecimal b0, final BigDecimal b1) {
    return b0.compareTo(b1) == 0;
  }

  /**
   * Convenience method for checking less than without direct use of comparison integers.
   *
   * @param b0 the first BigDecimal to compare
   * @param b1 the second BigDecimal to compare
   * @return true if b0 is less than b1 (regardless of scale)
   */
  public static boolean lessThan(final BigDecimal b0, final BigDecimal b1) {
    return b0.compareTo(b1) < 0;
  }

  /**
   * Convenience method for checking less than or equal without direct use of comparison integers.
   *
   * @param b0 the first BigDecimal to compare
   * @param b1 the second BigDecimal to compare
   * @return true if b0 is less than or equal to b1 (regardless of scale)
   */
  public static boolean lessThanOrEqual(final BigDecimal b0, final BigDecimal b1) {
    return b0.compareTo(b1) <= 0;
  }

  /**
   * Convenience method for checking greater than without direct use of comparison integers.
   *
   * @param b0 the first BigDecimal to compare
   * @param b1 the second BigDecimal to compare
   * @return true if b0 is greater than b1 (regardless of scale)
   */
  public static boolean greaterThan(final BigDecimal b0, final BigDecimal b1) {
    return b0.compareTo(b1) > 0;
  }

  /**
   * Convenience method for checking greater than or equal without direct use of comparison
   * integers.
   *
   * @param b0 the first BigDecimal to compare
   * @param b1 the second BigDecimal to compare
   * @return true if b0 is greater than or equal to b1 (regardless of scale)
   */
  public static boolean greaterThanOrEqual(final BigDecimal b0, final BigDecimal b1) {
    return b0.compareTo(b1) >= 0;
  }

  /**
   * Convenience method for checking whether an amount is positive.
   *
   * @param amount the amount to check
   * @return true if the amount is greater than 0 (regardless of scale)
   */
  public static boolean positive(final BigDecimal amount) {
    return greaterThan(amount, ZERO);
  }

  /**
   * Convenience method for checking whether an amount is negative.
   *
   * @param amount the amount to check
   * @return true if the amount is less than 0 (regardless of scale)
   */
  public static boolean negative(final BigDecimal amount) {
    return lessThan(amount, ZERO);
  }

  /**
   * Convenience method for checking whether an amount is zero.
   *
   * @param amount the amount to check
   * @return true if the amount is equal to 0 (regardless of scale)
   */
  public static boolean isZero(final BigDecimal amount) {
    return equal(amount, ZERO);
  }

  /**
   * Converts the percentage embedded in a tax rate to a raw number than can be used in
   * multiplication. A percentage in the rate in the form 12.34% will come back as 0.1234. This
   * will round percentages expressed to more than 2 decimal places in the input.
   *
   * @param rate the rate
   * @return the percent
   */
  public static BigDecimal toPercent(final Rate rate) {
    return rate.rate().divide(HUNDRED, 4, RoundingMode.HALF_UP);
  }

}
