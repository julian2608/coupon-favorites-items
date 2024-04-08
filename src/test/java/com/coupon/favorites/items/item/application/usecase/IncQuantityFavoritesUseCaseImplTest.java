package com.coupon.favorites.items.item.application.usecase;

import com.coupon.favorites.items.item.domain.service.IncQuantityFavoritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class IncQuantityFavoritesUseCaseImplTest {

    @InjectMocks
    private IncQuantityFavoritesUseCaseImpl incQuantityFavoritesUseCase;

    @Mock
    private IncQuantityFavoritesService incQuantityFavoritesService;

    private Collection<String> itemsId = Arrays.asList("1", "2", "3");

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    void execute_When_Ok() {
        incQuantityFavoritesUseCase.execute(itemsId);
        verify(incQuantityFavoritesService, times(1)).multiIncrementQuantity(itemsId);
    }

}