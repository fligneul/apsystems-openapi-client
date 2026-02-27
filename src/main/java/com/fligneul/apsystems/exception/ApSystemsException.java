package com.fligneul.apsystems.exception;

public class ApSystemsException extends RuntimeException {
    public ApSystemsException(String message) {
        super(message);
    }

    public ApSystemsException(String message, Throwable cause) {
        super(message, cause);
    }
}
