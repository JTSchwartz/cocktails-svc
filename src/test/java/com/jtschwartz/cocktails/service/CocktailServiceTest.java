package com.jtschwartz.cocktails.service;

import com.jtschwartz.cocktails.data.CocktailRepository;
import com.jtschwartz.cocktails.setup.TestConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.jtschwartz.cocktails.setup.TestUtil.assertContains;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        when(cocktailRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(TestConstant.COCKTAIL)));

        var result = classUnderTest.getAllCocktails(Pageable.unpaged());

        assertEquals(1, result.getSize());
        assertContains(TestConstant.COCKTAIL, result.getContent());
    }

}