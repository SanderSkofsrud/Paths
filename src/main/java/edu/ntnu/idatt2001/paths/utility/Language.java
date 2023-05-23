package edu.ntnu.idatt2001.paths.utility;

/**
 * The enum Language.
 * The Language enum is used to represent the different languages that the application supports.
 * Each enum value corresponds to a language code.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 - 11.04.2023
 */

public enum Language {
  AF,
  AM,
  AR,
  AZ,
  BE,
  BG,
  BN,
  BS,
  CA,
  CEB,
  CO,
  CS,
  CY,
  DA,
  DE,
  EL,
  EN,
  EO,
  ES,
  ET,
  EU,
  FA,
  FI,
  FIL,
  FR,
  FY,
  GA,
  GD,
  GL,
  GU,
  HA,
  HAW,
  HI,
  HMN,
  HR,
  HT,
  HU,
  HY,
  ID,
  IG,
  IS,
  IT,
  IW,
  JA,
  JV,
  KA,
  KK,
  KM,
  KN,
  KO,
  KU,
  KY,
  LA,
  LB,
  LO,
  LT,
  LV,
  MG,
  MI,
  MK,
  ML,
  MN,
  MR,
  MS,
  MT,
  MY,
  NE,
  NL,
  NO,
  NY,
  OR,
  PA,
  PL,
  PS,
  PT,
  RO,
  RU,
  RW,
  SD,
  SI,
  SK,
  SL,
  SM,
  SN,
  SO,
  SQ,
  SR,
  ST,
  SU,
  SV,
  SW,
  TA,
  TE,
  TG,
  TH,
  TK,
  TL,
  TR,
  TT,
  UG,
  UK,
  UR,
  UZ,
  VI,
  XH,
  YI,
  YO,
  ZH,
  ZU;

  /**
   * Constructs a new Language.
   */
  Language() {
  }


  /**
   * Returns the lower case string representation of the language.
   *
   * @return the language code in lower case
   */
  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}

