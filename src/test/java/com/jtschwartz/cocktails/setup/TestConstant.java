package com.jtschwartz.cocktails.setup;

import com.jtschwartz.cocktails.constant.Unit;
import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.model.Ingredient;

import java.util.List;
import java.util.UUID;

public class TestConstant {

    public static Cocktail COCKTAIL = new Cocktail(
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
}
