package com.jtschwartz.cocktails.model;

import com.jtschwartz.cocktails.constant.Unit;

import java.text.DecimalFormat;

import static java.lang.String.format;

public record Ingredient(
        String name,
        Unit unit,
        float amount
) {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    public String toString() {
        if (unit == Unit.ITEM) {
            return format("%s %s", DECIMAL_FORMAT.format(amount), name);
        } else if (unit.isOmitSpaceOnShorthand()) {
            return format("1%s %s", unit.getShorthand(), name);
        }
        return format("1 %s %s", unit.getShorthand(), name);
    }
}
