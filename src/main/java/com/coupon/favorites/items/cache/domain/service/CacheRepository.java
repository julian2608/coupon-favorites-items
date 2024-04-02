package com.coupon.favorites.items.cache.domain.service;

import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;

import java.util.Collection;
import java.util.List;

public interface CacheRepository {
    List<Item> getPriceItemsByIds(Collection<String> ids);

    void savePriceItems(Collection<Item> ids);

}
