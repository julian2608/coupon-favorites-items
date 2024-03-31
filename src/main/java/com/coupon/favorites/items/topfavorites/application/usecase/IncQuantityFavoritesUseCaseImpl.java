package com.coupon.favorites.items.topfavorites.application.usecase;

import com.coupon.favorites.items.topfavorites.domain.service.IncQuantityFavoritesService;
import com.coupon.favorites.items.topfavorites.domain.usecase.IncQuantityFavoritesUseCase;

import java.util.Collection;
import java.util.List;

public class IncQuantityFavoritesUseCaseImpl implements IncQuantityFavoritesUseCase {
    private final IncQuantityFavoritesService incQuantityFavoritesService;
    public IncQuantityFavoritesUseCaseImpl(IncQuantityFavoritesService incQuantityFavoritesService) {
        this.incQuantityFavoritesService = incQuantityFavoritesService;
    }
    @Override
    public void execute(Collection<String> favoritesItems) {
         incQuantityFavoritesService.multiIncrementQuantity(favoritesItems);
    }
}
