package com.jtschwartz.cocktails.service;

import com.jtschwartz.cocktails.constant.Unit;
import com.jtschwartz.cocktails.data.CocktailRepository;
import com.jtschwartz.cocktails.model.Cocktail;
import com.jtschwartz.cocktails.model.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CocktailService {

    private final CocktailRepository cocktailRepository;

    public Page<Cocktail> getAllCocktails(Pageable pageable) {
        return cocktailRepository.findAll(pageable);
    }

    public List<Cocktail> getRandomCocktails(int sampleSize) {
        return cocktailRepository.getRandomCocktails(sampleSize);
    }

}
