package com.coupon.favorites.items.cache.application.usecase;

import com.coupon.favorites.items.cache.domain.service.GetPricesInCacheService;
import com.coupon.favorites.items.cache.domain.usecase.GetPricesInCacheUseCase;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;

import java.util.List;

public class GetPricesInCacheUseCaseImpl implements GetPricesInCacheUseCase {

    private final GetPricesInCacheService getPriceItemsIdService;

    public GetPricesInCacheUseCaseImpl(GetPricesInCacheService getPriceItemsIdService) {
        this.getPriceItemsIdService = getPriceItemsIdService;
    }
    @Override
    public List<Item> execute(List<String> ids) {
        return getPriceItemsIdService.getPriceItemsByIds(ids);
    }
}
