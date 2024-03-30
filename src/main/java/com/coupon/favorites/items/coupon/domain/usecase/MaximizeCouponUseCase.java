package com.coupon.favorites.items.coupon.domain.usecase;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponResponse;
import io.vavr.control.Either;
public interface MaximizeCouponUseCase {
    Either<ErrorCoupon, MaximizeCouponResponse> execute(MaximizeCouponEntity couponRequest);
}
