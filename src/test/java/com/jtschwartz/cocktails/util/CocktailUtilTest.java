package com.jtschwartz.cocktails.util;

import com.jtschwartz.cocktails.model.Cocktail;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CocktailUtilTest {

  String searchString = "Amaretto So";

  Cocktail furthest = new Cocktail().setName("Whiskey Sour");
  Cocktail close = new Cocktail().setName("Jeffery Morgenthaler's Amaretto Sour");
  Cocktail closer = new Cocktail().setName("Spiced Amaretto Sour");
  Cocktail closest = new Cocktail().setName("Amaretto Sour");

  List<Cocktail> list = List.of(close, furthest, closest, closer);

  @Test
  void sortBy() {
    var result = new ArrayList<>(list);
    result.sort(CocktailUtil.sortByName(searchString));
    assertEquals(List.of(closest, closer, close, furthest), result);

  }

}