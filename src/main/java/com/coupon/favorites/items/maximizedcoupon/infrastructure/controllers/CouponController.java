package com.coupon.favorites.items.maximizedcoupon.infrastructure.controllers;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.maximizedcoupon.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.maximizedcoupon.domain.usecase.MaximizeCouponUseCase;
import com.coupon.favorites.items.topfavorites.domain.usecase.GetTopFavoritesUseCase;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final MaximizeCouponUseCase maximizeCouponUseCase;

    private final GetItemsPriceUseCase getItemsPriceUseCase;

    private final GetTopFavoritesUseCase getTopFavoritesUseCase;

    public CouponController(
            MaximizeCouponUseCase maximizeCouponUseCase,
            GetItemsPriceUseCase getItemsPriceUseCase,
            GetTopFavoritesUseCase getTopFavoritesUseCase
    ) {
        this.maximizeCouponUseCase = maximizeCouponUseCase;
        this.getItemsPriceUseCase = getItemsPriceUseCase;
        this.getTopFavoritesUseCase = getTopFavoritesUseCase;
    }

    @PostMapping()
    private ResponseEntity<Object> coupon(@RequestBody MaximizeCouponEntity maximizeCouponEntity) {
        return maximizeCouponUseCase.execute(maximizeCouponEntity).fold(
                error -> ResponseEntity.status(error.getCode()).body(error),
                ResponseEntity::ok
        );
    }

    @GetMapping("/stats")
    private ResponseEntity<Object> topFavorites(
            @RequestParam(name = "maxTop", required = false, defaultValue = "5") int maxTop
    ) {
        return getTopFavoritesUseCase.execute(maxTop).fold(
                error -> ResponseEntity.status(error.getCode()).body(error),
                ResponseEntity::ok
        );
    }
}
