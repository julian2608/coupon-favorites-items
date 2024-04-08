package com.coupon.favorites.items.item.application.listener;

import com.coupon.favorites.items.coupon.domain.event.IncCountFavoritesValueEvent;
import com.coupon.favorites.items.item.domain.usecase.IncQuantityFavoritesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class IncQuantityFavoritesListenerTest {

    @InjectMocks
    private IncQuantityFavoritesListener incQuantityFavoritesListener;

    @Mock
    private IncQuantityFavoritesUseCase incQuantityFavoritesUseCase;

    private final Set<String> itemsId = new HashSet<>(Arrays.asList("1", "2", "3"));

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    public void incCountFavorites_When_Ok() {
        incQuantityFavoritesListener.incCountFavorites(new IncCountFavoritesValueEvent(itemsId));

        verify(incQuantityFavoritesUseCase, times(1)).execute(itemsId);
    }

}