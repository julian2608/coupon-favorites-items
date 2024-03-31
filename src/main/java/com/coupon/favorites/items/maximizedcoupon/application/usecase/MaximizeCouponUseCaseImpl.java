package com.coupon.favorites.items.maximizedcoupon.application.usecase;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.maximizedcoupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.maximizedcoupon.domain.usecase.MaximizeCouponUseCase;
import io.vavr.control.Either;

public class MaximizeCouponUseCaseImpl implements MaximizeCouponUseCase {

    private final MaximizeCouponService maximizeCouponService;

    public MaximizeCouponUseCaseImpl(
            MaximizeCouponService maximizeCouponService
    ){
        this.maximizeCouponService = maximizeCouponService;
    }

    @Override
    public Either<ErrorCoupon, MaximizeCouponResponse> execute(MaximizeCouponEntity couponRequest) {
        try {
            return maximizeCouponService.maximizeCoupon(couponRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
