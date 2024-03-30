package com.coupon.favorites.items.coupon.domain.service;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponResponse;
import io.vavr.control.Either;

public interface MaximizeCouponService {
    Either<ErrorCoupon, MaximizeCouponResponse> maximizeCoupon(MaximizeCouponEntity couponRequest) throws Exception;
}
