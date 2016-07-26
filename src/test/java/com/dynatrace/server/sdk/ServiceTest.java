package com.dynatrace.server.sdk;

import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import com.dynatrace.server.sdk.testautomation.models.TestCategory;
import com.dynatrace.server.sdk.testautomation.models.TestRun;
import com.github.tomakehurst.wiremock.client.BasicCredentials;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.net.UnknownHostException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ServiceTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule(8080);
    private Service service;

    @Before
    public void setup() {
        this.service = new Service(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false))) {
        };
    }

    @Test
    public void invalidStatusCodeWithError() throws Exception {
        stubFor(get(urlPathEqualTo("/test")).willReturn(aResponse().withStatus(404).withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"Profile not found\"/>")));
        try {
            this.service.doGetRequest(this.service.buildURI("/test"), null);
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException e) {
            assertThat(e.getStatusCode(), is(404));
            assertThat(e.getMessage(), is("Profile not found"));
        }
    }

    @Test
    public void invalidStatusCodeWithoutError() throws Exception {
        stubFor(get(urlPathEqualTo("/404")).willReturn(aResponse().withStatus(404)));
        stubFor(get(urlPathEqualTo("/300")).willReturn(aResponse().withStatus(300)));

        try {
            this.service.doGetRequest(this.service.buildURI("/404"), null);
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException e) {
            assertThat(e.getStatusCode(), is(404));
        }

        try {
            this.service.doGetRequest(this.service.buildURI("/300"), null);
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException e) {
            assertThat(e.getStatusCode(), is(300));
        }
    }

    @Test
    public void validResponse() throws Exception {
        stubFor(get(urlPathEqualTo("/test")).withBasicAuth("admin", "admin").willReturn(aResponse().withStatus(200).withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testRun category=\"unit\" versionBuild=\"15:52:11\" versionMajor=\"2016\" versionMinor=\"7\" versionRevision=\"5\" platform=\"Linux 4.4.13-1-MANJARO x86_64\" startTime=\"1469109132002\" id=\"3283730b-364b-419a-adfd-c5653c62c789\" numPassed=\"4\" numFailed=\"0\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"IntelliJ\" creationMode=\"MANUAL\" href=\"https://localhost:8021/rest/management/profiles/IntelliJ/testruns/3283730b-364b-419a-adfd-c5653c62c789.xml\"/>")));
        TestRun tr = this.service.doGetRequest(this.service.buildURI("/test"), TestRun.class);
        //we test the rest of marshalling process for TestRun in a separate test
        assertThat(tr.getCategory(), is(TestCategory.UNIT));
        verify(getRequestedFor(urlPathEqualTo("/test")).withBasicAuth(new BasicCredentials("admin", "admin")));
    }

    @Test
    public void malformedXML() throws Exception {
        stubFor(get(urlPathEqualTo("/test")).willReturn(aResponse().withStatus(200).withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>testRun category=\"unit\" versionBuild=\"15:52:11\" versionMajor=\"2016\" versionMinor=\"7\" versionRevision=\"5\" platform=\"Linux 4.4.13-1-MANJARO x86_64\" startTime=\"1469109132002\" id=\"3283730b-364b-419a-adfd-c5653c62c789\" numPassed=\"4\" numFailed=\"0\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"IntelliJ\" creationMode=\"MANUAL\" href=\"https://localhost:8021/rest/management/profiles/IntelliJ/testruns/3283730b-364b-419a-adfd-c5653c62c789.xml\"/>")));
        try {
            this.service.doGetRequest(this.service.buildURI("/test"), TestRun.class);
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException e) {
            assertTrue(e.getCause() instanceof JAXBException);
        }
    }

    @Test
    public void invalidPort() throws Exception {
        //http://en.wikipedia.org/wiki/Ephemeral_port
        this.service = new Service(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 49153, false))) {
        };
        try {
            this.service.doGetRequest(this.service.buildURI("/"));
        } catch (ServerConnectionException e) {
            assertTrue(e.getCause() instanceof HttpHostConnectException);
        }
    }

    @Test
    public void invalidHostname() throws Exception {
        this.service = new Service(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "INVALID", 8080, false))) {
        };
        try {
            this.service.doGetRequest(this.service.buildURI("/"));
            fail("Exception was expected to be thrown");
        } catch (ServerConnectionException e) {
            assertTrue(e.getCause() instanceof UnknownHostException);
        }
    }
}
