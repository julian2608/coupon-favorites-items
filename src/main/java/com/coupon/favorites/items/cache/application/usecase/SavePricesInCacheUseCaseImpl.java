package com.coupon.favorites.items.cache.application.usecase;

import com.coupon.favorites.items.cache.domain.service.SavePricesInCacheService;
import com.coupon.favorites.items.cache.domain.usecase.SavePricesInCacheUseCase;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;

import java.util.Collection;

public class SavePricesInCacheUseCaseImpl implements SavePricesInCacheUseCase {

    private final SavePricesInCacheService savePricesInCacheService;

    public SavePricesInCacheUseCaseImpl(SavePricesInCacheService savePricesInCacheService) {
        this.savePricesInCacheService = savePricesInCacheService;
    }
    @Override
    public void execute(Collection<Item> ids) {
        savePricesInCacheService.savePriceItems(ids);
    }
}
