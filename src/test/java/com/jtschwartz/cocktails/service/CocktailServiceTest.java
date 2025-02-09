package com.jtschwartz.cocktails.service;

import com.jtschwartz.cocktails.data.CocktailRepository;
import com.jtschwartz.cocktails.setup.TestConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.jtschwartz.cocktails.setup.TestUtil.assertContains;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CocktailServiceTest {

    @Mock
    CocktailRepository cocktailRepository;

    @Spy
    @InjectMocks
    CocktailService classUnderTest;

    @Test
    void getAllCocktails() {
        when(cocktailRepository.findAll()).thenReturn(List.of(TestConstant.COCKTAIL));

        var result = classUnderTest.getAllCocktails();

        assertEquals(1, result.size());
        assertContains(TestConstant.COCKTAIL, result);
    }

}