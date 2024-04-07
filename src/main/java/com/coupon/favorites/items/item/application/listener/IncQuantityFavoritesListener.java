package com.coupon.favorites.items.item.application.listener;

import com.coupon.favorites.items.coupon.domain.event.IncCountFavoritesEvent;
import com.coupon.favorites.items.coupon.domain.event.IncCountFavoritesValueEvent;
import com.coupon.favorites.items.item.domain.usecase.IncQuantityFavoritesUseCase;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class IncQuantityFavoritesListener implements IncCountFavoritesEvent {
    private final IncQuantityFavoritesUseCase incQuantityFavoritesUseCase;

    public IncQuantityFavoritesListener(
            IncQuantityFavoritesUseCase incQuantityFavoritesUseCase
    ) {
        this.incQuantityFavoritesUseCase = incQuantityFavoritesUseCase;
    }

    @Override
    @EventListener
    public void incCountFavorites(IncCountFavoritesValueEvent eventValue) {
        incQuantityFavoritesUseCase.execute(eventValue.getItemsId());
    }
}
