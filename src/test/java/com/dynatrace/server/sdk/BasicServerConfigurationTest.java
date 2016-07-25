package com.dynatrace.server.sdk;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BasicServerConfigurationTest {
    @Test
    public void constructMinimal() {
        BasicServerConfiguration bsc = new BasicServerConfiguration("constructMinimalUser", "constructMinimalPassword");
        assertThat(bsc.getName(), is("constructMinimalUser"));
        assertThat(bsc.getPassword(), is("constructMinimalPassword"));
        assertThat(bsc.getHost(), is(BasicServerConfiguration.DEFAULT_HOST));
        assertThat(bsc.getPort(), is(BasicServerConfiguration.DEFAULT_PORT));
        assertThat(bsc.isSSL(), is(BasicServerConfiguration.DEFAULT_SSL));
        assertThat(bsc.isValidateCertificates(), is(BasicServerConfiguration.DEFAULT_VALIDATE_CERTIFICATES));
    }

    @Test
    public void construct() {
        BasicServerConfiguration bsc = new BasicServerConfiguration("constructUser", "constructPassword", !BasicServerConfiguration.DEFAULT_SSL, "8.8.8.8", 8080, !BasicServerConfiguration.DEFAULT_VALIDATE_CERTIFICATES);
        assertThat(bsc.getName(), is("constructUser"));
        assertThat(bsc.getPassword(), is("constructPassword"));
        assertThat(bsc.getHost(), is("8.8.8.8"));
        assertThat(bsc.getPort(), is(8080));
        assertThat(bsc.isSSL(), is(!BasicServerConfiguration.DEFAULT_SSL));
        assertThat(bsc.isValidateCertificates(), is(!BasicServerConfiguration.DEFAULT_VALIDATE_CERTIFICATES));
    }
}
