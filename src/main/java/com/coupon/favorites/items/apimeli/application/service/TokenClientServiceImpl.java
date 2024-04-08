package com.coupon.favorites.items.apimeli.application.service;

import com.coupon.favorites.items.retrofit.application.config.RetrofitConfig;
import com.coupon.favorites.items.apimeli.domain.service.MeliPublicApiService;
import com.coupon.favorites.items.retrofit.application.ApiCallExecutor;
import com.coupon.favorites.items.retrofit.application.RetryUtil;
import com.coupon.favorites.items.apimeli.domain.entity.TokenClientMem;
import com.coupon.favorites.items.apimeli.domain.entity.TokenClientResponse;
import com.coupon.favorites.items.apimeli.domain.service.TokenClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.util.Objects;
import java.util.function.Supplier;

@Service
public class TokenClientServiceImpl implements TokenClientService {

    @Value("${oauth2.client.mercadolibre.authorization-grant-type}")
    private String grantType;

    @Value("${oauth2.client.mercadolibre.client-id}")
    private String clientId;

    @Value("${oauth2.client.mercadolibre.client-secret}")
    private String clientSecret;

    private final static String URI_API_TOKEN = "https://api.mercadolibre.com/";
    private final MeliPublicApiService meliPublicApiService;

    public TokenClientServiceImpl() {
        Retrofit retrofit = RetrofitConfig.getClient(URI_API_TOKEN);
        meliPublicApiService = retrofit.create(MeliPublicApiService.class);
    }

    @Override
    public String getToken() {
        synchronized (TokenClientServiceImpl.class) {
            if (Objects.isNull(TokenClientMem.getInstance())) {
                TokenClientResponse token = getTokenApiExternal();
                TokenClientMem.initialize(token);
            }
            if (!TokenClientMem.getInstance().isTokenValid()) {
                TokenClientResponse newToken = getTokenApiExternal();
                TokenClientMem.getInstance().updateToken(newToken);
            }
            return TokenClientMem.getInstance().getAccessToken();
        }
    }

    private TokenClientResponse getTokenApiExternal() {
        Supplier<TokenClientResponse> getToken = () -> ApiCallExecutor.execute(meliPublicApiService.getToken(grantType, clientId, clientSecret));

        return RetryUtil.retryApiCallFunction(getToken);
    }
}
