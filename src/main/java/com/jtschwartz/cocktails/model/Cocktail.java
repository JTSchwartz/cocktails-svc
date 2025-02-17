package com.jtschwartz.cocktails.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("${spring.data.mongodb.collection-prefix}-cocktails")
public class Cocktail implements Comparable<Cocktail> {
  @Id
  String id;

  @TextIndexed(weight = 2)
  @Indexed(unique = true)
  String name;

  String instructions;

  List<Ingredient> ingredients;

  @Override
  public int compareTo(Cocktail o) {
    return name.compareTo(o.name);
  }
}
