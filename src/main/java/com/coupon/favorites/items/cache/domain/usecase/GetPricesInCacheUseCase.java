package com.coupon.favorites.items.cache.domain.usecase;

import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;

import java.util.Collection;
import java.util.List;

public interface GetPricesInCacheUseCase {
    List<Item> execute(List<String> ids);
}
