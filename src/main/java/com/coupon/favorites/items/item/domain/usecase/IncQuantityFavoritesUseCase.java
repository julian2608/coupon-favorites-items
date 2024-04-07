package com.coupon.favorites.items.item.domain.usecase;

import java.util.Collection;

public interface IncQuantityFavoritesUseCase {
    void execute(Collection<String> favoritesItems);

}
