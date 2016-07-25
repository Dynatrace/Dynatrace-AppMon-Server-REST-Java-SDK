package com.dynatrace.server.sdk.testruns;

import com.dynatrace.server.sdk.DynatraceClient;
import com.dynatrace.server.sdk.Service;
import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import com.dynatrace.server.sdk.testruns.models.CreateTestRunRequest;
import com.dynatrace.server.sdk.testruns.models.FetchTestRunsRequest;
import com.dynatrace.server.sdk.testruns.models.TestRun;
import org.apache.http.NameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Wraps Dynatrace Server TestRuns REST API providing an easy to use set of methods.
 */
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
    public TestRuns fetchTestRuns(FetchTestRunsRequest request) throws ServerConnectionException, ServerResponseException {
        List<NameValuePair> nvps = request.getParameters();
        try {
            URI uri = this.buildURI(String.format(TEST_RUNS_EP, request.getSystemProfile(), ""), nvps.toArray(new NameValuePair[nvps.size()]));
            return this.doGetRequest(uri, TestRuns.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid system profile format", e);
        }
    }
}
