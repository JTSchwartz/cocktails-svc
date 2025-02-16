package com.jtschwartz.cocktails.controller;

import com.jtschwartz.cocktails.service.CocktailService;
import com.jtschwartz.cocktails.setup.TestConstant;
import com.jtschwartz.cocktails.util.Transformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static com.jtschwartz.cocktails.setup.TestUtil.assertContains;
import static com.jtschwartz.cocktails.setup.TestUtil.transformer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CocktailsControllerTest {

    @Spy
    Transformer transformer = transformer();

    @Mock
    CocktailService cocktailService;

    @Spy
    @InjectMocks
    CocktailsController classUnderTest;

    @ParameterizedTest
    @NullAndEmptySource
    void getCocktails(List<String> filter) {
        when(cocktailService.getAllCocktails(any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

        var response = classUnderTest.getCocktails(Optional.ofNullable(filter), Pageable.unpaged());

        var result = response.getBody().getContent();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, result.size());
        assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getFirst().getName());
        assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getFirst().getInstructions());
        assertContains("1oz Amaretto", result.getFirst().getIngredients());
        assertContains("1oz Bourbon", result.getFirst().getIngredients());
        assertContains("1oz Lemon Juice", result.getFirst().getIngredients());
        assertContains("1tsp Simple Syrup", result.getFirst().getIngredients());
        assertContains("1 Egg White", result.getFirst().getIngredients());
    }

    @Test
    void getCocktailsFiltered() {
        when(cocktailService.filterCocktails(any(), any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

        var response = classUnderTest.getCocktails(Optional.of(List.of("filter")), Pageable.unpaged());

        var result = response.getBody().getContent();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, result.size());
        assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getFirst().getName());
        assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getFirst().getInstructions());
        assertContains("1oz Amaretto", result.getFirst().getIngredients());
        assertContains("1oz Bourbon", result.getFirst().getIngredients());
        assertContains("1oz Lemon Juice", result.getFirst().getIngredients());
        assertContains("1tsp Simple Syrup", result.getFirst().getIngredients());
        assertContains("1 Egg White", result.getFirst().getIngredients());
    }

    @Test
    void getRandomCocktail() {
        when(cocktailService.getRandomCocktails(1)).thenReturn(List.of(TestConstant.AMARETTO_SOUR));

        var response = classUnderTest.getRandomCocktail(Optional.empty());

        var result = response.getBody().getData();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, result.size());
        assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getFirst().getName());
        assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getFirst().getInstructions());
        assertContains("1oz Amaretto", result.getFirst().getIngredients());
        assertContains("1oz Bourbon", result.getFirst().getIngredients());
        assertContains("1oz Lemon Juice", result.getFirst().getIngredients());
        assertContains("1tsp Simple Syrup", result.getFirst().getIngredients());
        assertContains("1 Egg White", result.getFirst().getIngredients());
    }

}