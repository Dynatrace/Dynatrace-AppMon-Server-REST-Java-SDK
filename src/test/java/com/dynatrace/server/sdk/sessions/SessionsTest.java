package com.dynatrace.server.sdk.sessions;

import com.dynatrace.server.sdk.BasicServerConfiguration;
import com.dynatrace.server.sdk.DynatraceClient;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import com.dynatrace.server.sdk.sessions.models.RecordingOption;
import com.dynatrace.server.sdk.sessions.models.StartRecordingRequest;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class SessionsTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private Sessions sessions = new Sessions(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void endpoint() {
        assertThat(String.format(Sessions.SESSIONS_EP, "test", "clear"), is("/rest/management/profiles/test/clear"));
    }

    @Test
    public void stopRecording() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(Sessions.SESSIONS_EP, "test", "stoprecording")))
                .willReturn(aResponse()
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<result value=\"test [16:57:48]\"/>")));
        String sessionName = this.sessions.stopRecording("test");
        assertThat(sessionName, is("test [16:57:48]"));
    }


    @Test
    public void clear() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(Sessions.SESSIONS_EP, "test", "clear")))
                .willReturn(aResponse()
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<result value=\"true\"/>")));
        stubFor(get(urlPathEqualTo(String.format(Sessions.SESSIONS_EP, "non-existent", "clear")))
                .willReturn(aResponse()
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<result value=\"false\"/>")));

        boolean result = this.sessions.clear("test");
        assertThat(result, is(true));
        result = this.sessions.clear("non-existent");
        assertThat(result, is(false));
    }

    @Test
    public void startRecordingValidResponse() throws Exception {
        stubFor(post(urlPathEqualTo(String.format(Sessions.SESSIONS_EP, "test", "startrecording")))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withRequestBody(containing("isTimeStampAllowed=true"))
                .withRequestBody(containing("description=Description"))
                .withRequestBody(containing("isSessionLocked=true"))
                .withRequestBody(containing("presentableName=Presentablename"))
                .withRequestBody(containing("label=Label1"))
                .withRequestBody(containing("label=Label2"))
                .withRequestBody(containing("recordingOption=timeseries"))
                .willReturn(aResponse().withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"IntelliJ\"/>")));
        StartRecordingRequest request = new StartRecordingRequest("test");
        request.setTimestampAllowed(true);
        request.setSessionLocked(true);
        request.setDescription("Description");
        request.setPresentableName("Presentablename");
        request.addLabel("Label1");
        request.setRecordingOption(RecordingOption.TIME_SERIES);
        request.addLabel("Label2");
        String sessionName = this.sessions.startRecording(request);
        assertThat(sessionName, is("IntelliJ"));
    }

    @Test
    public void startRecordingAlreadyRecording() throws Exception {
        final String errorReason = "Session Recording could not be started because it is already started";
        stubFor(post(urlPathEqualTo(String.format(Sessions.SESSIONS_EP, "test", "startrecording")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"" + errorReason + "\"/>")));
        try {
            String sessionName = this.sessions.startRecording(new StartRecordingRequest("test"));
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException e) {
            assertThat(e.getMessage(), is(errorReason));
        }
    }
}
