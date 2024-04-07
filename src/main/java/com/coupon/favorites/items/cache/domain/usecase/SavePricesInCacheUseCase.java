package com.coupon.favorites.items.cache.domain.usecase;

import com.coupon.favorites.items.coupon.domain.valueobject.Item;

import java.util.Collection;

public interface SavePricesInCacheUseCase {
    void execute(Collection<Item> ids);
}
