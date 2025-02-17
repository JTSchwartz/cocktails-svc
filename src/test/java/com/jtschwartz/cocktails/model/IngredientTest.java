package com.jtschwartz.cocktails.model;

import com.jtschwartz.cocktails.constant.Unit;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTest {

  @TestFactory
  Stream<DynamicTest> ingredientFormatting() {
    return Stream.of(
        Pair.of("1oz Bourbon", new Ingredient("Bourbon", Unit.OUNCE, 1)),
        Pair.of("0.5oz Bourbon", new Ingredient("Bourbon", Unit.OUNCE, .5f)),
        Pair.of("1.25 cup Bourbon", new Ingredient("Bourbon", Unit.CUP, 1.25f)),
        Pair.of("3.33 cup Bourbon", new Ingredient("Bourbon", Unit.CUP, 3.333f)),
        Pair.of("1 Egg White", new Ingredient("Egg White", Unit.ITEM, 1)),
        Pair.of("0.25 Egg White", new Ingredient("Egg White", Unit.ITEM, .25f))
    ).map(pair -> DynamicTest.dynamicTest(pair.getKey(), () -> assertEquals(pair.getKey(), pair.getValue().toString())));
  }


}