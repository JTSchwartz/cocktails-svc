package com.jtschwartz.cocktails.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransformerTest {

  @Mock
  ModelMapper modelMapper;

  @Spy
  @InjectMocks
  Transformer classUnderTest;

  @Test
  void transform_SadPath() {
    when(modelMapper.map("expected", Object.class)).thenThrow(new MappingException(List.of()));

    assertThrows(
        RuntimeException.class,
        () -> classUnderTest.transform("expected", Object.class)
    );
  }

}