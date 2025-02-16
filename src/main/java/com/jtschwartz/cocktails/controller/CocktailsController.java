package com.jtschwartz.cocktails.controller;

import com.jtschwartz.cocktails.api.definition.ApiApi;
import com.jtschwartz.cocktails.api.model.CocktailListResponse;
import com.jtschwartz.cocktails.api.model.CocktailModel;
import com.jtschwartz.cocktails.api.model.CocktailPageResponse;
import com.jtschwartz.cocktails.service.CocktailService;
import com.jtschwartz.cocktails.util.Transformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CocktailsController implements ApiApi {

    private final CocktailService cocktailService;
    private final Transformer transformer;

    @Override
    public ResponseEntity<CocktailPageResponse> getCocktails(Optional<List<String>> filter, Pageable pageable) {
        var cocktails = filter.isEmpty() || filter.get().isEmpty()
                ? cocktailService.getAllCocktails(pageable)
                : cocktailService.filterCocktails(filter.get(), pageable);

        return ResponseEntity.ok(
                transformer.transform(cocktails, CocktailPageResponse.class)
        );
    }

    @Override
    public ResponseEntity<CocktailListResponse> getRandomCocktail(Optional<Integer> size) {
        var cocktails = cocktailService.getRandomCocktails(size.orElse(1)).stream()
                .map(cocktail -> transformer.transform(cocktail, CocktailModel.class)).toList();

        return ResponseEntity.ok(new CocktailListResponse().data(cocktails));
    }
}
