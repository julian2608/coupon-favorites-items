package com.coupon.favorites.items.coupon.infrastructure.controllers;

import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.usecase.MaximizeCouponUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {

    private final MaximizeCouponUseCase maximizeCouponUseCase;


    public CouponController(MaximizeCouponUseCase maximizeCouponUseCase) {
        this.maximizeCouponUseCase = maximizeCouponUseCase;
    }

    @PostMapping("/coupon")
    private ResponseEntity<Object> coupon(@RequestBody MaximizeCouponEntity maximizeCouponEntity) {
        return maximizeCouponUseCase.execute(maximizeCouponEntity).fold(
                error -> ResponseEntity.status(error.getCode()).body(error),
                ResponseEntity::ok
        );
    }
}
