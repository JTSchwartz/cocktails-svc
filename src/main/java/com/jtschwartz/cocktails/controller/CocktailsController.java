package com.jtschwartz.cocktails.controller;

import com.jtschwartz.cocktails.api.definition.ApiApi;
import com.jtschwartz.cocktails.api.model.CocktailCreateRequest;
import com.jtschwartz.cocktails.api.model.CocktailListResponse;
import com.jtschwartz.cocktails.api.model.CocktailModel;
import com.jtschwartz.cocktails.api.model.CocktailPageResponse;
import com.jtschwartz.cocktails.api.model.CocktailResponse;
import com.jtschwartz.cocktails.exception.BadRequestException;
import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.service.CocktailService;
import com.jtschwartz.cocktails.util.Transformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CocktailsController implements ApiApi {

  private final CocktailService cocktailService;
  private final Transformer transformer;

  @Override
  public CocktailPageResponse getCocktails(Optional<List<String>> filter, Optional<String> all, Optional<String> name, Optional<String> ingredient, Pageable pageable) {
    var paramCount = Stream.of(all, name, ingredient).mapToInt(opt -> opt.isPresent() ? 1 : 0).sum();
    if (paramCount > 1) {
      throw new BadRequestException("All, Name, and Ingredient are mutually exclusive.");
    }

    var filterPresent = filter.isPresent() && !filter.get().isEmpty();

    Page<Cocktail> cocktails = null;
    if (paramCount == 0) {
      cocktails = filterPresent
          ? cocktailService.filterCocktails(filter.get(), pageable)
          : cocktailService.getAllCocktails(pageable);
    } else if (all.isPresent()) {
      cocktails = filterPresent
          ? cocktailService.filterAndSearchByNameAndIngredient(filter.get(), all.get(), pageable)
          : cocktailService.searchByNameAndIngredient(all.get(), pageable);
    } else if (name.isPresent()) {
      cocktails = filterPresent
          ? cocktailService.filterAndSearchByName(filter.get(), name.get(), pageable)
          : cocktailService.searchByName(name.get(), pageable);
    } else if (ingredient.isPresent()) {
      cocktails = filterPresent
          ? cocktailService.filterAndSearchByIngredient(filter.get(), ingredient.get(), pageable)
          : cocktailService.searchByIngredient(ingredient.get(), pageable);
    }

    return transformer.transform(cocktails, CocktailPageResponse.class);
  }

  @Override
  public CocktailResponse getCocktail(String name) {
    var cocktail = cocktailService.getCocktail(name);

    return new CocktailResponse().data(transformer.transform(cocktail, CocktailModel.class));
  }

  @Override
  public CocktailListResponse getRandomCocktail(Optional<Integer> size) {
    var cocktails = cocktailService.getRandomCocktails(size.orElse(1)).stream()
        .map(cocktail -> transformer.transform(cocktail, CocktailModel.class)).toList();

    return new CocktailListResponse().data(cocktails);
  }

  @Override
  public CocktailResponse createCocktail(CocktailCreateRequest cocktailCreateRequest) {
    var cocktail = transformer.transform(cocktailCreateRequest, Cocktail.class);

    var storedCocktail = cocktailService.storeCocktail(cocktail);

    return new CocktailResponse().data(transformer.transform(storedCocktail, CocktailModel.class));
  }
}
