package com.fligneul.apsystems.api;

import com.fligneul.apsystems.exception.ApSystemsApiException;
import com.fligneul.apsystems.exception.ApSystemsException;
import com.fligneul.apsystems.model.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * A wrapper for Retrofit's {@link Call} that unwraps the {@link ApiResponse} envelope.
 * @param <T> The type of the data inside the ApiResponse.
 */
public class ApSystemsCall<T> {
    private final Call<ApiResponse<T>> delegate;

    public ApSystemsCall(Call<ApiResponse<T>> delegate) {
        this.delegate = delegate;
    }

    /**
     * Converts the call to a {@link CompletableFuture}.
     * @return A future that will complete with the unwrapped data or an exception.
     */
    public CompletableFuture<T> toCompletableFuture() {
        CompletableFuture<T> future = new CompletableFuture<>();
        enqueue(new ApSystemsCallback<T>() {
            @Override
            public void onSuccess(T result) {
                future.complete(result);
            }

            @Override
            public void onFailure(ApSystemsException exception) {
                future.completeExceptionally(exception);
            }
        });
        return future;
    }

    /**
     * Executes the request synchronously and returns the unwrapped data.
     * @return The data returned by the API.
     * @throws ApSystemsException if a network or parsing error occurs.
     * @throws ApSystemsApiException if the API returns a non-zero code.
     */
    public T execute() throws ApSystemsException {
        try {
            Response<ApiResponse<T>> response = delegate.execute();
            return handleResponse(response);
        } catch (IOException e) {
            throw new ApSystemsException("Network error during API call", e);
        }
    }

    /**
     * Executes the request asynchronously.
     * @param callback The callback to handle the result.
     */
    public void enqueue(final ApSystemsCallback<T> callback) {
        delegate.enqueue(new Callback<ApiResponse<T>>() {
            @Override
            public void onResponse(Call<ApiResponse<T>> call, Response<ApiResponse<T>> response) {
                try {
                    T result = handleResponse(response);
                    callback.onSuccess(result);
                } catch (ApSystemsException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<T>> call, Throwable t) {
                if (t instanceof ApSystemsException) {
                    callback.onFailure((ApSystemsException) t);
                } else {
                    callback.onFailure(new ApSystemsException("Request failed: " + t.getMessage(), t));
                }
            }
        });
    }

    private T handleResponse(Response<ApiResponse<T>> response) {
        if (!response.isSuccessful()) {
            throw new ApSystemsException("HTTP error " + response.code() + ": " + response.message());
        }

        ApiResponse<T> body = response.body();
        if (body == null) {
            throw new ApSystemsException("Empty response body from API");
        }

        if (!body.isSuccess()) {
            throw new ApSystemsApiException(body.getCode(), body.getErrorMessage());
        }

        return body.getData();
    }

    /**
     * Cancels the request.
     */
    public void cancel() {
        delegate.cancel();
    }

    /**
     * Clones the call.
     */
    public ApSystemsCall<T> clone() {
        return new ApSystemsCall<>(delegate.clone());
    }
}
