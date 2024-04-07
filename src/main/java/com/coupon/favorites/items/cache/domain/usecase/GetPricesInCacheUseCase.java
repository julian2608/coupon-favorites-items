package com.coupon.favorites.items.cache.domain.usecase;

import com.coupon.favorites.items.coupon.domain.valueobject.Item;

import java.util.List;

public interface GetPricesInCacheUseCase {
    List<Item> execute(List<String> ids);
}
