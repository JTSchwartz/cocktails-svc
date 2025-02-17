package com.jtschwartz.cocktails.data;

import com.jtschwartz.cocktails.model.Cocktail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CocktailRepository extends MongoRepository<Cocktail, String> {

  Optional<Cocktail> findByName(String name);

  @Aggregation("{ $sample: { size: ?0 } }")
  List<Cocktail> getRandomCocktails(int sample);

  @Query("{ $text: { $search: ?0 } }")
  Page<Cocktail> filterCocktails(String text, Pageable pageable);

  Stream<Cocktail> findAllBy();

}
