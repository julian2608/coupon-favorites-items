package com.coupon.favorites.items.coupon.application.usecase;

import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.service.MaximizeCouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class MaximizeCouponUseCaseImplTest {

    @InjectMocks
    private MaximizeCouponUseCaseImpl maximizeCouponUseCase;

    @Mock
    private MaximizeCouponService maximizeCouponService;

    private MaximizeCouponEntity couponRequest;
    @BeforeEach
    public void init() {
        couponRequest = new MaximizeCouponEntity();
        openMocks(this);
    }

    @Test
    void execute_When_Ok() {
        maximizeCouponUseCase.execute(couponRequest);
        verify(maximizeCouponService, times(1)).maximizeCoupon(couponRequest);
    }
}