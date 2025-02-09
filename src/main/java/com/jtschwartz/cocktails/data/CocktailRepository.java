package com.jtschwartz.cocktails.data;

import com.jtschwartz.cocktails.model.Cocktail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CocktailRepository extends MongoRepository<Cocktail,String> {
}
