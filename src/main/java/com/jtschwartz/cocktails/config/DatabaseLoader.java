package com.jtschwartz.cocktails.config;

import com.jtschwartz.cocktails.constant.Unit;
import com.jtschwartz.cocktails.data.CocktailRepository;
import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.model.Ingredient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseLoader {

  @Bean
  CommandLineRunner initDb(CocktailRepository cocktailRepository) {
    var initCocktail = new Cocktail()
        .setName("Init")
        .setInstructions("Init")
        .setIngredients(List.of(new Ingredient("Init", Unit.ITEM, 1)));

    var document = cocktailRepository.insert(initCocktail);
    cocktailRepository.delete(document);
    return args -> {
    };
  }

}
