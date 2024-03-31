package com.coupon.favorites.items.topfavorites.application.callable;

import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import java.util.Collection;
import java.util.concurrent.Callable;

public class IncQuantityFavoritesCallable implements Callable<Integer> {
    private final Collection<String> favoritesItems;
    private final ItemFavoriteRepository itemFavoriteRepository;

    public IncQuantityFavoritesCallable(
            Collection<String> favoritesItems,
            ItemFavoriteRepository itemFavoriteRepository) {
        this.favoritesItems = favoritesItems;
        this.itemFavoriteRepository = itemFavoriteRepository;
    }

    @Override
    public Integer call() {
        favoritesItems.forEach(itemFavoriteRepository::incrementQuantityById);
        return 1;
    }
}
