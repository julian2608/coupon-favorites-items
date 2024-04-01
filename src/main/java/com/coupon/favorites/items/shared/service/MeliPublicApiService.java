package com.coupon.favorites.items.shared.service;

import com.coupon.favorites.items.itemsprice.domain.entity.ItemPriceResponse;
import com.coupon.favorites.items.tokenclientoauth2.domain.entity.TokenClientRequest;
import com.coupon.favorites.items.tokenclientoauth2.domain.entity.TokenClientResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cache.annotation.Cacheable;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MeliPublicApiService {

    @GET("items")
    @Headers({"Content-Type: application/json"})
    Call<List<ItemPriceResponse>> getItemsPrice(
            @Query("ids") String ids,
            @Query("attributes") String attributes
    );

    @POST("oauth/token")
    @Headers({"Content-Type: application/json"})
    @FormUrlEncoded
    Call<TokenClientResponse> getToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUri
    );

    @POST("oauth/token")
    @Headers({"Content-Type: application/json"})
    @FormUrlEncoded
    Call<TokenClientResponse> refreshToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("refresh_token") String refreshToken
    );

    @GET("https://api.mercadolibre.com/sites/MCO/search?category=MCO1747")
    @Headers({"Content-Type: application/json"})
    Call<JsonNode> getIds(
            @Query("offset") int offset,
            @Query("category") String category
    );



}
