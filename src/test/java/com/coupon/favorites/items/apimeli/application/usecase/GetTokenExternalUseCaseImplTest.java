package com.coupon.favorites.items.apimeli.application.usecase;

import com.coupon.favorites.items.apimeli.domain.service.TokenClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class GetTokenExternalUseCaseImplTest {
    @InjectMocks
    private GetTokenExternalUseCaseImpl getTokenExternalUseCase;

    @Mock
    private TokenClientService tokenClientService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getToken() {
        String token = "token";
        when(tokenClientService.getToken()).thenReturn(token);

        String result = getTokenExternalUseCase.getToken();

        assertEquals(token, result);
        verify(tokenClientService, times(1)).getToken();
    }


}