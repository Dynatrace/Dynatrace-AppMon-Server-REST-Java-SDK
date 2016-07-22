package com.dynatrace.server.sdk.testruns;

import com.dynatrace.server.sdk.BasicServerConfiguration;
import com.dynatrace.server.sdk.ServerConfiguration;
import com.dynatrace.server.sdk.Service;
import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import com.dynatrace.server.sdk.testruns.models.TestRun;
import com.dynatrace.server.sdk.testruns.models.TestRunRequest;

import java.net.URI;
import java.net.URISyntaxException;

public class TestRuns extends Service {
    public static final String TEST_RUNS_EP = "/rest/management/profiles/%s/testruns/%s";

    public TestRuns(ServerConfiguration configuration) {
        super(configuration);
    }

    public TestRun createTestRun(TestRunRequest request) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(TEST_RUNS_EP, "", request.getSystemProfile(), "");
            return this.doPostRequest(uri, request, TestRun.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid system profile format", e);
        }
    }
}
