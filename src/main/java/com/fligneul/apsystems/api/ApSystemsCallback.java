package com.fligneul.apsystems.api;

import com.fligneul.apsystems.exception.ApSystemsException;

/**
 * Callback for asynchronous APsystems API calls.
 * @param <T> The type of the data returned by the API.
 */
public interface ApSystemsCallback<T> {
    /**
     * Called when the request was successful.
     * @param result The unwrapped data from the API.
     */
    void onSuccess(T result);

    /**
     * Called when the request failed (HTTP error, API error, or network error).
     * @param exception The exception describing the error.
     */
    void onFailure(ApSystemsException exception);
}
