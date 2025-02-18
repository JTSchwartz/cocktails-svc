package com.jtschwartz.cocktails.model;

import com.jtschwartz.cocktails.constant.Unit;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.text.DecimalFormat;

import static java.lang.String.format;

public record Ingredient(
    @TextIndexed
    String name,
    Unit unit,
    float amount
) {
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

  public String toString() {
    var amountFormatted = DECIMAL_FORMAT.format(amount);

    if (unit == Unit.ITEM) {
      return format("%s %s", amountFormatted, name);
    }
    return format("%s %s %s", amountFormatted, unit.getShorthand(), name);
  }
}
