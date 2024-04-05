package com.coupon.favorites.items.maximizedcoupon.application.service;

import com.coupon.favorites.items.cache.domain.usecase.GetPricesInCacheUseCase;
import com.coupon.favorites.items.cache.domain.usecase.SavePricesInCacheUseCase;
import com.coupon.favorites.items.itemsprice.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Coupon;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

class MaximizedCouponServiceImplTest {

    @InjectMocks
    private MaximizedCouponServiceImpl maximizedCouponService;
    @Mock
    private GetItemsPriceUseCase getItemsPriceUseCase;
    @Mock
    private SavePricesInCacheUseCase savePricesInCacheUseCase;
    @Mock
    private GetPricesInCacheUseCase getPricesInCacheUseCase;
    @Mock
    private ApplicationEventPublisher publisher;
    private MaximizeCouponEntity maximizeCouponEntity;

    private Set<String> itemIds;

    private List<Item> itemsPrice;

    private ItemsId itemsIdValueObject;

    @BeforeEach
    public void init (){
        itemIds = new HashSet<>(new ArrayList<>(List.of("MCO32175689","MCO543789","MCO432765","MCO4354765","MCO4326525")));

        itemsIdValueObject = ItemsId.builder().value(itemIds).build();

        itemsPrice = List.of(
                new Item("MCO32175689", 240.0),
                new Item("MCO543789", 105.0),
                new Item("MCO432765", 75.0),
                new Item("MCO4354765", 40.0),
                new Item("MCO4326525", 20.0));

        maximizeCouponEntity = MaximizeCouponEntity
                .builder()
                .coupon(Coupon.builder().value(400).build())
                .favoritesItems(itemsIdValueObject)
                .build();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void maximized_Coupon_When_Not_Cached_Prices() {
        when(getPricesInCacheUseCase.execute(itemIds.stream().toList())).thenReturn(
                new ArrayList<>());

        when(getItemsPriceUseCase.execute(itemsIdValueObject)).thenReturn(
                Either.right(itemsPrice));

        doNothing().when(publisher)
                .publishEvent(itemsIdValueObject);

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        MaximizeCouponResponse response = (MaximizeCouponResponse) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        verify(savePricesInCacheUseCase, times(1)).execute(any());
        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(getItemsPriceUseCase, times(1)).execute(any());

        Assertions.assertTrue(maximizeCouponEntity.getCoupon().getValue() >= response.getTotal());

    }

    @Test
    public void maximized_Coupon_When_Cached_All_Prices() {
        when(getPricesInCacheUseCase.execute(any())).thenReturn(
                itemsPrice);

        doNothing().when(publisher)
                .publishEvent(itemsIdValueObject);

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        MaximizeCouponResponse response = (MaximizeCouponResponse) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(savePricesInCacheUseCase, times(0)).execute(any());
        verify(getItemsPriceUseCase, times(0)).execute(any());

        Assertions.assertTrue(maximizeCouponEntity.getCoupon().getValue() >= response.getTotal());

    }

    @Test
    public void maximized_Coupon_When_Cached_Partial_Prices() {
        ArgumentCaptor<ItemsId> captorItemsId = ArgumentCaptor.forClass(ItemsId.class);

        when(getPricesInCacheUseCase.execute(itemIds.stream().toList())).thenReturn(
                itemsPrice.subList(0, 2));

        when(getItemsPriceUseCase.execute(captorItemsId.capture()))
                .thenReturn(Either.right(itemsPrice));

        doNothing().when(publisher)
                .publishEvent(any());

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        MaximizeCouponResponse response = (MaximizeCouponResponse) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        ItemsId capturedArgument = captorItemsId.getValue();

        verify(savePricesInCacheUseCase, times(1)).execute(any());
        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(getItemsPriceUseCase, times(1)).execute(any());

        Assertions.assertTrue(maximizeCouponEntity.getCoupon().getValue() >= response.getTotal());
        Assertions.assertEquals(3, capturedArgument.getValue().size());

    }
}