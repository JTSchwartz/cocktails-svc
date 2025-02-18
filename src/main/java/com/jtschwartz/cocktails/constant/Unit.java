package com.jtschwartz.cocktails.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Unit {
  OUNCE("oz", "ounce"),
  MILLILITER("ml", "milliliter"),
  TEASPOON("tsp", "teaspoon"),
  TABLESPOON("tbsp", "tablespoon"),
  DASH("dash", "dash", "dashes"),
  CUP("cup", "cup"),
  GRAM("g", "gram"),
  ITEM("", "", ""),
  ;

  private final String shorthand;
  private final String singular;
  private final String plural;

  Unit(String shorthand, String singular) {
    this(shorthand, singular, shorthand + 's');
  }

  public static Unit ofShorthand(String shorthand) {
    return Arrays.stream(values()).filter(unit -> unit.shorthand.equals(shorthand)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown unit: " + shorthand));
  }
}
