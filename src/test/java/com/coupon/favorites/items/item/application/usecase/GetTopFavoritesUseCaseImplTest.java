package com.coupon.favorites.items.item.application.usecase;

import com.coupon.favorites.items.item.domain.service.GetTopFavoritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class GetTopFavoritesUseCaseImplTest {
    @InjectMocks
    private GetTopFavoritesUseCaseImpl getTopFavoritesUseCase;

    @Mock
    private GetTopFavoritesService getTopFavoritesService;

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    public void getTopFavorites_When_Ok() {
        getTopFavoritesUseCase.execute(10);
        verify(getTopFavoritesService, times(1)).execute(10);
    }

}