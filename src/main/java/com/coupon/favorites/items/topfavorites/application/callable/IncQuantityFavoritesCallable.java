package com.coupon.favorites.items.topfavorites.application.callable;

import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import java.util.Collection;

public class IncQuantityFavoritesCallable implements Runnable {
    private final Collection<String> favoritesItems;
    private final ItemFavoriteRepository itemFavoriteRepository;

    public IncQuantityFavoritesCallable(
            Collection<String> favoritesItems,
            ItemFavoriteRepository itemFavoriteRepository) {
        this.favoritesItems = favoritesItems;
        this.itemFavoriteRepository = itemFavoriteRepository;
    }

    @Override
    public void run() {
        itemFavoriteRepository.incrementQuantity(favoritesItems);
    }
}
