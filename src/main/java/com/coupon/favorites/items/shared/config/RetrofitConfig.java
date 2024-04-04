package com.coupon.favorites.items.shared.config;


import com.coupon.favorites.items.apimeli.domain.service.MeliPublicApiService;
import com.coupon.favorites.items.apimeli.domain.usecase.GetTokenExternalUseCase;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.util.Objects;

@Configuration
public class RetrofitConfig {

    @Value("${app.retrofit.connection-timeout:10000}")
    private long connectionTimeout;
    @Value("${app.retrofit.read-timeout:15000}")
    private long readTimeout;
    @Value("${app.retrofit.write-timeout:30000}")
    private long writeTimeout;

    @Value("${app.meli.public.api}")
    private String urlApiMeli;
    private static Retrofit retrofit;

    @Value("${app.retrofit.authorization-header-name}")
    private String authorizationHeaderName;

    private final GetTokenExternalUseCase getTokenExternalUseCase;

    public RetrofitConfig(GetTokenExternalUseCase getTokenExternalUseCase){
        this.getTokenExternalUseCase = getTokenExternalUseCase;
    }


    @Bean
    public MeliPublicApiService meliPublicApiService() {
        Retrofit retrofit = getGenericRetrofitBuilderForUri(urlApiMeli);
        return retrofit.create(MeliPublicApiService.class);
    }

    private Retrofit getGenericRetrofitBuilderForUri(final String uri) {
        return new Retrofit.Builder()
                .baseUrl(uri)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMillis(connectionTimeout))
                .readTimeout(Duration.ofMillis(readTimeout))
                .writeTimeout(Duration.ofMillis(writeTimeout))
                .addInterceptor(getXAppInterceptor())
                .build();
    }

    private Interceptor getXAppInterceptor() {
        return chain -> {
            String token = getTokenExternalUseCase.getToken();
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(authorizationHeaderName, token)
                    .build();
            return chain.proceed(request);
        };
    }

    public static Retrofit getClient(final String uri) {
        if (Objects.isNull(retrofit)) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            retrofit = new Retrofit.Builder()
                    .baseUrl(uri)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
