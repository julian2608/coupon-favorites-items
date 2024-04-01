package com.coupon.favorites.items.itemsprice.application.config;

import com.coupon.favorites.items.itemsprice.application.usecase.GetItemsPriceUseCaseImpl;
import com.coupon.favorites.items.itemsprice.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.itemsprice.domain.usecase.GetItemsPriceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringGetItemsPriceConfig {
    @Bean
    public GetItemsPriceUseCase getItemPrice(GetItemsPriceService getItemsPriceService) {
        return new GetItemsPriceUseCaseImpl(getItemsPriceService);
    }
}
