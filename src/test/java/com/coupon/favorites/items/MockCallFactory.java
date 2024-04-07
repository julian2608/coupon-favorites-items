package com.coupon.favorites.items;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockCallFactory {

    public static <T> Call<T> getMockCallWithResponse(final T mockResponse) {
        return MockCallFactory.getMockCallWithResponse(mockResponse, null, "GET");
    }

    public static <T> Call<T> getMockCallWithResponse(final T mockResponse,
                                                      final RequestBody requestBody,
                                                      final String method) {
        return new Call<T>() {
            @Override
            public Response<T> execute() throws IOException {
                return Response.success(mockResponse);
            }

            @Override
            public void enqueue(final Callback<T> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<T> clone() {
                return null;
            }

            @Override
            public Request request() {
                return new Request
                        .Builder()
                        .method(method, requestBody)
                        .url("https://fake.url")
                        .build();
            }

            public Timeout timeout() {
                return null;
            }
        };
    }
}
