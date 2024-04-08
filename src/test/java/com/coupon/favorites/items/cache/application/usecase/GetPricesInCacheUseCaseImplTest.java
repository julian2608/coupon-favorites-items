package com.coupon.favorites.items.cache.application.usecase;

import com.coupon.favorites.items.cache.domain.service.GetPricesInCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class GetPricesInCacheUseCaseImplTest {

    @InjectMocks
    private GetPricesInCacheUseCaseImpl getPricesInCacheUseCase;

    @Mock
    private GetPricesInCacheService getPriceItemsIdService;

    private List<String> ids = List.of("MCO32175689");

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    void execute() {
        getPricesInCacheUseCase.execute(ids);
        verify(getPriceItemsIdService, times(1)).getPriceItemsByIds(ids);
    }
}