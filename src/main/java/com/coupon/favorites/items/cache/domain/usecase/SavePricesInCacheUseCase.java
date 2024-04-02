package com.coupon.favorites.items.cache.domain.usecase;

import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;

import java.util.Collection;

public interface SavePricesInCacheUseCase {
    void execute(Collection<Item> ids);
}
