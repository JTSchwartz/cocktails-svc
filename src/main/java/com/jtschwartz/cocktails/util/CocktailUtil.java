package com.jtschwartz.cocktails.util;

import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.model.Ingredient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.EditDistance;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import static java.lang.Double.sum;
import static java.lang.Math.min;
import static org.apache.commons.lang3.StringUtils.*;

public class CocktailUtil {

  // distance.apply("abc", "abc") == 0.0
  // distance.apply("abc", "xyz") == 1.0
  private static final EditDistance<Double> distance = new JaccardDistance();

  public static Comparator<Cocktail> sortByName(String target) {
    return Comparator.comparingDouble(applyDistanceTo(target, Cocktail::getName));
  }

  public static Comparator<Cocktail> sortByIngredient(String target) {
    return Comparator.comparingDouble(cocktail ->
        cocktail.getIngredients().stream()
            .mapToDouble(applyDistanceTo(target, Ingredient::name)).min()
            .orElse(1)
    );
  }

  public static Comparator<Cocktail> sortByNameAndIngredient(String target) {
    return Comparator.comparingDouble(cocktail -> sum(
        applyDistanceTo(target, Cocktail::getName).applyAsDouble(cocktail),
        cocktail.getIngredients().stream()
            .mapToDouble(applyDistanceTo(target, Ingredient::name)).min()
            .orElse(1)
    ));
  }

  private static <T> ToDoubleFunction<T> applyDistanceTo(String target, Function<T, String> stringify) {
    final var strippedTarget = strip(target);
    return search -> {
      var searchString = strip(stringify.apply(search));
      return equalsIgnoreCase(searchString, strippedTarget)
          ? 0
          : startsWith(searchString, strippedTarget)
          ? 0.01
          :containsIgnoreCase(searchString, strippedTarget)
          ? 0.02
          : distance.apply(target.toLowerCase(), searchString.toLowerCase());
    };
  }

  private static String strip(String str) {
    return stripAccents(str.toUpperCase().replaceAll("/[^A-Z0-9]/", ""));
  }
}
