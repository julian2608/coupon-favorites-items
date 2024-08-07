package com.coupon.favorites.items.coupon.infrastructure.controllers;

import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.usecase.MaximizeCouponUseCase;
import com.coupon.favorites.items.item.domain.usecase.GetTopFavoritesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final MaximizeCouponUseCase maximizeCouponUseCase;

    private final GetTopFavoritesUseCase getTopFavoritesUseCase;

    public CouponController(
            MaximizeCouponUseCase maximizeCouponUseCase,
            GetTopFavoritesUseCase getTopFavoritesUseCase
    ) {
        this.maximizeCouponUseCase = maximizeCouponUseCase;
        this.getTopFavoritesUseCase = getTopFavoritesUseCase;
    }

    @PostMapping()
    public ResponseEntity<Object> coupon(@RequestBody MaximizeCouponEntity maximizeCouponEntity) {
        return maximizeCouponUseCase.execute(maximizeCouponEntity).fold(
                error -> ResponseEntity.status(error.getCode()).body(error),
                ResponseEntity::ok
        );
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> topFavorites(
            @RequestParam(name = "maxTop", required = false, defaultValue = "5") int maxTop
    ) {
        return getTopFavoritesUseCase.execute(maxTop).fold(
                error -> ResponseEntity.status(error.getCode()).body(error),
                ResponseEntity::ok
        );
    }
}
