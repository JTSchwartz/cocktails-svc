package com.jtschwartz.cocktails.controller;

import com.jtschwartz.cocktails.api.definition.ApiApi;
import com.jtschwartz.cocktails.api.model.Cocktail;
import com.jtschwartz.cocktails.api.model.CocktailListResponse;
import com.jtschwartz.cocktails.service.CocktailService;
import com.jtschwartz.cocktails.util.Transformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CocktailsController implements ApiApi {

    private final CocktailService cocktailService;
    private final Transformer transformer;

    @Override
    public ResponseEntity<CocktailListResponse> getCocktails(Optional<String> name, Optional<String> ingredient) {
        if (name.isPresent() || ingredient.isPresent()) {
            throw new NotImplementedException();
        }

        var cocktails = cocktailService.getAllCocktails().stream()
                .map(e -> transformer.transform(e, Cocktail.class)).toList();

        return ResponseEntity.ok(
                new CocktailListResponse().data(cocktails)
        );
    }
}
