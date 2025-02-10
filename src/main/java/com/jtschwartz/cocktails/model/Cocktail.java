package com.jtschwartz.cocktails.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("${spring.data.mongodb.collection-prefix}-cocktails")
public class Cocktail {
    @Id
    String id;

    @Indexed(unique = true)
    String name;

    String instructions;

    List<Ingredient> ingredients;
}
