package com.coupon.favorites.items.topfavorites.domain.service;

import java.util.Collection;

public interface IncQuantityFavoritesService {
    void multiIncrementQuantity(Collection<String> favoritesItems);
}
