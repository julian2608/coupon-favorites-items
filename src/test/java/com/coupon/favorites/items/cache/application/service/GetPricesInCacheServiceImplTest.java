package com.coupon.favorites.items.cache.application.service;

import com.coupon.favorites.items.cache.domain.service.CacheRepository;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import com.coupon.favorites.items.item.domain.entity.ItemPriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class GetPricesInCacheServiceImplTest {

    @InjectMocks
    private GetPricesInCacheServiceImpl getPricesInCacheService;

    @Mock
    private CacheRepository cacheRepository;

    private Collection<String> ids;

    private List<Item> mockResponseCache;

    @BeforeEach
    public void setUp() {
        ids = new ArrayList<>(List.of("MCO32175689","MCO543789","MCO432765","MCO4354765","MCO4328796","MCO4323245","MCO4327568","MCO4323187","MCO4323472","MCO4326479"));

        mockResponseCache = ids.stream().map(item -> Item.builder()
                .id(item)
                .price(Double.parseDouble(item.substring(item.length()-4)))
                .build()).toList();

        openMocks(this);
    }

    @Test
    public void getPriceItemsByIds_When_Ok() {
        when(cacheRepository.getPriceItemsByIds(ids)).thenReturn(mockResponseCache);

        List<Item> items = getPricesInCacheService.getPriceItemsByIds(ids.stream().toList());

        assertEquals(10, items.size());
        assertEquals(mockResponseCache, items);
        verify(cacheRepository, times(1)).getPriceItemsByIds(ids);
    }

    @Test
    public void getPriceItemsByIds_When_Repository_Exception() {
        doThrow(new RuntimeException("Repository error")).when(cacheRepository).getPriceItemsByIds(ids);

        List<Item> items = getPricesInCacheService.getPriceItemsByIds(ids.stream().toList());

        assertEquals(0, items.size());
        verify(cacheRepository, times(1)).getPriceItemsByIds(ids);
    }

}