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

import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.sessions.models.RecordingStatus;
import com.dynatrace.sdk.server.sessions.models.StartRecordingRequest;
import com.dynatrace.sdk.server.sessions.models.StoreSessionRequest;

/**
 * Wraps a Live Session REST API, providing an easy to use set of methods to control sessions.
 * <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=175966050">Community Page</a>
 */
public class Sessions extends Service {
    public static final String SESSIONS_EP = "/profiles/%s/session/%s";
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
     * @return created resource location
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public String startRecording(StartRecordingRequest request) throws ServerConnectionException, ServerResponseException {

        return this.doPostRequest(String.format(SESSIONS_EP, request.getSystemProfile(), "recording"),
        		Service.getHeaderLocationResolver(), request);
    }

    /**
     * Stops live session recording
     * <strong>This call does complete until all recorded data is fully processed on the Server. Depending on the environment, it can take a few minutes until an HTTP response message is received.</strong>
     * <strong>Stopping session recording is not possible if Continuous Transaction Storage is enabled.</strong>
     *
     * @param profileName - profile name to stop the session of
     * @return created resource location
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public String stopRecording(String profileName) throws ServerResponseException, ServerConnectionException {

    	return this.doPutRequest(String.format(SESSIONS_EP, profileName, "recording/status"),
    			new RecordingStatus(false), getHeaderLocationResolver());
    }

    /**
     * Store all time series and PurePaths in the Server's memory to a stored session
     * for a specific {@link StoreSessionRequest#getSystemProfile() system profile}.
     *
     * @param request - session parameters
     * @return created resource location
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public String store(StoreSessionRequest request) throws ServerConnectionException, ServerResponseException {

        return this.doPostRequest(String.format(SESSIONS_EP, request.getSystemProfile(), "store"),
        		Service.getHeaderLocationResolver(), request);
    }
}