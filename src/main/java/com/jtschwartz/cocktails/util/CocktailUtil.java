package com.jtschwartz.cocktails.util;

import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.model.Ingredient;
import org.apache.commons.text.similarity.JaccardDistance;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import static java.lang.Math.min;

public class CocktailUtil {

    // distance.apply("abc", "abc") == 0.0
    // distance.apply("abc", "xyz") == 1.0
    private static final JaccardDistance distance = new JaccardDistance();

    public static Comparator<Cocktail> sortByName(String target) {
        return Comparator.comparingDouble(applyDistanceTo(target, Cocktail::getName));
    }

    public static Comparator<Cocktail> sortByIngredient(String target) {
        return Comparator.comparingDouble(cocktail ->
                cocktail.getIngredients().stream()
                        .mapToDouble(applyDistanceTo(target, Ingredient::name)).min()
                        .orElse(10)
        );
    }

    public static Comparator<Cocktail> sortByNameAndIngredient(String target) {
        return Comparator.comparingDouble(cocktail -> min(
                applyDistanceTo(target, Cocktail::getName).applyAsDouble(cocktail),
                cocktail.getIngredients().stream()
                        .mapToDouble(applyDistanceTo(target, Ingredient::name)).min()
                        .orElse(1)
        ));
    }

    private static <T> ToDoubleFunction<T> applyDistanceTo(String target, Function<T, String> stringify) {
        return search -> distance.apply(target, stringify.apply(search));
    }
}
