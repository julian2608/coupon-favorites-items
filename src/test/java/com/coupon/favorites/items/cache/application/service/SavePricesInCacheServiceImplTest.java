package com.coupon.favorites.items.cache.application.service;

import com.coupon.favorites.items.cache.domain.service.CacheRepository;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import com.coupon.favorites.items.item.domain.exception.ItemRepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class SavePricesInCacheServiceImplTest {

    @InjectMocks
    private SavePricesInCacheServiceImpl savePricesInCacheService;

    @Mock
    private CacheRepository cacheRepository;

    private List<Item> items;

    @BeforeEach
    public void setUp() {
        items = List.of(Item.builder().id("MCO32175689").price(10.0).build());
        openMocks(this);
    }

    @Test
    public void savePriceItems_When_Ok() {
        savePricesInCacheService.savePriceItems(items);

        verify(cacheRepository, times(1)).savePriceItems(items);
    }

    @Test
    public void savePriceItems_When_Repository_Exception() {
        doThrow(new RuntimeException("Repository error")).when(cacheRepository).savePriceItems(items);

        assertThrows(ItemRepositoryException.class, () -> savePricesInCacheService.savePriceItems(items));

        verify(cacheRepository, times(1)).savePriceItems(items);
    }
}