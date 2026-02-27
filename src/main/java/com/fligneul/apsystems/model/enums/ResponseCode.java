package com.fligneul.apsystems.model.enums;

/**
 * API Response Codes as defined in Annex 1 of the APsystems OpenAPI manual.
 */
public enum ResponseCode {
    SUCCESS(0, "Succeed to request."),
    DATA_EXCEPTION(1000, "Data exception."),
    NO_DATA(1001, "No data."),
    APP_ACCOUNT_EXCEPTION(2000, "Application account exception."),
    INVALID_APP_ACCOUNT(2001, "Invalid application account."),
    APP_NOT_AUTHORIZED(2002, "The application account is not authorized."),
    APP_AUTH_EXPIRES(2003, "Application account authorization expires."),
    APP_NO_PERMISSION(2004, "The application account has no permission."),
    APP_LIMIT_EXCEEDED(2005, "The access limit of the application account was exceeded."),
    ACCESS_TOKEN_EXCEPTION(3000, "Access token exception."),
    MISSING_ACCESS_TOKEN(3001, "Missing Access token."),
    UNABLE_TO_VERIFY_TOKEN(3002, "Unable to verify Access token."),
    ACCESS_TOKEN_TIMEOUT(3003, "Access token timeout."),
    REFRESH_TOKEN_TIMEOUT(3004, "Refresh token timeout."),
    REQUEST_PARAM_EXCEPTION(4000, "Request parameter exception."),
    INVALID_REQUEST_PARAM(4001, "Invalid request parameter."),
    INTERNAL_SERVER_EXCEPTION(5000, "Internal server exception."),
    COMMUNICATION_EXCEPTION(6000, "Communication exception."),
    SERVER_RESTRICTION_EXCEPTION(7000, "Server access restriction exception."),
    SERVER_LIMIT_EXCEEDED(7001, "Server access limit exceeded."),
    TOO_MANY_REQUESTS(7002, "Too many requests, please request later."),
    SYSTEM_BUSY(7003, "The system is busy, please request later."),
    UNKNOWN(-1, "Unknown response code.");

    private final int code;
    private final String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ResponseCode fromCode(int code) {
        for (ResponseCode value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
