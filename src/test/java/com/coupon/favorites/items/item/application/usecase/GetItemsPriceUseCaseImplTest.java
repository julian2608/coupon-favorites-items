package com.coupon.favorites.items.item.application.usecase;

import com.coupon.favorites.items.item.domain.service.GetItemsPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class GetItemsPriceUseCaseImplTest {
    @InjectMocks
    private GetItemsPriceUseCaseImpl getItemsPriceUseCase;

    @Mock
    private GetItemsPriceService getItemsPriceService;

    private Set<String> itemsId = new HashSet<>(Arrays.asList("1", "2", "3"));

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    void getItemsPrice_When_Ok() {
        getItemsPriceUseCase.execute(new HashSet<>(itemsId));
        verify(getItemsPriceService, times(1)).getItemsPrice(itemsId);
    }
}