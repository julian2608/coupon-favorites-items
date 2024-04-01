package com.coupon.favorites.items.tokenclientoauth2.application.usecase;

import com.coupon.favorites.items.tokenclientoauth2.domain.service.TokenClientService;
import com.coupon.favorites.items.tokenclientoauth2.domain.usecase.GetTokenExternalUseCase;

public class GetTokenExternalUseCaseImpl implements GetTokenExternalUseCase {

    private final TokenClientService tokenClientService;

    public GetTokenExternalUseCaseImpl(TokenClientService tokenClientService) {
        this.tokenClientService = tokenClientService;
    }

    @Override
    public String getToken() {
        return tokenClientService.getToken();
    }
}
