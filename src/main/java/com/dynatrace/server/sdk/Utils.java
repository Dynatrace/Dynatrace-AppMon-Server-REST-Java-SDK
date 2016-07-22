package com.dynatrace.server.sdk;

import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Utils {

    public static HttpClient buildClient(ServerConfiguration configuration) {
        HttpClientBuilder builder = HttpClients.custom();

        // marks all certificates as trusted
        if (configuration.isSkipCertificateCheck()) {
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

}
