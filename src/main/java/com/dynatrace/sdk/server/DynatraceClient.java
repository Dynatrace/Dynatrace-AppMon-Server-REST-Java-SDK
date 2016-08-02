/*
 * Dynatrace Server SDK
 * Copyright (c) 2008-2016, DYNATRACE LLC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *  Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  Neither the name of the dynaTrace software nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package com.dynatrace.sdk.server;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * The main entry point holding HTTPClient and Configuration
 * It is advised to cache the instance and pass it to all services
 */
@ThreadSafe
public class DynatraceClient {
    public static HttpClientBuilder clientBuilder(ServerConfiguration configuration) {
        HttpClientBuilder builder = HttpClients.custom();
        builder.setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(configuration.getTimeout()).build());
        // marks all certificates as trusted
        if (!configuration.isValidateCertificates()) {
            try {
                builder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
                builder.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        return true;
                    }
                }).build());
            } catch (Exception e) {
                //should not happen
                throw new RuntimeException("Failed to create a HTTP Client with skipped certificates check.", e);
            }
        }
        return builder;
    }

    private final CloseableHttpClient client;
    private final ServerConfiguration configuration;

    public DynatraceClient(ServerConfiguration configuration) {
        this(configuration, clientBuilder(configuration).build());
    }

    public DynatraceClient(ServerConfiguration configuration, CloseableHttpClient httpClient) {
        this.configuration = configuration;
        this.client = httpClient;
    }

    public CloseableHttpClient getClient() {
        return this.client;
    }

    public ServerConfiguration getConfiguration() {
        return this.configuration;
    }
}
