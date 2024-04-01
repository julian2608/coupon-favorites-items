package com.coupon.favorites.items.tokenclientoauth2.application.config;

import com.coupon.favorites.items.tokenclientoauth2.application.usecase.GetTokenExternalUseCaseImpl;
import com.coupon.favorites.items.tokenclientoauth2.domain.service.TokenClientService;
import com.coupon.favorites.items.tokenclientoauth2.domain.usecase.GetTokenExternalUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringTokenClientConfig {
    @Bean
    public GetTokenExternalUseCase getToken(TokenClientService tokenClientService) {
        return new GetTokenExternalUseCaseImpl(tokenClientService);
    }
}
