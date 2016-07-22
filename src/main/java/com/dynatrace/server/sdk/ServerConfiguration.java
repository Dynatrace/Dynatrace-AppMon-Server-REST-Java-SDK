package com.dynatrace.server.sdk;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.auth.Credentials;

import java.security.Principal;

/**
 * Provides a server's configuration.
 * Used to construct every single module
 * <strong>Methods should be thread-safe!</strong>
 */
@ThreadSafe
public interface ServerConfiguration extends Credentials, Principal {
    String getHost();

    boolean isSSL();

    int getPort();

    /**
     * Indicates whether to accept invalid certificates.
     * Useful for development machines, should return false in production environment.
     */
    boolean isSkipCertificateCheck();
}
