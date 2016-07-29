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

package com.dynatrace.sdk.server.sessions;

import com.dynatrace.sdk.server.sessions.models.RecordingOption;
import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.sessions.models.StartRecordingRequest;
import com.dynatrace.sdk.server.sessions.models.StoreSessionRequest;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

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

    @Test
    public void reanalyze() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(Sessions.REANALYZE_SESSION_EP, "test")))
                .willReturn(aResponse()
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<result value=\"true\"/>")));
        stubFor(get(urlPathEqualTo(String.format(Sessions.REANALYZE_SESSION_EP, "non-existent")))
                .willReturn(aResponse()
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<result value=\"false\"/>")));

        boolean result = this.sessions.reanalyze("test");
        assertThat(result, is(true));
        result = this.sessions.reanalyze("non-existent");
        assertThat(result, is(false));
    }

    @Test
    public void getReanalysisStatus() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(Sessions.REANALYZE_SESSION_STATUS_EP, "test")))
                .willReturn(aResponse()
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<result value=\"true\"/>")));
        stubFor(get(urlPathEqualTo(String.format(Sessions.REANALYZE_SESSION_STATUS_EP, "non-existent")))
                .willReturn(aResponse()
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<result value=\"false\"/>")));

        boolean result = this.sessions.getReanalysisStatus("test");
        assertThat(result, is(true));
        result = this.sessions.getReanalysisStatus("non-existent");
        assertThat(result, is(false));
    }

    @Test
    public void store() throws Exception {
        ArrayList<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("appendTimestamp", String.valueOf(true)));
        nvps.add(new BasicNameValuePair("recordingOption", RecordingOption.TIME_SERIES.getInternal()));
        nvps.add(new BasicNameValuePair("isSessionLocked", String.valueOf(true)));
        nvps.add(new BasicNameValuePair("timeframeStart", "2016-01-02'T'12:34:56.789"));
        nvps.add(new BasicNameValuePair("timeframeEnd", "2016-01-03'T'12:34:56.789"));
        nvps.add(new BasicNameValuePair("storedSessionName", "session"));
        nvps.add(new BasicNameValuePair("label", "Label1"));
        nvps.add(new BasicNameValuePair("label", "Label2"));

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(String.format(Sessions.SESSIONS_EP, "test", "storepurepaths"));
        uriBuilder.setParameters(nvps);
        String uri = uriBuilder.build().toString();

        stubFor(get(urlEqualTo(uri))
                .willReturn(aResponse()
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<result value=\"test\"/>")));

        StoreSessionRequest request = new StoreSessionRequest("test");
        request.setAppendTimestamp(true);
        request.setSessionLocked(true);
        request.addLabel("Label1");
        request.addLabel("Label2");
        request.setTimeframeStart("2016-01-02'T'12:34:56.789");
        request.setTimeframeEnd("2016-01-03'T'12:34:56.789");
        request.setStoredSessionName("session");
        request.setRecordingOption(RecordingOption.TIME_SERIES);

        String sessionName = this.sessions.store(request);
        assertThat(sessionName, is("test"));
    }
}
