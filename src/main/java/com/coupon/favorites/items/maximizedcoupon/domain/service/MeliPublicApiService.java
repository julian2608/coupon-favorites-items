package com.coupon.favorites.items.maximizedcoupon.domain.service;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ItemPriceResponse;
import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.List;

public interface MeliPublicApiService {

    @GET("items")
    @Headers({"Content-Type: application/json"})
    Call<List<ItemPriceResponse>> getItemsPrice(
            @Query("ids") String ids,
            @Query("attributes") String attributes
    );

    @GET("https://api.mercadolibre.com/sites/MCO/search?category=MCO1747")
    @Headers({"Content-Type: application/json"})
    Call<JsonNode> getIds(
            @Query("offset") int offset,
            @Query("category") String category
    );



}
