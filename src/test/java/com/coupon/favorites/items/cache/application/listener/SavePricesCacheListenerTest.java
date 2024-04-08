package com.coupon.favorites.items.cache.application.listener;

import com.coupon.favorites.items.cache.domain.usecase.SavePricesInCacheUseCase;
import com.coupon.favorites.items.coupon.domain.event.SaveCachePricesValueEvent;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class SavePricesCacheListenerTest {
    @InjectMocks
    private SavePricesCacheListener savePricesCacheListener;

    @Mock
    private SavePricesInCacheUseCase savePricesInCacheUseCase;

    private final List<Item> items = List.of();

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    public void savePricesInCache_When_Ok() {
        savePricesCacheListener.savePricesInCache(new SaveCachePricesValueEvent(items));

        verify(savePricesInCacheUseCase, times(1)).execute(List.of());
    }

}