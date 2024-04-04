package com.coupon.favorites.items.shared.util;


import com.coupon.favorites.items.shared.ApiCallException;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Objects;
import java.util.concurrent.Callable;

public class ApiCallExecutorCallable<T> implements Callable<T> {
    private final Call<T> call;

    public ApiCallExecutorCallable(Call<T> call) {
        this.call = call;
    }

    @Override
    public T call(){
            try {
                Response<T> response = call.execute();
                if (!response.isSuccessful()) {
                    String message = extractErrorMessage(response);
                    throw new HttpClientErrorException(HttpStatus.valueOf(response.code()), message);
                }
                return response.body();
            } catch (Exception ex) {
                throw new ApiCallException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            }
    }

    private static String extractErrorMessage(final Response errorResponse) {
        ResponseBody errorBody = errorResponse.errorBody();
        try {
            return Objects.nonNull(errorBody) ? errorBody.string().trim(): null;
        } catch (Exception ex) {
            return "Failed to get body. ["+ex.getCause()+"] ["+ ex.getMessage()+"]";
        }
    }
}

