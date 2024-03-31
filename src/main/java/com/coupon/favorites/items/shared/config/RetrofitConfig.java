package com.coupon.favorites.items.shared.config;


import com.coupon.favorites.items.maximizedcoupon.domain.service.MeliPublicApiService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;

@Configuration
public class RetrofitConfig {

    @Value("${app.retrofit.connection-timeout:10000}")
    private long connectionTimeout;
    @Value("${app.retrofit.read-timeout:15000}")
    private long readTimeout;
    @Value("${app.retrofit.write-timeout:30000}")
    private long writeTimeout;

    private String urlApiMeli;

    private final String authorizationHeaderName;

    private final String token;

   /* private OAuth2AuthorizedClientService authorizedClientService;

    private OAuth2AuthorizedClient client;*/

    public RetrofitConfig(
            @Value("${app.retrofit.authorization-header-name}") String authorizationHeaderName,
            @Value("${app.meli.public.api}") final String urlApiMeli,
            @Value("${app.meli.public.api.token}") final String token
            //OAuth2AuthorizedClientService authorizedClientService
    ){
        this.authorizationHeaderName = authorizationHeaderName;
        this.urlApiMeli = urlApiMeli;
        this.token = token;

        /*this.authorizedClientService = authorizedClientService;

        client = authorizedClientService.loadAuthorizedClient("mercadolibre", "mercadolibre");*/

    }

    @Bean
    public MeliPublicApiService restRtEtaStorageApi() {
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
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(authorizationHeaderName, token)
                    .build();
            return chain.proceed(request);
        };
    }

}
