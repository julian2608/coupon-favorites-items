package com.coupon.favorites.items.topfavorites.application.config;

import com.coupon.favorites.items.topfavorites.application.usecase.GetTopFavoritesUseCaseImpl;
import com.coupon.favorites.items.topfavorites.application.usecase.IncQuantityFavoritesUseCaseImpl;
import com.coupon.favorites.items.topfavorites.domain.service.GetTopFavoritesService;
import com.coupon.favorites.items.topfavorites.domain.service.IncQuantityFavoritesService;
import com.coupon.favorites.items.topfavorites.domain.usecase.GetTopFavoritesUseCase;
import com.coupon.favorites.items.topfavorites.domain.usecase.IncQuantityFavoritesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringIncQuantityFavoriteConfig {
    @Bean
    public IncQuantityFavoritesUseCase incQuantityFavorites (IncQuantityFavoritesService incQuantityFavoritesService) {
        return new IncQuantityFavoritesUseCaseImpl(incQuantityFavoritesService);
    }

    @Bean
    public GetTopFavoritesUseCase getTopFavorites (GetTopFavoritesService getTopFavoritesService) {
        return new GetTopFavoritesUseCaseImpl(getTopFavoritesService);
    }
}
