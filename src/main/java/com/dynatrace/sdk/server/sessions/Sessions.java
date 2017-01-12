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

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.response.models.ResultResponse;
import com.dynatrace.sdk.server.sessions.models.StartRecordingRequest;
import com.dynatrace.sdk.server.sessions.models.StoreSessionRequest;

/**
 * Wraps a Live Session REST API, providing an easy to use set of methods to control sessions.
 * <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=175966050">Community Page</a>
 */
public class Sessions extends Service {
    public static final String SESSIONS_EP = "/rest/management/profiles/%s/%s";
    public static final String REANALYZE_SESSION_EP = "/rest/management/sessions/%s/reanalyze";
    public static final String REANALYZE_SESSION_STATUS_EP = "/rest/management/sessions/%s/reanalyze/finished";

    public Sessions(DynatraceClient client) {
        super(client);
    }

    /**
     * Starts session recording for a specific {@link StartRecordingRequest#getSystemProfile() system profile}.
     * <strong>Starting session recording is not possible if Continuous Transaction Storage is enabled.</strong>
     *
     * @param request - session parameters
     * @return session name
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public String startRecording(StartRecordingRequest request) throws ServerConnectionException, ServerResponseException {
        ArrayList<NameValuePair> nvps = new ArrayList<>();
        if (request.getPresentableName() != null) {
            nvps.add(new BasicNameValuePair("presentableName", request.getPresentableName()));
        }
        if (request.getDescription() != null) {
            nvps.add(new BasicNameValuePair("description", request.getDescription()));
        }
        if (request.isTimestampAllowed() != null) {
            nvps.add(new BasicNameValuePair("isTimeStampAllowed", String.valueOf(request.isTimestampAllowed())));
        }
        if (request.getRecordingOption() != null) {
            nvps.add(new BasicNameValuePair("recordingOption", request.getRecordingOption().getInternal()));
        }
        if (request.isSessionLocked() != null) {
            nvps.add(new BasicNameValuePair("isSessionLocked", String.valueOf(request.isSessionLocked())));
        }
        for (String label : request.getLabels()) {
            nvps.add(new BasicNameValuePair("label", label));
        }

        return this.doPostRequest(String.format(SESSIONS_EP,request.getSystemProfile(), "startrecording"), nvps).getValue();
    }

    /**
     * Stops live session recording
     * <strong>This call does complete until all recorded data is fully processed on the Server. Depending on the environment, it can take a few minutes until an HTTP response message is received.</strong>
     * <strong>Stopping session recording is not possible if Continuous Transaction Storage is enabled.</strong>
     *
     * @param profileName - profile name to stop the session of
     * @return session name
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public String stopRecording(String profileName) throws ServerResponseException, ServerConnectionException {

    	return this.doGetRequest(String.format(SESSIONS_EP, profileName, "stoprecording"), ResultResponse.class).getValue();
    }

    /**
     * Clears the live session.
     * <strong>Clearing a session is not possible if Continuous Transaction Storage is enabled.</strong>
     *
     * @param profileName - profile name to clean the live session of
     * @return a boolean indicating whether clearing a session was successful
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public boolean clear(String profileName) throws ServerResponseException, ServerConnectionException {

    	return this.doGetRequest(String.format(SESSIONS_EP, profileName, "clear")).getValueAsBoolean();
    }

    /**
     * Requests start reanalyzing a session with the name (@param sessionName)
     *
     * @param sessionName - session name to reanalyze
     * @return value that describes that the specified session could be found and session reanalysis started successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public boolean reanalyze(String sessionName) throws ServerResponseException, ServerConnectionException {

    	return this.doGetRequest(String.format(REANALYZE_SESSION_EP, sessionName)).getValueAsBoolean();
    }

    /**
     * Queries reanalysis status of the session with the name (@param sessionName)
     *
     * @param sessionName - session name to query
     * @return value that describes that session reanalysis is finished
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public boolean getReanalysisStatus(String sessionName) throws ServerResponseException, ServerConnectionException {

    	return this.doGetRequest(String.format(REANALYZE_SESSION_STATUS_EP, sessionName)).getValueAsBoolean();
    }

    /**
     * Store all time series and PurePaths in the Server's memory to a stored session
     * for a specific {@link StoreSessionRequest#getSystemProfile() system profile}.
     * <strong>
     * The timeframe parameters {@link StoreSessionRequest#timeframeStart} and {@link StoreSessionRequest#timeframeEnd}
     * must be formatted according to ISO 8601, without a time zone specification, in the form yyyy-MM-dd'T'HH:mm:ss or yyyy-MM-dd'T'HH:mm:ss.S
     * </strong>
     *
     * @param request - session parameters
     * @return session name
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public String store(StoreSessionRequest request) throws ServerConnectionException, ServerResponseException {
        ArrayList<NameValuePair> nvps = new ArrayList<>();
        if (request.isAppendTimestamp() != null) {
            nvps.add(new BasicNameValuePair("appendTimestamp", String.valueOf(request.isAppendTimestamp())));
        }
        if (request.getRecordingOption() != null) {
            nvps.add(new BasicNameValuePair("recordingOption", request.getRecordingOption().getInternal()));
        }
        if (request.isSessionLocked() != null) {
            nvps.add(new BasicNameValuePair("isSessionLocked", String.valueOf(request.isSessionLocked())));
        }
        if (request.getTimeframeStart() != null) {
            nvps.add(new BasicNameValuePair("timeframeStart", request.getTimeframeStart()));
        }
        if (request.getTimeframeEnd() != null) {
            nvps.add(new BasicNameValuePair("timeframeEnd", request.getTimeframeEnd()));
        }
        if (request.getStoredSessionName() != null) {
            nvps.add(new BasicNameValuePair("storedSessionName", request.getStoredSessionName()));
        }
        for (String label : request.getLabels()) {
            nvps.add(new BasicNameValuePair("label", label));
        }

        return this.doGetRequest(String.format(SESSIONS_EP, request.getSystemProfile(), "storepurepaths"),
        		nvps.toArray(new NameValuePair[nvps.size()])).getValue();
    }
}