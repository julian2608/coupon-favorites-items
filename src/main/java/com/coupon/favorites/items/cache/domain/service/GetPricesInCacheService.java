package com.coupon.favorites.items.cache.domain.service;

import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;

import java.util.List;

public interface GetPricesInCacheService {
    List<Item> getPriceItemsByIds(List<String> ids);
}
