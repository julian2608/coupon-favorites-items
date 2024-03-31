package com.coupon.favorites.items.maximizedcoupon.domain.usecase;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponResponse;
import io.vavr.control.Either;
public interface MaximizeCouponUseCase {
    Either<ErrorCoupon, MaximizeCouponResponse> execute(MaximizeCouponEntity couponRequest);
}
