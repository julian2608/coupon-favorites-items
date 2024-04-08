package com.coupon.favorites.items.item.application.service;


import com.coupon.favorites.items.item.domain.entity.ErrorItem;
import com.coupon.favorites.items.item.domain.entity.ItemFavorite;
import com.coupon.favorites.items.item.domain.repository.ItemFavoriteRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class GetTopFavoritesServiceImplTest {

    @InjectMocks
    private GetTopFavoritesServiceImpl getTopFavoritesService;

    @Mock
    private ItemFavoriteRepository itemFavoriteRepository;

    private List<ItemFavorite> items;

    @BeforeEach
    public void init() {
        List<String> itemsId = List.of("MCO32175625","MCO543789","MCO432750","MCO4354765","MCO4328796");

        items = itemsId.stream()
                .map(item -> ItemFavorite.builder()
                        .id(item)
                        .quantity(Integer.parseInt(item.substring(item.length() - 2)))
                        .build())
                .toList();

        openMocks(this);
    }

    @Test
    public void getTopFavorites_When_Ok() {
        when(itemFavoriteRepository.getTopFavorites(5)).thenReturn(items);

        Either<ErrorItem, List<ItemFavorite>> result = getTopFavoritesService.execute(5);

        verify(itemFavoriteRepository, times(1)).getTopFavorites(5);
        assertTrue(result.isRight());
        assertEquals(items, result.get());

    }

    @Test
    public void getTopFavorites_When_Result_Db_Empty() {
        when(itemFavoriteRepository.getTopFavorites(5)).thenReturn(new ArrayList<>());

        Either<ErrorItem, List<ItemFavorite>> result = getTopFavoritesService.execute(5);


        verify(itemFavoriteRepository, times(1)).getTopFavorites(5);
        assertTrue(result.isLeft());
        assertEquals(ErrorItem.ErrorGettingFavorites, result.getLeft());

    }

    @Test
    public void testGetTopFavorites_RepositoryException() {
        when(itemFavoriteRepository.getTopFavorites(anyInt())).thenThrow(new RuntimeException("Repository error"));

        Either<ErrorItem, List<ItemFavorite>> result = getTopFavoritesService.execute(5);

        assertTrue(result.isLeft());
        assertEquals(ErrorItem.ExceptionGettingFavorites, result.getLeft());
    }

}