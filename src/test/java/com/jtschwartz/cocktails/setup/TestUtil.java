package com.jtschwartz.cocktails.setup;

import com.jtschwartz.cocktails.config.ModelMapperConfig;
import com.jtschwartz.cocktails.util.Transformer;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtil {

  public static ModelMapper modelMapper() {
    return new ModelMapperConfig().modelMapper();
  }

  public static Transformer transformer() {
    return new Transformer(modelMapper());
  }

  public static <T> void assertContains(T expected, Collection<T> collection) {
    assertTrue(collection.contains(expected));
  }

  public static <T, S> void assertContains(T expected, Function<S, T> func, Collection<S> collection) {
    assertContains(expected, collection.stream().map(func).toList());
  }

}
