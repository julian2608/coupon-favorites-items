package com.coupon.favorites.items.topfavorites.domain.usecase;

import java.util.Collection;
import java.util.List;

public interface IncQuantityFavoritesUseCase {
    void execute(Collection<String> favoritesItems);

}
