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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;

import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.sessions.models.RecordingOption;
import com.dynatrace.sdk.server.sessions.models.StartRecordingRequest;
import com.dynatrace.sdk.server.sessions.models.StoreSessionRequest;
import com.dynatrace.sdk.server.testautomation.adapters.DateStringIso8601Adapter;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class SessionsTest {
	@Rule
	public WireMockRule wireMock = new WireMockRule();
	private Sessions sessions = new Sessions(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

	@Test
	public void stopRecording() throws Exception {

		String location = "returnedLocation";
		stubFor(put(urlPathEqualTo(Service.APP_VER_URI_PREFIX + String.format(Sessions.SESSIONS_EP, "testProfile", "recording/status")))
				.withRequestBody(containing("\"recording\":false"))
				.willReturn(aResponse().withStatus(204).withHeader("Location", location)));

		String stopRecording = sessions.stopRecording("testProfile");
		assertThat(location, is(stopRecording));
	}

	@Test
	public void startRecordingValidResponse() throws Exception {

		StartRecordingRequest request = new StartRecordingRequest("testProfile");
		request.setTimestampAllowed(true);
		request.setSessionLocked(true);
		request.setDescription("Description");
		request.setPresentableName("Presentablename");
		request.addLabel("Label1");
		request.addLabel("Label2");
		request.addLabel("Label4");
		request.setRecordingOption(RecordingOption.TIME_SERIES);

        String location = "locationResult";
		stubFor(post(urlPathEqualTo(Service.APP_VER_URI_PREFIX + String.format(Sessions.SESSIONS_EP, "testProfile", "recording")))
        		.withRequestBody(containing("\"appendtimestamp\":true"))
        		.withRequestBody(containing("\"locksession\":true"))
                .withRequestBody(containing("\"description\":\"Description\""))
        		.withRequestBody(containing("\"sessionname\":\"Presentablename\""))
        		.withRequestBody(containing("labels\":[\"Label1\",\"Label2\",\"Label4\"]"))
        		.withRequestBody(containing("\"recordingoption\":\"" +  RecordingOption.TIME_SERIES.getInternal() + "\""))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Location", location)));

        String startRecording = sessions.startRecording(request);
        assertThat(startRecording, is(location));
	}

	@Test
	public void startRecordingAlreadyRecording() throws Exception {
		final String errorReason = "Session Recording could not be started because it is already started";
		stubFor(post(urlPathEqualTo(Service.APP_VER_URI_PREFIX + String.format(Sessions.SESSIONS_EP, "test", "recording")))
				.willReturn(aResponse()
						.withStatus(404)
						.withBody("{ \"code\": 404, \"message\": \"" + errorReason + "\"}")));
		try {
			this.sessions.startRecording(new StartRecordingRequest("test"));
			fail("Exception was expected to be thrown");
		} catch (ServerResponseException e) {
			assertThat(e.getMessage(), is(errorReason));
		}
	}

	@Test
	public void store() throws Exception {

		StoreSessionRequest request = new StoreSessionRequest("testProfile");
		request.setAppendTimestamp(true);
		request.setSessionLocked(true);
		request.setDescription("test_description");
		request.addLabel("Label1");
		request.addLabel("Label2");
		request.addLabel("Label4");
		request.setTimeframeStart(DateStringIso8601Adapter.getAsDate("2016-01-02T12:34:56.789"));
		request.setTimeframeEnd(DateStringIso8601Adapter.getAsDate("2016-01-03T12:34:56.789"));
		request.setStoredSessionName("session_name_test");
		request.setRecordingOption(RecordingOption.TIME_SERIES);

		String location = "https://localhost:8021/api/v2/sessions/easyTravel%252F20170116121233_0.session";

        stubFor(post(urlPathEqualTo(Service.APP_VER_URI_PREFIX + String.format(Sessions.SESSIONS_EP, "testProfile", "store")))
                .withRequestBody(containing("\"sessionname\":\"session_name_test\""))
                .withRequestBody(containing("\"description\":\"test_description\""))
                .withRequestBody(containing("\"appendtimestamp\":true"))
                .withRequestBody(containing("\"recordingoption\":\"" +  RecordingOption.TIME_SERIES.getInternal() + "\""))
                .withRequestBody(containing("\"locksession\":true"))
                .withRequestBody(containing("labels\":[\"Label1\",\"Label2\",\"Label4\"]"))
                .withRequestBody(containing("\"timeframestart\":\"2016-01-02T12:34:56.789\""))
                .withRequestBody(containing("\"timeframeend\":\"2016-01-03T12:34:56.789\""))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Location", location)));

        assertThat(sessions.store(request), is(location));
	}
}
