package com.coupon.favorites.items.shared.util;

import java.time.Duration;
import java.util.function.Supplier;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

import static java.time.temporal.ChronoUnit.SECONDS;

public class RetryUtil  {
    private static final int MAX_RETRIES_ATTEMPTS = 3;
    private static final int MAX_WAIT_RETRY = 2;

    public static <T> T retryApiCallFunction(Supplier<T> callingObject){
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(MAX_RETRIES_ATTEMPTS)
                .waitDuration(Duration.of(MAX_WAIT_RETRY, SECONDS))
                .retryOnException(
                        ex -> ex instanceof ApiCallException && ((ApiCallException) ex).getCode() >= 500 &&
                        ((ApiCallException) ex).getCode() < 600)
                .build();
        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("retryFunction");
        Supplier<T> callingObjectResponseSupplier = Retry.decorateSupplier(retry,callingObject);
        return callingObjectResponseSupplier.get();
    }

}