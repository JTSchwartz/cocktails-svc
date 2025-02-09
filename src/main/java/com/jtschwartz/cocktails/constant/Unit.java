package com.jtschwartz.cocktails.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Unit {
    OUNCE("oz", "ounce"),
    MILLILITER("ml", "milliliter"),
    TEASPOON("tsp", "teaspoon"),
    TABLESPOON("tbsp", "tablespoon"),
    DASH("dash", "dash", "dashes", false),
    CUP("cup", "cup", false),
    GRAM("g", "gram"),
    ITEM("", "", ""),;

    private final String shorthand;
    private final String singular;
    private final String plural;
    private final boolean omitSpaceOnShorthand;

    Unit(String shorthand, String singular) {
        this(shorthand, singular, shorthand + 's', true);
    }

    Unit(String shorthand, String singular, boolean omitSpaceOnShorthand) {
        this(shorthand, singular, shorthand + 's', omitSpaceOnShorthand);
    }

    Unit(String shorthand, String singular, String plural) {
        this(shorthand, singular, plural, true);
    }

    public static Unit ofShorthand(String shorthand) {
        return Arrays.stream(values()).filter(unit -> unit.shorthand.equals(shorthand)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown unit: " + shorthand));
    }
}
