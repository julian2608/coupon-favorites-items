package com.coupon.favorites.items.item.application.config;

import com.coupon.favorites.items.item.application.usecase.GetItemsPriceUseCaseImpl;
import com.coupon.favorites.items.item.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.item.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.item.application.usecase.GetTopFavoritesUseCaseImpl;
import com.coupon.favorites.items.item.application.usecase.IncQuantityFavoritesUseCaseImpl;
import com.coupon.favorites.items.item.domain.service.GetTopFavoritesService;
import com.coupon.favorites.items.item.domain.service.IncQuantityFavoritesService;
import com.coupon.favorites.items.item.domain.usecase.GetTopFavoritesUseCase;
import com.coupon.favorites.items.item.domain.usecase.IncQuantityFavoritesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringItemConfig {
    @Bean
    public GetItemsPriceUseCase getItemPrice(GetItemsPriceService getItemsPriceService) {
        return new GetItemsPriceUseCaseImpl(getItemsPriceService);
    }

    @Bean
    public IncQuantityFavoritesUseCase incQuantityFavorites (IncQuantityFavoritesService incQuantityFavoritesService) {
        return new IncQuantityFavoritesUseCaseImpl(incQuantityFavoritesService);
    }

    @Bean
    public GetTopFavoritesUseCase getTopFavorites (GetTopFavoritesService getTopFavoritesService) {
        return new GetTopFavoritesUseCaseImpl(getTopFavoritesService);
    }
}
