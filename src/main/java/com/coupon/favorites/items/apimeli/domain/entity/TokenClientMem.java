package com.coupon.favorites.items.apimeli.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Objects;

@Builder
@Data
public class TokenClientMem {
    private static TokenClientMem instance;

    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String scope;
    private int userId;
    private String refreshToken;
    private Instant expirationTime;

    public static synchronized TokenClientMem getInstance() {
        return instance;
    }

    public static synchronized void initialize(TokenClientResponse tokenResponse) {
        instance = TokenClientMem
                .builder()
                .accessToken(tokenResponse.getTokenType()+" "+tokenResponse.getAccessToken())
                .tokenType(tokenResponse.getTokenType())
                .expiresIn(tokenResponse.getExpiresIn())
                .scope(tokenResponse.getScope())
                .userId(tokenResponse.getUserId())
                .refreshToken(tokenResponse.getRefreshToken())
                .expirationTime(Instant.now().plusSeconds(tokenResponse.getExpiresIn()))
                .build();
    }

    public synchronized void updateToken(TokenClientResponse newTokenResponse) {
        this.accessToken = newTokenResponse.getTokenType()+" "+newTokenResponse.getAccessToken();
        this.tokenType = newTokenResponse.getTokenType();
        this.expiresIn = newTokenResponse.getExpiresIn();
        this.scope = newTokenResponse.getScope();
        this.userId = newTokenResponse.getUserId();
        this.refreshToken = newTokenResponse.getRefreshToken();
        this.expirationTime = Instant.now().plusSeconds(newTokenResponse.getExpiresIn());

    }

    public synchronized boolean isTokenValid() {
        return Objects.nonNull(expirationTime) && expirationTime.isAfter(Instant.now());
    }
}
