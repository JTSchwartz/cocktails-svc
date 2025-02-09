package com.jtschwartz.cocktails.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("${spring.data.mongodb.collection-prefix}-cocktails")
public class Cocktail {
    @Id
    String id;

    @Indexed(unique = true)
    final String name;

    final String instructions;

    final List<Ingredient> ingredients;
}
