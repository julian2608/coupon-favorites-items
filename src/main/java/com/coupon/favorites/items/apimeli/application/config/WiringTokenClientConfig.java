package com.coupon.favorites.items.apimeli.application.config;

import com.coupon.favorites.items.apimeli.application.usecase.GetTokenExternalUseCaseImpl;
import com.coupon.favorites.items.apimeli.domain.service.TokenClientService;
import com.coupon.favorites.items.apimeli.domain.usecase.GetTokenExternalUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringTokenClientConfig {
    @Bean
    public GetTokenExternalUseCase getToken(TokenClientService tokenClientService) {
        return new GetTokenExternalUseCaseImpl(tokenClientService);
    }
}
