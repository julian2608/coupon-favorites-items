package com.coupon.favorites.items.tokenclientoauth2.application.service;

import com.coupon.favorites.items.shared.config.RetrofitConfig;
import com.coupon.favorites.items.shared.service.MeliPublicApiService;
import com.coupon.favorites.items.shared.util.ApiCallExecutor;
import com.coupon.favorites.items.shared.util.RetryUtil;
import com.coupon.favorites.items.tokenclientoauth2.domain.entity.TokenClientMem;
import com.coupon.favorites.items.tokenclientoauth2.domain.entity.TokenClientRequest;
import com.coupon.favorites.items.tokenclientoauth2.domain.entity.TokenClientResponse;
import com.coupon.favorites.items.tokenclientoauth2.domain.service.TokenClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.util.function.Supplier;

@Service
public class TokenClientServiceImpl implements TokenClientService {

    @Value("${oauth2.client.mercadolibre.authorization-grant-type}")
    private String grantType;

    @Value("${oauth2.client.mercadolibre.client-id}")
    private String clientId;

    @Value("${oauth2.client.mercadolibre.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.mercadolibre.code-authorization}")
    private String code;

    @Value("${oauth2.client.mercadolibre.redirect-uri}")
    private String redirectUri;

    @Value("${app.meli.public.api}")
    private String urlBaseToken;
    private final MeliPublicApiService meliPublicApiService;

    public TokenClientServiceImpl() {
        Retrofit retrofit = RetrofitConfig.getClient(urlBaseToken);
        meliPublicApiService = retrofit.create(MeliPublicApiService.class);
    }

    @Override
    public String getToken() {
        synchronized (TokenClientServiceImpl.class) {
            if (TokenClientMem.getInstance() == null) {
                TokenClientResponse token = getTokenApiExternal();
                TokenClientMem.initialize(token);
            }
            if (!TokenClientMem.getInstance().isTokenValid()) {
                TokenClientResponse newToken = refreshTokenApiExternal();
                TokenClientMem.getInstance().updateToken(newToken);
            }
            return TokenClientMem.getInstance().getAccessToken();
        }
    }

    private TokenClientResponse getTokenApiExternal() {
        Supplier<TokenClientResponse> getToken = () -> ApiCallExecutor.execute(meliPublicApiService.getToken(grantType, clientId, clientSecret, code, redirectUri));

        return RetryUtil.retryApiCallFunction(getToken);
    }

    private TokenClientResponse refreshTokenApiExternal() {
        TokenClientRequest tokenClientRequest = new TokenClientRequest(grantType, clientId, clientSecret, code, redirectUri);
        tokenClientRequest.clearToRefreshToken();

        Supplier<TokenClientResponse> getToken = () -> ApiCallExecutor.execute(meliPublicApiService.refreshToken("refresh_token", clientId, clientSecret, TokenClientMem.getInstance().getRefreshToken()));

        return RetryUtil.retryApiCallFunction(getToken);
    }
}
