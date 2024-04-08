package com.coupon.favorites.items.cache.application.usecase;

import com.coupon.favorites.items.cache.domain.service.SavePricesInCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.HashSet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class SavePricesInCacheUseCaseImplTest {

    @InjectMocks
    private SavePricesInCacheUseCaseImpl savePricesInCacheUseCase;

    @Mock
    private SavePricesInCacheService savePricesInCacheService;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void execute() {
        savePricesInCacheUseCase.execute(new HashSet<>());
        verify(savePricesInCacheService, times(1)).savePriceItems(new HashSet<>());
    }
}