package com.coupon.favorites.items.cache.application.listener;


import com.coupon.favorites.items.cache.domain.usecase.SavePricesInCacheUseCase;
import com.coupon.favorites.items.maximizedcoupon.domain.event.SaveCachePricesEvent;
import com.coupon.favorites.items.maximizedcoupon.domain.event.SaveCachePricesValueEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SavePricesCacheListener implements SaveCachePricesEvent {
    private final SavePricesInCacheUseCase savePricesInCacheUseCase;

    public SavePricesCacheListener(SavePricesInCacheUseCase savePricesInCacheUseCase) {
        this.savePricesInCacheUseCase = savePricesInCacheUseCase;
    }

    @Override
    @EventListener
    public void savePricesInCache(SaveCachePricesValueEvent valueEvent) {
        savePricesInCacheUseCase.execute(valueEvent.getItems());
    }
}
