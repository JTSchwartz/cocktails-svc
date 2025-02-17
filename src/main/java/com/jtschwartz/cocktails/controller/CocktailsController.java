package com.jtschwartz.cocktails.controller;

import com.jtschwartz.cocktails.api.definition.ApiApi;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CocktailsController implements ApiApi {

  private final CocktailService cocktailService;
  private final Transformer transformer;

  @Override
  public CocktailPageResponse getCocktails(Optional<List<String>> filter, Pageable pageable) {
    var cocktails = filter.isEmpty() || filter.get().isEmpty()
        ? cocktailService.getAllCocktails(pageable)
        : cocktailService.filterCocktails(filter.get(), pageable);

    return transformer.transform(cocktails, CocktailPageResponse.class);
  }

  @Override
  public CocktailResponse getCocktail(String name) {
    var cocktail = cocktailService.getCocktail(name);

    return new CocktailResponse().data(transformer.transform(cocktail, CocktailModel.class));
  }

  @Override
  public CocktailPageResponse searchCocktails(Optional<String> all, Optional<String> name, Optional<String> ingredient, Pageable pageable) {
    var paramCount = Stream.of(all, name, ingredient).mapToInt(opt -> opt.isPresent() ? 1 : 0).sum();
    if (paramCount != 1) {
      throw new BadRequestException("Only one parameter may be specified");
    }

    AtomicReference<Page<Cocktail>> cocktails = new AtomicReference<>();
    all.ifPresent(search -> cocktails.set(cocktailService.searchByNameAndIngredient(all.get(), pageable)));
    name.ifPresent(search -> cocktails.set(cocktailService.searchByName(name.get(), pageable)));
    ingredient.ifPresent(search -> cocktails.set(cocktailService.searchByIngredient(ingredient.get(), pageable)));

    return transformer.transform(cocktails.get(), CocktailPageResponse.class);
  }

  @Override
  public CocktailListResponse getRandomCocktail(Optional<Integer> size) {
    var cocktails = cocktailService.getRandomCocktails(size.orElse(1)).stream()
        .map(cocktail -> transformer.transform(cocktail, CocktailModel.class)).toList();

    return new CocktailListResponse().data(cocktails);
  }
}
