package com.dynatrace.server.sdk.exceptions;

import java.io.IOException;

public class ServerConnectionException extends IOException {
    public ServerConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
