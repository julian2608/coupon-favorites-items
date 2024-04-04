package com.coupon.favorites.items.maximizedcoupon.domain.service;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.vavr.control.Either;

public interface MaximizeCouponService {
    Either<ErrorCoupon, MaximizeCouponResponse> maximizeCoupon(MaximizeCouponEntity couponRequest) throws JsonProcessingException;
}
