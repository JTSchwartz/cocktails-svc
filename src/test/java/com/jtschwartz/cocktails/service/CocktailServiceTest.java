package com.jtschwartz.cocktails.service;

import com.jtschwartz.cocktails.data.CocktailRepository;
import com.jtschwartz.cocktails.exception.NotFoundException;
import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.setup.TestConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.jtschwartz.cocktails.setup.TestConstant.*;
import static com.jtschwartz.cocktails.setup.TestUtil.assertContains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CocktailServiceTest {

  @Mock
  CocktailRepository cocktailRepository;

  @Spy
  @InjectMocks
  CocktailService classUnderTest;

  @Test
  void getAllCocktails() {
    when(cocktailRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var result = classUnderTest.getAllCocktails(Pageable.unpaged());

    assertEquals(1, result.getSize());
    assertContains(TestConstant.AMARETTO_SOUR, result.getContent());
  }

  @Test
  void getCocktail_HappyPath() {
      when(cocktailRepository.findByName(anyString())).thenReturn(Optional.of(AMARETTO_SOUR));

      assertEquals(AMARETTO_SOUR, classUnderTest.getCocktail("cocktail"));
  }

  @Test
  void getCocktail_SadPath() {
      when(cocktailRepository.findByName(anyString())).thenReturn(Optional.empty());

      assertThrows(
          NotFoundException.class,
          () -> classUnderTest.getCocktail("cocktail")
      );
  }

  @Test
  void getRandomCocktails() {
    when(cocktailRepository.getRandomCocktails(anyInt())).thenReturn(List.of(TestConstant.AMARETTO_SOUR));

    var result = classUnderTest.getRandomCocktails(1);

    assertEquals(1, result.size());
    assertContains(TestConstant.AMARETTO_SOUR, result);
  }

  @Test
  void storeCocktail() {
    when(cocktailRepository.save(any())).thenReturn(TestConstant.AMARETTO_SOUR);

    var result = classUnderTest.storeCocktail(SOUTHSIDE);

    assertEquals(TestConstant.AMARETTO_SOUR, result);
  }

  @Test
  void filterCocktails() {
    var expected = "mint bourbon";

    when(cocktailRepository.filterCocktails(anyString(), any())).thenReturn(new PageImpl<>(List.of(TestConstant.AMARETTO_SOUR)));

    var result = classUnderTest.filterCocktails(Arrays.stream(expected.split(" ")).toList(), Pageable.unpaged());

    verify(cocktailRepository).filterCocktails(eq(expected), any());

    assertEquals(1, result.getSize());
    assertContains(TestConstant.AMARETTO_SOUR, result.getContent());
  }

  static Stream<Arguments> searchByName() {
    return Stream.of(
        Arguments.of("Amaretto", AMARETTO_SOUR, SOUTHSIDE),
        Arguments.of("South", SOUTHSIDE, AMARETTO_SOUR)
    );
  }

  @MethodSource
  @ParameterizedTest
  void searchByName(String search, Cocktail first, Cocktail second) {
    when(cocktailRepository.findAll()).thenReturn(COCKTAILS);

    var pageable0 = PageRequest.of(0, 1);
    var pageable1 = PageRequest.of(1, 1);

    var page0 = classUnderTest.searchByName(search, pageable0);
    var page1 = classUnderTest.searchByName(search, pageable1);

    assertEquals(2, page0.getTotalPages());
    assertEquals(2, page1.getTotalPages());
    assertEquals(2, page0.getTotalElements());
    assertEquals(2, page1.getTotalElements());
    assertEquals(1, page0.getNumberOfElements());
    assertEquals(1, page1.getNumberOfElements());
    assertEquals(1, page0.getSize());
    assertEquals(1, page1.getSize());
    assertContains(first, page0.getContent());
    assertContains(second, page1.getContent());
  }

  @ParameterizedTest
  @MethodSource("searchByName")
  void filterAndSearchByName(String search, Cocktail first, Cocktail second) {
    when(cocktailRepository.filterCocktails(anyString())).thenReturn(COCKTAILS);

    var pageable0 = PageRequest.of(0, 1);
    var pageable1 = PageRequest.of(1, 1);

    var page0 = classUnderTest.filterAndSearchByName(List.of("filter"), search, pageable0);
    var page1 = classUnderTest.filterAndSearchByName(List.of("filter"), search, pageable1);

    assertEquals(2, page0.getTotalPages());
    assertEquals(2, page1.getTotalPages());
    assertEquals(2, page0.getTotalElements());
    assertEquals(2, page1.getTotalElements());
    assertEquals(1, page0.getNumberOfElements());
    assertEquals(1, page1.getNumberOfElements());
    assertEquals(1, page0.getSize());
    assertEquals(1, page1.getSize());
    assertContains(first, page0.getContent());
    assertContains(second, page1.getContent());
  }

  static Stream<Arguments> searchByIngredient() {
    return Stream.of(
        Arguments.of("mitn", SOUTHSIDE),
        Arguments.of("bourn", AMARETTO_SOUR)
    );
  }

  @MethodSource
  @ParameterizedTest
  void searchByIngredient(String search, Cocktail cocktail) {
    when(cocktailRepository.findAll()).thenReturn(COCKTAILS);

    var result = classUnderTest.searchByIngredient(search, PageRequest.of(0, 1));

    assertEquals(cocktail, result.getContent().getFirst());
  }

  @ParameterizedTest
  @MethodSource("searchByIngredient")
  void filterAndSearchByIngredient(String search, Cocktail cocktail) {
    when(cocktailRepository.filterCocktails(anyString())).thenReturn(COCKTAILS);

    var result = classUnderTest.filterAndSearchByIngredient(List.of("filter"), search, PageRequest.of(0, 1));

    assertEquals(cocktail, result.getContent().getFirst());
  }

  static Stream<Arguments> searchByNameAndIngredient() {
    return Stream.of(
        Arguments.of("mitn", SOUTHSIDE),
        Arguments.of("suoth", SOUTHSIDE),
        Arguments.of("bourn", AMARETTO_SOUR),
        Arguments.of("jeffry", AMARETTO_SOUR)
    );
  }

  @MethodSource
  @ParameterizedTest
  void searchByNameAndIngredient(String search, Cocktail cocktail) {
    when(cocktailRepository.findAll()).thenReturn(COCKTAILS);

    var result = classUnderTest.searchByNameAndIngredient(search, PageRequest.of(0, 1));

    assertEquals(cocktail, result.getContent().getFirst());
  }

  @ParameterizedTest
  @MethodSource("searchByNameAndIngredient")
  void filterAndSearchByNameAndIngredient(String search, Cocktail cocktail) {
    when(cocktailRepository.filterCocktails(anyString())).thenReturn(COCKTAILS);

    var result = classUnderTest.filterAndSearchByNameAndIngredient(List.of("filter"), search, PageRequest.of(0, 1));

    assertEquals(cocktail, result.getContent().getFirst());
  }

}