package com.coupon.favorites.items.coupon.application.usecase;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.coupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.usecase.MaximizeCouponUseCase;
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
            return maximizeCouponService.maximizeCoupon(couponRequest);
    }
}
