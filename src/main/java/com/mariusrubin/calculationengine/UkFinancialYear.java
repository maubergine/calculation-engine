package com.mariusrubin.calculationengine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Convenience extension of {@link FinancialYear} to simplify building of financial years in the UK,
 * which run from April 6th to April 5th.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public final class UkFinancialYear extends FinancialYear {

  private static final Map<Integer, UkFinancialYear> YEARS = new HashMap<>();

  private UkFinancialYear(final int startYear) {
    super(LocalDate.of(startYear, 4, 6), LocalDate.of(startYear + 1, 4, 5));
  }

  /**
   * Build a UK financial year for the given start year e.g. providing 2023 to this will provide a
   * UK "FY23" tax year with start date 2023-04-06 and end date 2024-04-05.
   *
   * @param startYear the starting year of the tax year
   * @return the tax year
   */
  public static UkFinancialYear starting(final int startYear) {
    return YEARS.computeIfAbsent(startYear, UkFinancialYear::new);
  }

}
