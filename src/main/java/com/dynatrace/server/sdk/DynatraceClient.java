package com.dynatrace.server.sdk;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@ThreadSafe
public class DynatraceClient {

    public static CloseableHttpClient buildClient(ServerConfiguration configuration) {
        HttpClientBuilder builder = HttpClients.custom();

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

        //setup basic http authentication
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, configuration);
        builder.setDefaultCredentialsProvider(provider);

        return builder.build();
    }

    private final CloseableHttpClient client;
    private final ServerConfiguration configuration;

    public DynatraceClient(ServerConfiguration configuration) {
        this.configuration = configuration;
        this.client = buildClient(configuration);
    }

    public CloseableHttpClient getClient() {
        return this.client;
    }

    public ServerConfiguration getConfiguration() {
        return this.configuration;
    }
}
