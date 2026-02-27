package com.fligneul.apsystems.exception;

import com.fligneul.apsystems.model.enums.ResponseCode;

/**
 * Exception thrown when the APsystems API returns a non-zero response code.
 */
public class ApSystemsApiException extends ApSystemsException {
    private final int code;
    private final ResponseCode responseCode;

    public ApSystemsApiException(int code, String message) {
        super(message);
        this.code = code;
        this.responseCode = ResponseCode.fromCode(code);
    }

    public int getCode() {
        return code;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
