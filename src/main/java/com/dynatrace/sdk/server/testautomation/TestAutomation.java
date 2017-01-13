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

package com.dynatrace.sdk.server.testautomation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.message.BasicNameValuePair;

import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.testautomation.models.CreateTestRunRequest;
import com.dynatrace.sdk.server.testautomation.models.FetchTestRunsRequest;
import com.dynatrace.sdk.server.testautomation.models.TestRun;
import com.dynatrace.sdk.server.testautomation.models.TestRuns;

/**
 * Wraps Dynatrace Server TestAutomation REST API providing an easy to use set of methods.
 */
@ThreadSafe
public class TestAutomation extends Service {
    public static final String TEST_RUNS_EP = "/rest/management/profiles/%s/testruns/%s";
    public static final String FINISH_TEST_RUN_EP = "/rest/management/profiles/%s/testruns/%s/finish";

    public TestAutomation(DynatraceClient client) {
        super(client);
    }

    /**
     * Registers a {@link TestRun} on a Dynatrace server.
     *
     * @param request {@link CreateTestRunRequest} - configuration parameters
     * @return a {@link TestRun} object, containing a {@code testRunId}, which can be used in {@link } as well as other parameters.
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public TestRun createTestRun(CreateTestRunRequest request) throws ServerConnectionException, ServerResponseException {

    	return this.doPostRequest(String.format(TEST_RUNS_EP, request.getSystemProfile(), ""), getBodyResponseResolver(TestRun.class), request);
    }

    /**
     * Waits for all PurePaths for given {@link TestRun} to be processed and then mark the {@link TestRun} as finished.
     * Returns the finished {@link TestRun} as a result.
     *
     * @param systemProfile {@link TestRun}'s system profile
     * @param testRunId     {@link TestRun}'s id
     * @return {@link TestRun} instance or {@code null} if no {@link TestRun} matching given parameters has been found
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public TestRun finishTestRun(String systemProfile, String testRunId) throws ServerConnectionException, ServerResponseException {

    	return this.doPutRequest(String.format(FINISH_TEST_RUN_EP, systemProfile, testRunId), null, getBodyResponseResolver(TestRun.class));
    }

    /**
     * Fetches a single {@link TestRun} based on it's {@code testRunId} and {@code systemProfile}.
     *
     * @param systemProfile {@link TestRun}'s system profile
     * @param testRunId     {@link TestRun}'s id
     * @return {@link TestRun} instance or {@code null} if no {@link TestRun} matching given parameters has been found
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public TestRun fetchTestRun(String systemProfile, String testRunId) throws ServerConnectionException, ServerResponseException {
    	return this.doGetRequest(String.format(TEST_RUNS_EP, systemProfile, testRunId), getBodyResponseResolver(TestRun.class));
    }

    /**
     * Fetches multiple {@link TestRun} matching given criteria
     *
     * @param request - parameters and criteria
     * @return {@link TestRuns} instance containing a {@link List} of {@link TestRun}
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public TestRuns fetchTestRuns(FetchTestRunsRequest request) throws ServerConnectionException, ServerResponseException {
        List<NameValuePair> nvps = new ArrayList<>();
        if (request.getStartTime() != null) {
            nvps.add(new BasicNameValuePair("startTime", String.valueOf(request.getStartTime())));
        }
        if (request.getEndTime() != null) {
            nvps.add(new BasicNameValuePair("endTime", String.valueOf(request.getEndTime())));
        }
        if (request.getExtend() != null) {
            nvps.add(new BasicNameValuePair("extend", request.getExtend().getInternal()));
        }
        if (request.getMaxTestRuns() != null) {
            nvps.add(new BasicNameValuePair("lastNTestruns", String.valueOf(request.getMaxTestRuns())));
        }
        if (request.getMaxBuilds() != null) {
            nvps.add(new BasicNameValuePair("lastNBuilds", String.valueOf(request.getMaxBuilds())));
        }

        for (Map.Entry<String, List<String>> filter : request.getFilters().entrySet()) {
            for (String value : filter.getValue()) {
                nvps.add(new BasicNameValuePair(filter.getKey(), value));
            }
        }

        return this.doGetRequest(String.format(TEST_RUNS_EP, request.getSystemProfile(), ""), getBodyResponseResolver(TestRuns.class),
        		nvps.toArray(new NameValuePair[nvps.size()]));
    }
}
