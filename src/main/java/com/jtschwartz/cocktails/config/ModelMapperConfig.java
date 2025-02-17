package com.jtschwartz.cocktails.config;

import com.jtschwartz.cocktails.api.model.CocktailCreateRequest;
import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.model.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    return configureModelMapper(modelMapper);
  }

  public ModelMapper configureModelMapper(ModelMapper modelMapper) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.registerModule(new RecordModule());

    modelMapper.typeMap(CocktailCreateRequest.class, Cocktail.class)
        .addMappings(m -> m.using(new IngredientListConverter()).map(CocktailCreateRequest::getIngredients, Cocktail::setIngredients));

    return modelMapper;
  }

  private static class IngredientListConverter extends AbstractConverter<List<com.jtschwartz.cocktails.api.model.Ingredient>, List<com.jtschwartz.cocktails.model.Ingredient>> {
    @Override
    protected List<Ingredient> convert(List<com.jtschwartz.cocktails.api.model.Ingredient> ingredientList) {
      return isNull(ingredientList)
          ? null
          : ingredientList.stream()
          .peek(ing -> log.info("Ingredient: {}", ing))
          .map(ing -> new Ingredient(ing.getName(), ing.getUnit(), ing.getAmount()))
          .toList();
    }
  }
}
