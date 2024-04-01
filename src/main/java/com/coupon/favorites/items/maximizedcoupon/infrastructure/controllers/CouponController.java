package com.coupon.favorites.items.maximizedcoupon.infrastructure.controllers;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.maximizedcoupon.domain.usecase.MaximizeCouponUseCase;
import com.coupon.favorites.items.tokenclientoauth2.domain.usecase.GetTokenExternalUseCase;
import com.coupon.favorites.items.topfavorites.domain.usecase.GetTopFavoritesUseCase;
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

    private final GetTokenExternalUseCase getTokenExternalUseCase;

    public CouponController(
            MaximizeCouponUseCase maximizeCouponUseCase,
            GetTopFavoritesUseCase getTopFavoritesUseCase,
            GetTokenExternalUseCase getTokenExternalUseCase
    ) {
        this.maximizeCouponUseCase = maximizeCouponUseCase;
        this.getTopFavoritesUseCase = getTopFavoritesUseCase;
        this.getTokenExternalUseCase = getTokenExternalUseCase;
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

    @GetMapping("/token")
    private ResponseEntity<Object> getToken(
            ) {
        return ResponseEntity.ok().body(getTokenExternalUseCase.getToken());
    }
}
