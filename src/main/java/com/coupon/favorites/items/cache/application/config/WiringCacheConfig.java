package com.coupon.favorites.items.cache.application.config;

import com.coupon.favorites.items.cache.application.usecase.GetPricesInCacheUseCaseImpl;
import com.coupon.favorites.items.cache.application.usecase.SavePricesInCacheUseCaseImpl;
import com.coupon.favorites.items.cache.domain.service.GetPricesInCacheService;
import com.coupon.favorites.items.cache.domain.service.SavePricesInCacheService;
import com.coupon.favorites.items.cache.domain.usecase.GetPricesInCacheUseCase;
import com.coupon.favorites.items.cache.domain.usecase.SavePricesInCacheUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringCacheConfig {
    @Bean
    public GetPricesInCacheUseCase getPricesInCache (GetPricesInCacheService getPricesInCacheService) {
        return new GetPricesInCacheUseCaseImpl(getPricesInCacheService);
    }

    @Bean
    public SavePricesInCacheUseCase savePricesInCache (SavePricesInCacheService savePricesInCacheService) {
        return new SavePricesInCacheUseCaseImpl(savePricesInCacheService);
    }
}
