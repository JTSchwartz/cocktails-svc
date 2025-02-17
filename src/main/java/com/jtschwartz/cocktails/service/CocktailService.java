package com.jtschwartz.cocktails.service;

import com.jtschwartz.cocktails.data.CocktailRepository;
import com.jtschwartz.cocktails.exception.NotFoundException;
import com.jtschwartz.cocktails.model.Cocktail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.jtschwartz.cocktails.util.CocktailUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CocktailService {

  private final CocktailRepository cocktailRepository;

  public Page<Cocktail> getAllCocktails(Pageable pageable) {
    return cocktailRepository.findAll(pageable);
  }

  public Cocktail getCocktail(String name) {
    return cocktailRepository.findByName(name).orElseThrow(() -> new NotFoundException(name));
  }

  public List<Cocktail> getRandomCocktails(int sampleSize) {
    return cocktailRepository.getRandomCocktails(sampleSize);
  }

  public Page<Cocktail> filterCocktails(List<String> filter, Pageable pageable) {
    return cocktailRepository.filterCocktails(Strings.join(filter, ' '), pageable);
  }

  public List<Cocktail> filterCocktails(List<String> filter) {
    return cocktailRepository.filterCocktails(Strings.join(filter, ' '));
  }

  public Page<Cocktail> searchByName(String name, Pageable pageable) {
    return searchBy(cocktailRepository.findAll(), sortByName(name), pageable);
  }

  public Page<Cocktail> filterAndSearchByName(List<String> filter, String name, Pageable pageable) {
    return searchBy(filterCocktails(filter), sortByName(name), pageable);
  }

  public Page<Cocktail> searchByIngredient(String ingredient, Pageable pageable) {
    return searchBy(cocktailRepository.findAll(), sortByIngredient(ingredient), pageable);
  }

  public Page<Cocktail> filterAndSearchByIngredient(List<String> filter, String ingredient, Pageable pageable) {
    return searchBy(filterCocktails(filter), sortByIngredient(ingredient), pageable);
  }

  public Page<Cocktail> searchByNameAndIngredient(String target, Pageable pageable) {
    return searchBy(cocktailRepository.findAll(), sortByNameAndIngredient(target), pageable);
  }

  public Page<Cocktail> filterAndSearchByNameAndIngredient(List<String> filter, String target, Pageable pageable) {
    return searchBy(filterCocktails(filter), sortByNameAndIngredient(target), pageable);
  }

  protected Page<Cocktail> searchBy(List<Cocktail> cocktails, Comparator<Cocktail> comparator, Pageable pageable) {
    cocktails.sort(comparator);

    return new PageImpl<>(cocktails, pageable, cocktails.size());
  }

}
