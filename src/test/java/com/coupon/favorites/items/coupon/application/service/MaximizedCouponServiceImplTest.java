package com.coupon.favorites.items.coupon.application.service;

import com.coupon.favorites.items.cache.domain.usecase.GetPricesInCacheUseCase;
import com.coupon.favorites.items.cache.domain.usecase.SavePricesInCacheUseCase;
import com.coupon.favorites.items.item.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.coupon.domain.event.IncCountFavoritesValueEvent;
import com.coupon.favorites.items.coupon.domain.event.SaveCachePricesValueEvent;
import com.coupon.favorites.items.coupon.domain.valueobject.Coupon;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import com.coupon.favorites.items.coupon.domain.valueobject.ItemsId;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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


    @BeforeEach
    public void init (){
        itemIds = new HashSet<>(new ArrayList<>(List.of("MCO32175689","MCO543789","MCO432765","MCO4354765","MCO4326525")));


        itemsPrice = List.of(
                new Item("MCO32175689", 240.0),
                new Item("MCO543789", 105.0),
                new Item("MCO432765", 75.0),
                new Item("MCO4354765", 40.0),
                new Item("MCO4326525", 20.0));

        maximizeCouponEntity = MaximizeCouponEntity
                .builder()
                .coupon(Coupon.builder().value(400).build())
                .favoritesItems(new ItemsId(itemIds))
                .build();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void maximized_Coupon_When_Not_Cached_Prices() {
        when(getPricesInCacheUseCase.execute(itemIds.stream().toList())).thenReturn(
                new ArrayList<>());

        when(getItemsPriceUseCase.execute(itemIds)).thenReturn(
                Either.right(itemsPrice));

        doNothing().when(publisher)
                .publishEvent(any(SaveCachePricesValueEvent.class));

        doNothing().when(publisher)
                .publishEvent(any(IncCountFavoritesValueEvent.class));

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        MaximizeCouponResponse response = (MaximizeCouponResponse) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        verify(publisher, times(1)).publishEvent(any(SaveCachePricesValueEvent.class));
        verify(publisher, times(1)).publishEvent(any(IncCountFavoritesValueEvent.class));
        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(getItemsPriceUseCase, times(1)).execute(any());

        assertTrue(maximizeCouponEntity.getCoupon().getValue() >= response.getTotal());

    }

    @Test
    public void maximized_Coupon_When_Cached_All_Prices() {
        when(getPricesInCacheUseCase.execute(any())).thenReturn(
                itemsPrice);

        doNothing().when(publisher)
                .publishEvent(any(SaveCachePricesValueEvent.class));

        doNothing().when(publisher)
                .publishEvent(any(IncCountFavoritesValueEvent.class));

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        MaximizeCouponResponse response = (MaximizeCouponResponse) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        verify(publisher, times(0)).publishEvent(any(SaveCachePricesValueEvent.class));
        verify(publisher, times(1)).publishEvent(any(IncCountFavoritesValueEvent.class));
        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(savePricesInCacheUseCase, times(0)).execute(any());
        verify(getItemsPriceUseCase, times(0)).execute(any());

        assertTrue(maximizeCouponEntity.getCoupon().getValue() >= response.getTotal());

    }

    @Test
    public void maximized_Coupon_When_Cached_Partial_Prices() {
        ArgumentCaptor<Set<String>> captorItemsId = ArgumentCaptor.forClass(Set.class);

        when(getPricesInCacheUseCase.execute(itemIds.stream().toList())).thenReturn(
                itemsPrice.subList(0, 2));

        when(getItemsPriceUseCase.execute(captorItemsId.capture()))
                .thenReturn(Either.right(itemsPrice));

        doNothing().when(publisher)
                .publishEvent(any(SaveCachePricesValueEvent.class));

        doNothing().when(publisher)
                .publishEvent(any(IncCountFavoritesValueEvent.class));

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        MaximizeCouponResponse response = (MaximizeCouponResponse) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        Set<String> capturedArgument = captorItemsId.getValue();

        verify(publisher, times(1)).publishEvent(any(SaveCachePricesValueEvent.class));
        verify(publisher, times(1)).publishEvent(any(IncCountFavoritesValueEvent.class));
        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(getItemsPriceUseCase, times(1)).execute(any());

        assertTrue(maximizeCouponEntity.getCoupon().getValue() >= response.getTotal());
        assertEquals(3, capturedArgument.size());

    }

    @Test
    public void maximized_Coupon_When_Result_Is_Exact() {
        itemsPrice = List.of(
                new Item("MCO32175689", 50.0),
                new Item("MCO543789", 200.0),
                new Item("MCO4354765", 100.0),
                new Item("MCO4326525", 50.0),
                new Item("MCO432765", 130.0));

        when(getPricesInCacheUseCase.execute(itemIds.stream().toList())).thenReturn(
                new ArrayList<>());

        when(getItemsPriceUseCase.execute(itemIds)).thenReturn(
                Either.right(itemsPrice));

        doNothing().when(publisher)
                .publishEvent(any(SaveCachePricesValueEvent.class));

        doNothing().when(publisher)
                .publishEvent(any(IncCountFavoritesValueEvent.class));

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        MaximizeCouponResponse response = (MaximizeCouponResponse) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        verify(publisher, times(1)).publishEvent(any(SaveCachePricesValueEvent.class));
        verify(publisher, times(1)).publishEvent(any(IncCountFavoritesValueEvent.class));
        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(getItemsPriceUseCase, times(1)).execute(any());

        assertTrue(maximizeCouponEntity.getCoupon().getValue() >= response.getTotal());
        assertEquals(maximizeCouponEntity.getCoupon().getValue(), response.getTotal());

    }


    @Test
    public void maximized_Coupon_When_Result_Is_Not_Exact() {
        itemsPrice = List.of(
                new Item("MCO32175689", 50.0),
                new Item("MCO543789", 200.0),
                new Item("MCO432765", 130.0),
                new Item("MCO4354765", 100.0),
                new Item("MCO4326525", 50.0)
        );

        when(getPricesInCacheUseCase.execute(itemIds.stream().toList())).thenReturn(
                new ArrayList<>());

        when(getItemsPriceUseCase.execute(itemIds)).thenReturn(
                Either.right(itemsPrice));

        doNothing().when(publisher)
                .publishEvent(any(SaveCachePricesValueEvent.class));

        doNothing().when(publisher)
                .publishEvent(any(IncCountFavoritesValueEvent.class));

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        MaximizeCouponResponse response = (MaximizeCouponResponse) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        verify(publisher, times(1)).publishEvent(any(SaveCachePricesValueEvent.class));
        verify(publisher, times(1)).publishEvent(any(IncCountFavoritesValueEvent.class));
        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(getItemsPriceUseCase, times(1)).execute(any());

        assertTrue(maximizeCouponEntity.getCoupon().getValue() >= response.getTotal());
        assertNotEquals(maximizeCouponEntity.getCoupon().getValue(), response.getTotal());

    }

    @Test
    public void maximized_Coupon_When_Items_Price_Use_case_Return_Error() {
        when(getPricesInCacheUseCase.execute(itemIds.stream().toList())).thenReturn(
                new ArrayList<>());

        when(getItemsPriceUseCase.execute(itemIds)).thenReturn(
                Either.left(ErrorCoupon.ErrorGettingPrices));

        doNothing().when(publisher)
                .publishEvent(any(IncCountFavoritesValueEvent.class));

        doNothing().when(savePricesInCacheUseCase)
                .execute(itemsPrice);


        ErrorCoupon response = (ErrorCoupon) maximizedCouponService.maximizeCoupon(maximizeCouponEntity).fold(
                error -> error,
                result -> result
        );

        assertTrue(response.getMessage().contains(ErrorCoupon.ErrorGettingPrices.getMessage()));
        verify(getItemsPriceUseCase, times(1)).execute(any());
        verify(publisher, times(1)).publishEvent(any(IncCountFavoritesValueEvent.class));
        verify(getPricesInCacheUseCase, times(1)).execute(any());
        verify(publisher, times(0)).publishEvent(any(SaveCachePricesValueEvent.class));

    }
}