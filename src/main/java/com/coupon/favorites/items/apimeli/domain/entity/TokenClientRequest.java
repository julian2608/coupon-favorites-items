package com.coupon.favorites.items.apimeli.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenClientRequest {
    private String grantType;

    private String clientId;

    private String clientSecret;

    private String code;

    private String redirectUri;

    private String refreshToken;

    public TokenClientRequest(
            String grantType,
            String clientId,
            String clientSecret,
            String code,
            String redirectUri) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.redirectUri = redirectUri;
    }

    public void clearToRefreshToken() {
        this.refreshToken = TokenClientMem.getInstance().getRefreshToken();
        this.grantType = "refresh_token";
        this.code = null;
        this.redirectUri = null;
    }

}
