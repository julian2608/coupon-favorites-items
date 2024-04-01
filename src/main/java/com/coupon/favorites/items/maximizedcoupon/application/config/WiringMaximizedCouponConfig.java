package com.coupon.favorites.items.maximizedcoupon.application.config;

import com.coupon.favorites.items.maximizedcoupon.application.usecase.MaximizeCouponUseCaseImpl;
import com.coupon.favorites.items.maximizedcoupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.maximizedcoupon.domain.usecase.MaximizeCouponUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringMaximizedCouponConfig {
    @Bean
    public MaximizeCouponUseCase maximizedCoupon(MaximizeCouponService maximizeCouponService) {
        return new MaximizeCouponUseCaseImpl(maximizeCouponService);
    }
}
