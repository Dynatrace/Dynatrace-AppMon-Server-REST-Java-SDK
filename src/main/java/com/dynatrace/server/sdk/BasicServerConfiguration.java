package com.dynatrace.server.sdk;

import org.apache.http.annotation.Immutable;
import org.apache.http.annotation.ThreadSafe;

import java.security.Principal;

/**
 * A basic immutable implementation of {@link ServerConfiguration} interface.
 * Immutable and thus thread-safe.
 *
 * @author Maciej Mionskowski
 */
@Immutable
@ThreadSafe
public class BasicServerConfiguration implements ServerConfiguration {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8021;
    public static final boolean DEFAULT_SSL = true;
    public static final boolean DEFAULT_VALIDATE_CERTIFICATES = true;
    //5 seconds default timeout
    public static final int DEFAULT_CONNECTION_TIMEOUT = 5 * 1000;

    private final String name;
    private final String password;
    private final boolean ssl;
    private final String host;
    private final int port;
    private final int connectionTimeout;
    private final boolean validateCertificates;

    /**
     * Constructs BasicServerConfiguration with given {@code username} and {@code password} and populating it with default values:
     * <dl>
     * <dt>Host</dt>
     * <dd>{@value DEFAULT_HOST}</dd>
     * <dt>Port</dt>
     * <dd>{@value DEFAULT_PORT}</dd>
     * <dt>SSL</dt>
     * <dd>{@value DEFAULT_SSL}</dd>
     * <dt>Connection timeout</dt>
     * <dd>{@value DEFAULT_CONNECTION_TIMEOUT}</dd>
     * </dl>
     *
     * @param name                 - server's username
     * @param password             - server's password
     * @param validateCertificates - whether to validate certificates
     */
    public BasicServerConfiguration(String name, String password, boolean validateCertificates) {
        this(name, password, DEFAULT_SSL, DEFAULT_HOST, DEFAULT_PORT, validateCertificates, DEFAULT_CONNECTION_TIMEOUT);
    }

    /**
     * Constructs BasicServerConfiguration with given {@code username} and {@code password} and populating it with default values:
     * <dl>
     * <dt>Host</dt>
     * <dd>{@value DEFAULT_HOST}</dd>
     * <dt>Port</dt>
     * <dd>{@value DEFAULT_PORT}</dd>
     * <dt>SSL</dt>
     * <dd>{@value DEFAULT_SSL}</dd>
     * <dt>Validate Certificates</dt>
     * <dd>{@value DEFAULT_VALIDATE_CERTIFICATES}</dd>
     * <dt>Connection timeout</dt>
     * <dd>{@value DEFAULT_CONNECTION_TIMEOUT}</dd>
     * </dl>
     *
     * @param name     - server's username
     * @param password - server's password
     */
    public BasicServerConfiguration(String name, String password) {
        this(name, password, DEFAULT_SSL, DEFAULT_HOST, DEFAULT_PORT, DEFAULT_VALIDATE_CERTIFICATES, DEFAULT_CONNECTION_TIMEOUT);
    }

    /**
     * Constructs BasicServerConfiguration with given parameters
     *
     * @param name                 - server's username
     * @param password             - server's password
     * @param host                 - server's host
     * @param ssl                  - whether to preceed hostname with https:// or leave http://
     * @param port                 - server's port
     * @param validateCertificates - whether to validate certificates
     * @param connectionTimeout    - connection timeout in milliseconds
     */
    public BasicServerConfiguration(String name, String password, boolean ssl, String host, int port, boolean validateCertificates, int connectionTimeout) {
        this.name = name;
        this.password = password;
        this.ssl = ssl;
        this.host = host;
        this.port = port;
        this.validateCertificates = validateCertificates;
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public Principal getUserPrincipal() {
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isSSL() {
        return this.ssl;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public int getTimeout() {
        return this.connectionTimeout;
    }

    @Override
    public boolean isValidateCertificates() {
        return this.validateCertificates;
    }
}
