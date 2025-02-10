package com.jtschwartz.cocktails.controller;

import com.jtschwartz.cocktails.service.CocktailService;
import com.jtschwartz.cocktails.setup.TestConstant;
import com.jtschwartz.cocktails.util.Transformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static com.jtschwartz.cocktails.setup.TestUtil.assertContains;
import static org.junit.jupiter.api.Assertions.*;
import static com.jtschwartz.cocktails.setup.TestUtil.transformer;
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

    @Test
    void getCocktails() {
        when(cocktailService.getAllCocktails()).thenReturn(List.of(TestConstant.COCKTAIL));

        var response = classUnderTest.getCocktails(Optional.empty(), Optional.empty());

        var result = response.getBody().getData();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, result.size());
        assertEquals(TestConstant.COCKTAIL.getName(), result.get(0).getName());
        assertEquals(TestConstant.COCKTAIL.getInstructions(), result.get(0).getInstructions());
        assertContains("1oz Amaretto", result.get(0).getIngredients());
        assertContains("1oz Bourbon", result.get(0).getIngredients());
        assertContains("1oz Lemon Juice", result.get(0).getIngredients());
        assertContains("1tsp Simple Syrup", result.get(0).getIngredients());
        assertContains("1 Egg White", result.get(0).getIngredients());
    }

}