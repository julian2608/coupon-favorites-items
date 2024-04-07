package com.coupon.favorites.items.item.domain.service;

import java.util.Collection;

public interface IncQuantityFavoritesService {
    void multiIncrementQuantity(Collection<String> favoritesItems);
}
