package com.jtschwartz.cocktails.setup;

import com.jtschwartz.cocktails.constant.Unit;
import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestConstant {

  public static Cocktail AMARETTO_SOUR = new Cocktail(
      UUID.randomUUID().toString(),
      "Jeffery Morgenthaler's Amaretto Sour",
      "Shaken",
      List.of(
          new Ingredient("Amaretto", Unit.OUNCE, 1),
          new Ingredient("Bourbon", Unit.OUNCE, 1),
          new Ingredient("Lemon Juice", Unit.OUNCE, 1),
          new Ingredient("Simple Syrup", Unit.TEASPOON, 1),
          new Ingredient("Egg White", Unit.ITEM, 1)
      )
  ).setId(UUID.randomUUID().toString());

  public static Cocktail SOUTHSIDE = new Cocktail(
      UUID.randomUUID().toString(),
      "Southside",
      "Muddle mint leaves with simple syrup. Shake with remaining ingredients.",
      List.of(
          new Ingredient("Gin", Unit.OUNCE, 2),
          new Ingredient("Simple Syrup", Unit.OUNCE, 0.75f),
          new Ingredient("Lemon Juice", Unit.OUNCE, 1),
          new Ingredient("Mint Leaves", Unit.ITEM, 6)
      )
  );

  public static List<Cocktail> COCKTAILS = new ArrayList<>(List.of(AMARETTO_SOUR, SOUTHSIDE));
}
