package com.fligneul.apsystems.model;

import com.fligneul.apsystems.model.enums.ResponseCode;
import com.google.gson.annotations.SerializedName;

/**
 * Generic wrapper for all APsystems API responses.
 * @param <T> The type of the data returned by the API.
 */
public class ApiResponse<T> {
    @SerializedName("data")
    private T data;
    
    /**
     * The response code. 0 means success.
     */
    @SerializedName("code")
    private int code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Checks if the API request was successful (code == 0).
     * @return true if successful, false otherwise.
     */
    public boolean isSuccess() {
        return code == 0;
    }

    /**
     * Gets the mapped enum representation of the response code.
     * @return The corresponding ResponseCode enum.
     */
    public ResponseCode getResponseCodeEnum() {
        return ResponseCode.fromCode(code);
    }

    /**
     * Gets the human-readable description of the response code.
     * @return The error message or success description.
     */
    public String getErrorMessage() {
        return getResponseCodeEnum().getDescription();
    }
}
