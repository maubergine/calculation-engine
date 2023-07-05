package com.mariusrubin.calculationengine.api;

public enum RateLevel {

  //Add another comment re: the sort order of this enum being relevant when it comes to
  //running the post-process for nil rates.

  ZERO,
  BASIC_NIL,
  BASIC,
  LOWER,
  HIGHER_NIL,
  HIGHER,
  ADDITIONAL_NIL,
  ADDITIONAL,
  TOTAL

}
