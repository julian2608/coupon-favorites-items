package com.coupon.favorites.items.coupon.infrastructure.controllers;


import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.coupon.domain.usecase.MaximizeCouponUseCase;
import com.coupon.favorites.items.item.domain.entity.ErrorItem;
import com.coupon.favorites.items.item.domain.entity.ItemFavorite;
import com.coupon.favorites.items.item.domain.usecase.GetTopFavoritesUseCase;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CouponControllerTest {
    @InjectMocks
    private CouponController couponController;

    @Mock
    private MaximizeCouponUseCase maximizeCouponUseCase;

    @Mock
    private GetTopFavoritesUseCase getTopFavoritesUseCase;

    private MaximizeCouponResponse maximizeCouponResponse;

    private List<ItemFavorite> topFavoritesResponse;

    @BeforeEach
    public void init() {
        maximizeCouponResponse = MaximizeCouponResponse
                .builder()
                .itemIds(List.of("MCO32175689","MCO543789","MCO432765","MCO4354765","MCO4326525"))
                .total(400.0)
                .build();

        topFavoritesResponse = List.of(
                new ItemFavorite("MCO32175689", 45),
                new ItemFavorite("MCO543789", 30),
                new ItemFavorite("MCO432765", 18)
        );

        openMocks(this);
    }

    @Test
    void savePricesItem_When_Ok() {

        when(maximizeCouponUseCase.execute(any(MaximizeCouponEntity.class))).thenReturn(Either.right(maximizeCouponResponse));

        ResponseEntity<Object> response = couponController.coupon(new MaximizeCouponEntity());

        MaximizeCouponResponse result = (MaximizeCouponResponse)response.getBody();

        verify(maximizeCouponUseCase, times(1)).execute(any(MaximizeCouponEntity.class));
        assertEquals(maximizeCouponResponse, result);

    }
    @Test
    void savePricesItem_When_Error() {
        when(maximizeCouponUseCase.execute(any(MaximizeCouponEntity.class))).thenReturn(Either.left(ErrorCoupon.ErrorValueCouponDecimals));

        ResponseEntity<Object> response = couponController.coupon(new MaximizeCouponEntity());

        ErrorCoupon result = (ErrorCoupon)response.getBody();

        verify(maximizeCouponUseCase, times(1)).execute(any(MaximizeCouponEntity.class));
        assertEquals(ErrorCoupon.ErrorValueCouponDecimals, result);
    }

    @Test
    void getTopFavorites_When_Ok() {

        when(getTopFavoritesUseCase.execute(anyInt())).thenReturn(Either.right(topFavoritesResponse));

        ResponseEntity<Object> response = couponController.topFavorites(5);

        List<ItemFavorite> result = (List<ItemFavorite>)response.getBody();

        verify(getTopFavoritesUseCase, times(1)).execute(anyInt());
        assertEquals(topFavoritesResponse, result);
    }

    @Test
    void getTopFavorites_When_Error() {

        when(getTopFavoritesUseCase.execute(anyInt())).thenReturn(Either.left(ErrorItem.ErrorGettingFavorites));

        ResponseEntity<Object> response = couponController.topFavorites(5);

        ErrorItem result = (ErrorItem)response.getBody();

        verify(getTopFavoritesUseCase, times(1)).execute(anyInt());
        assertEquals(ErrorItem.ErrorGettingFavorites, result);
    }



}