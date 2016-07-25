package com.dynatrace.server.sdk.exceptions;

public class ServerResponseException extends Exception {
    private final int statusCode;

    public ServerResponseException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public ServerResponseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
