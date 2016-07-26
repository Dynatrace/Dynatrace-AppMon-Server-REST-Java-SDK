package com.dynatrace.server.sdk.testruns;

import com.dynatrace.server.sdk.DynatraceClient;
import com.dynatrace.server.sdk.Service;
import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import com.dynatrace.server.sdk.testruns.models.CreateTestRunRequest;
import com.dynatrace.server.sdk.testruns.models.FetchTestRunsRequest;
import com.dynatrace.server.sdk.testruns.models.TestRun;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Wraps Dynatrace Server TestRuns REST API providing an easy to use set of methods.
 */
@ThreadSafe
public class TestRuns extends Service {
    public static final String TEST_RUNS_EP = "/rest/management/profiles/%s/testruns/%s";

    public TestRuns(DynatraceClient client) {
        super(client);
    }

    /**
     * Registers a {@link TestRun} on a Dynatrace server.
     *
     * @param request - {@link CreateTestRunRequest} - configuration parameters
     * @return a {@link TestRun} object, containing a {@code testRunId}, which can be used in {@link } as well as other parameters.
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public TestRun createTestRun(CreateTestRunRequest request) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(TEST_RUNS_EP, request.getSystemProfile(), ""));
            return this.doPostRequest(uri, request, TestRun.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid system profile format", e);
        }
    }

    /**
     * Fetches a single {@link TestRun} based on it's {@code testRunId} and {@code systemProfile}.
     *
     * @param systemProfile - {@link TestRun}'s system profile
     * @param testRunId     - {@link TestRun}'s id
     * @return {@link TestRun} instance or {@code null} if no {@link TestRun} matching given parameters has been found
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public TestRun fetchTestRun(String systemProfile, String testRunId) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(TEST_RUNS_EP, systemProfile, testRunId));
            return this.doGetRequest(uri, TestRun.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid system profile or testRunId format", e);
        }
    }

    /**
     * Fetches multiple {@link TestRun} matching given criteria
     *
     * @param request - parameters and criteria
     * @return {@link TestRuns} instance containing a {@link List} of {@link TestRun}
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public com.dynatrace.server.sdk.testruns.models.TestRuns fetchTestRuns(FetchTestRunsRequest request) throws ServerConnectionException, ServerResponseException {
        ArrayList<NameValuePair> nvps = new ArrayList<>();
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
        try {
            URI uri = this.buildURI(String.format(TEST_RUNS_EP, request.getSystemProfile(), ""), nvps.toArray(new NameValuePair[nvps.size()]));
            return this.doGetRequest(uri, com.dynatrace.server.sdk.testruns.models.TestRuns.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid system profile format", e);
        }
    }
}
