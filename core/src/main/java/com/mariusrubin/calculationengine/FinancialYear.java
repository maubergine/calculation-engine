package com.mariusrubin.calculationengine;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a financial year: a period of time with a start date and an end date that may/may not
 * be the same as a calendar year. This object is immutable once constructed.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class FinancialYear {

  private final LocalDate startDate;
  private final LocalDate endDate;

  /**
   * Build a financial year.
   *
   * @param startDate the start date of the financial year.
   * @param endDate   the end date of the financial year.
   */
  public FinancialYear(final LocalDate startDate, final LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Get the start date of the year.
   *
   * @return the start date
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Get the end date of the year.
   *
   * @return the end date
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final FinancialYear that = (FinancialYear) o;
    return startDate.equals(that.startDate) && endDate.equals(that.endDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate);
  }

}
