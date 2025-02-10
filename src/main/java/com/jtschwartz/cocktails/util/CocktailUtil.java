package com.jtschwartz.cocktails.util;

import com.jtschwartz.cocktails.model.Cocktail;
import lombok.experimental.UtilityClass;
import org.apache.commons.text.similarity.JaccardDistance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@UtilityClass
public class CocktailUtil {

    private static final JaccardDistance distance = new JaccardDistance();

    public Comparator<Cocktail> sortBy(String target) {
        return Comparator.comparing(cocktail -> distance.apply(target, cocktail.getName()));
    }

    public Cocktail closest(String target, List<Cocktail> cocktails) {
        var copyOfCocktails = new ArrayList<>(cocktails);
        copyOfCocktails.sort(sortBy(target));
        return copyOfCocktails.getFirst();
    }
}
