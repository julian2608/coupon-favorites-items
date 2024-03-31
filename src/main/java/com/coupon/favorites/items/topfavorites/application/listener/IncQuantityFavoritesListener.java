package com.coupon.favorites.items.topfavorites.application.listener;

import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import com.coupon.favorites.items.topfavorites.domain.usecase.IncQuantityFavoritesUseCase;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class IncQuantityFavoritesListener {
    private final IncQuantityFavoritesUseCase incQuantityFavoritesUseCase;

    public IncQuantityFavoritesListener(
            IncQuantityFavoritesUseCase incQuantityFavoritesUseCase
    ) {
        this.incQuantityFavoritesUseCase = incQuantityFavoritesUseCase;
    }

    @EventListener
    private void updateBrandAddAttr(ItemsId itemsId) {
        incQuantityFavoritesUseCase.execute(itemsId.getValue());
    }
}
