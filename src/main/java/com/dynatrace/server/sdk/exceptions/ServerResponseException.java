package com.dynatrace.server.sdk.exceptions;

public class ServerResponseException extends Exception {
    public ServerResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerResponseException(String message) {
        super(message);
    }
}
