package com.coupon.favorites.items.item.application.service;

import com.coupon.favorites.items.item.domain.exception.IncrementQuantityException;
import com.coupon.favorites.items.item.domain.repository.ItemFavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class IncQuantityFavoritesServiceImplTest {
    @InjectMocks
    private IncQuantityFavoritesServiceImpl incQuantityFavoritesService;

    @Mock
    private ItemFavoriteRepository itemFavoriteRepository;

    private Collection<String> favoritesItems;

    @BeforeEach
    public void init() {
        favoritesItems = List.of("MCO32175625","MCO543789","MCO432750","MCO4354765","MCO4328796");

        openMocks(this);
    }

    @Test
    public void multiIncrementQuantity_When_Ok() {
        incQuantityFavoritesService.multiIncrementQuantity(favoritesItems);

        verify(itemFavoriteRepository, times(1)).incrementQuantity(favoritesItems);
    }

    @Test
    public void testMultiIncrementQuantity_RepositoryException() {
        Collection<String> favoritesItems = Arrays.asList("item1", "item2");

        doThrow(new RuntimeException("Repository exception")).when(itemFavoriteRepository).incrementQuantity(favoritesItems);

        assertThrows(IncrementQuantityException.class, () -> incQuantityFavoritesService.multiIncrementQuantity(favoritesItems));
    }

}