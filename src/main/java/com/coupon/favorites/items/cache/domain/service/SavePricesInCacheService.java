package com.coupon.favorites.items.cache.domain.service;

import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;

import java.util.Collection;

public interface SavePricesInCacheService {
    void savePriceItems(Collection<Item> ids);
}
