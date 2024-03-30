package com.coupon.favorites.items.coupon.application.config;

import com.coupon.favorites.items.coupon.application.usecase.GetItemsPriceUseCaseImpl;
import com.coupon.favorites.items.coupon.application.usecase.MaximizeCouponUseCaseImpl;
import com.coupon.favorites.items.coupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.coupon.domain.service.MeliPublicApiService;
import com.coupon.favorites.items.coupon.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.coupon.domain.usecase.MaximizeCouponUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringConfig {
    @Bean
    public MaximizeCouponUseCase maximizedCoupon(MaximizeCouponService maximizeCouponService) {
        return new MaximizeCouponUseCaseImpl(maximizeCouponService);
    }

    @Bean
    public GetItemsPriceUseCase getItemPrice(MeliPublicApiService meliPublicApiService) {
        return new GetItemsPriceUseCaseImpl(meliPublicApiService);
    }
}
