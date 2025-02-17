package com.jtschwartz.cocktails.controller;

import com.jtschwartz.cocktails.api.model.CocktailCreateRequest;
import com.jtschwartz.cocktails.constant.Unit;
import com.jtschwartz.cocktails.exception.BadRequestException;
import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.service.CocktailService;
import com.jtschwartz.cocktails.setup.TestConstant;
import com.jtschwartz.cocktails.util.Transformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.jtschwartz.cocktails.setup.TestUtil.assertContains;
import static com.jtschwartz.cocktails.setup.TestUtil.transformer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
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

  @Captor
  ArgumentCaptor<Cocktail> cocktailCaptor;

  @ParameterizedTest
  @NullAndEmptySource
  void getCocktails(List<String> filter) {
    when(cocktailService.getAllCocktails(any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var response = classUnderTest.getCocktails(Optional.ofNullable(filter), Optional.empty(), Optional.empty(), Optional.empty(), Pageable.unpaged());

    var result = response.getContent();
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

    var response = classUnderTest.getCocktails(Optional.of(List.of("filter")), Optional.empty(), Optional.empty(), Optional.empty(), Pageable.unpaged());

    var result = response.getContent();
    assertEquals(1, result.size());
    assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getFirst().getName());
    assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getFirst().getInstructions());
    assertContains("1oz Amaretto", result.getFirst().getIngredients());
    assertContains("1oz Bourbon", result.getFirst().getIngredients());
    assertContains("1oz Lemon Juice", result.getFirst().getIngredients());
    assertContains("1tsp Simple Syrup", result.getFirst().getIngredients());
    assertContains("1 Egg White", result.getFirst().getIngredients());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void getCocktails_All(List<String> filter) {
    when(cocktailService.searchByNameAndIngredient(any(), any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var response = classUnderTest.getCocktails(Optional.ofNullable(filter), Optional.of("all"), Optional.empty(), Optional.empty(), Pageable.unpaged());

    var result = response.getContent();
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
  void getCocktails_FilteredAll() {
    when(cocktailService.filterAndSearchByNameAndIngredient(any(), any(), any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var response = classUnderTest.getCocktails(Optional.of(List.of("filter")), Optional.of("all"), Optional.empty(), Optional.empty(), Pageable.unpaged());

    var result = response.getContent();
    assertEquals(1, result.size());
    assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getFirst().getName());
    assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getFirst().getInstructions());
    assertContains("1oz Amaretto", result.getFirst().getIngredients());
    assertContains("1oz Bourbon", result.getFirst().getIngredients());
    assertContains("1oz Lemon Juice", result.getFirst().getIngredients());
    assertContains("1tsp Simple Syrup", result.getFirst().getIngredients());
    assertContains("1 Egg White", result.getFirst().getIngredients());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void getCocktails_Name(List<String> filter) {
    when(cocktailService.searchByName(any(), any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var response = classUnderTest.getCocktails(Optional.ofNullable(filter), Optional.empty(), Optional.of("name"), Optional.empty(), Pageable.unpaged());

    var result = response.getContent();
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
  void getCocktails_FilteredName() {
    when(cocktailService.filterAndSearchByName(any(), any(), any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var response = classUnderTest.getCocktails(Optional.of(List.of("filter")), Optional.empty(), Optional.of("name"), Optional.empty(), Pageable.unpaged());

    var result = response.getContent();
    assertEquals(1, result.size());
    assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getFirst().getName());
    assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getFirst().getInstructions());
    assertContains("1oz Amaretto", result.getFirst().getIngredients());
    assertContains("1oz Bourbon", result.getFirst().getIngredients());
    assertContains("1oz Lemon Juice", result.getFirst().getIngredients());
    assertContains("1tsp Simple Syrup", result.getFirst().getIngredients());
    assertContains("1 Egg White", result.getFirst().getIngredients());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void getCocktails_Ingredient(List<String> filter) {
    when(cocktailService.searchByIngredient(any(), any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var response = classUnderTest.getCocktails(Optional.ofNullable(filter), Optional.empty(), Optional.empty(), Optional.of("ingredient"), Pageable.unpaged());

    var result = response.getContent();
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
  void getCocktails_FilteredIngredient() {
    when(cocktailService.filterAndSearchByIngredient(any(), any(), any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var response = classUnderTest.getCocktails(Optional.of(List.of("filter")), Optional.empty(), Optional.empty(), Optional.of("ingredient"), Pageable.unpaged());

    var result = response.getContent();
    assertEquals(1, result.size());
    assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getFirst().getName());
    assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getFirst().getInstructions());
    assertContains("1oz Amaretto", result.getFirst().getIngredients());
    assertContains("1oz Bourbon", result.getFirst().getIngredients());
    assertContains("1oz Lemon Juice", result.getFirst().getIngredients());
    assertContains("1tsp Simple Syrup", result.getFirst().getIngredients());
    assertContains("1 Egg White", result.getFirst().getIngredients());
  }

  static Stream<Arguments> getCocktails_BadRequest() {
    return Stream.of(
        Arguments.of(Optional.of(""), Optional.of(""), Optional.of("")),
        Arguments.of(Optional.empty(), Optional.of(""), Optional.of("")),
        Arguments.of(Optional.of(""), Optional.empty(), Optional.of("")),
        Arguments.of(Optional.of(""), Optional.of(""), Optional.empty())
    );
  }

  @MethodSource
  @ParameterizedTest
  void getCocktails_BadRequest(Optional<String> all, Optional<String> name, Optional<String> ingredient) {

    assertThrows(
        BadRequestException.class,
        () -> classUnderTest.getCocktails(Optional.empty(), all, name, ingredient, Pageable.unpaged())
    );
  }

  @Test
  void getCocktail() {
    when(cocktailService.getCocktail(anyString())).thenReturn(TestConstant.AMARETTO_SOUR);

    var response = classUnderTest.getCocktail("cocktail");

    var result = response.getData();
    assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getName());
    assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getInstructions());
    assertContains("1oz Amaretto", result.getIngredients());
    assertContains("1oz Bourbon", result.getIngredients());
    assertContains("1oz Lemon Juice", result.getIngredients());
    assertContains("1tsp Simple Syrup", result.getIngredients());
    assertContains("1 Egg White", result.getIngredients());
  }

  @Test
  void getRandomCocktail() {
    when(cocktailService.getRandomCocktails(1)).thenReturn(List.of(TestConstant.AMARETTO_SOUR));

    var response = classUnderTest.getRandomCocktail(Optional.empty());

    var result = response.getData();
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
  void createCocktail() {
    var createRequest = transformer.transform(TestConstant.SOUTHSIDE, CocktailCreateRequest.class);

    when(cocktailService.storeCocktail(any())).thenReturn(TestConstant.AMARETTO_SOUR);

    var response = classUnderTest.createCocktail(createRequest);

    var result = response.getData();
    assertEquals(TestConstant.AMARETTO_SOUR.getName(), result.getName());
    assertEquals(TestConstant.AMARETTO_SOUR.getInstructions(), result.getInstructions());
    assertContains("1oz Amaretto", result.getIngredients());
    assertContains("1oz Bourbon", result.getIngredients());
    assertContains("1oz Lemon Juice", result.getIngredients());
    assertContains("1tsp Simple Syrup", result.getIngredients());
    assertContains("1 Egg White", result.getIngredients());

    verify(cocktailService).storeCocktail(cocktailCaptor.capture());
    var input = cocktailCaptor.getValue();

    var gin = input.getIngredients().get(0);
    var simpleSyrup = input.getIngredients().get(1);
    var lemonJuice = input.getIngredients().get(2);
    var mintLeaves = input.getIngredients().get(3);
    assertEquals(TestConstant.SOUTHSIDE.getName(), input.getName());
    assertEquals(TestConstant.SOUTHSIDE.getInstructions(), input.getInstructions());

    assertEquals(2, gin.amount());
    assertEquals(Unit.OUNCE, gin.unit());
    assertEquals("Gin", gin.name());

    assertEquals(0.75f, simpleSyrup.amount());
    assertEquals(Unit.OUNCE, simpleSyrup.unit());
    assertEquals("Simple Syrup", simpleSyrup.name());

    assertEquals(1, lemonJuice.amount());
    assertEquals(Unit.OUNCE, lemonJuice.unit());
    assertEquals("Lemon Juice", lemonJuice.name());

    assertEquals(6, mintLeaves.amount());
    assertEquals(Unit.ITEM, mintLeaves.unit());
    assertEquals("Mint Leaves", mintLeaves.name());
  }

}