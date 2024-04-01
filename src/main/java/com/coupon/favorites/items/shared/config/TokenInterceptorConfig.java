package com.coupon.favorites.items.shared.config;

import com.coupon.favorites.items.itemsprice.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.tokenclientoauth2.domain.service.TokenClientService;
import com.coupon.favorites.items.tokenclientoauth2.domain.usecase.GetTokenExternalUseCase;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
public class TokenInterceptorConfig implements Interceptor {

    @Value("${app.retrofit.authorization-header-name}")
    private String authorizationHeaderName;
    private final TokenClientService getTokenExternalUseCase;

    public TokenInterceptorConfig(
            TokenClientService getTokenExternalUseCase
    ) {
        this.getTokenExternalUseCase = getTokenExternalUseCase;

    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request modifiedRequest = originalRequest.newBuilder()
                .addHeader(authorizationHeaderName,String.format("Bearer %s", getTokenExternalUseCase.getToken()))
                .build();

        return chain.proceed(modifiedRequest);
    }
}
