package com.coupon.favorites.items.shared.util;

import com.coupon.favorites.items.shared.ApiCallException;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpClientErrorException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ApiCallExecutor {
    private ApiCallExecutor() {
        throw new IllegalStateException("ApiCallExecutor is a Utility class");
    }

    public static <T> T execute(@Nullable final Call<T> call) {
        Response<T> response;

        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                String message = extractErrorMessage(response);

                throw new HttpClientErrorException(HttpStatus.valueOf(response.code()), message);
            }
        } catch (Exception ex) {
            throw new ApiCallException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        }
        return response.body();
    }

    private static String extractErrorMessage(final Response errorResponse) {
        ResponseBody errorBody = errorResponse.errorBody();
        try {
            assert errorBody != null;
            return errorBody.string().trim();
        } catch (Exception ex) {
            return "Failed to get body. ["+ex.getCause()+"] ["+ ex.getMessage()+"]";
        }
    }

}