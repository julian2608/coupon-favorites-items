package com.coupon.favorites.items.maximizedcoupon.application.config;

import com.coupon.favorites.items.maximizedcoupon.application.usecase.GetItemsPriceUseCaseImpl;
import com.coupon.favorites.items.maximizedcoupon.application.usecase.MaximizeCouponUseCaseImpl;
import com.coupon.favorites.items.maximizedcoupon.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.maximizedcoupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.maximizedcoupon.domain.service.MeliPublicApiService;
import com.coupon.favorites.items.maximizedcoupon.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.maximizedcoupon.domain.usecase.MaximizeCouponUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringMaximizedCouponConfig {
    @Bean
    public MaximizeCouponUseCase maximizedCoupon(MaximizeCouponService maximizeCouponService) {
        return new MaximizeCouponUseCaseImpl(maximizeCouponService);
    }

    @Bean
    public GetItemsPriceUseCase getItemPrice(GetItemsPriceService getItemsPriceService) {
        return new GetItemsPriceUseCaseImpl(getItemsPriceService);
    }
}
