package com.jtschwartz.cocktails.data;

import com.jtschwartz.cocktails.model.Cocktail;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CocktailRepository extends MongoRepository<Cocktail,String> {

    @Aggregation("{ $sample: { size: ?0 } }")
    List<Cocktail> getRandomCocktails(int sample);

}
