package com.coupon.favorites.items.item.application.usecase;

import com.coupon.favorites.items.item.domain.service.IncQuantityFavoritesService;
import com.coupon.favorites.items.item.domain.usecase.IncQuantityFavoritesUseCase;

import java.util.Collection;

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
