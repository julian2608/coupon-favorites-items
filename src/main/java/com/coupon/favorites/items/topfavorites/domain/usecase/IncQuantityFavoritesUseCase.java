package com.coupon.favorites.items.topfavorites.domain.usecase;

import java.util.Collection;

public interface IncQuantityFavoritesUseCase {
    void execute(Collection<String> favoritesItems);

}
