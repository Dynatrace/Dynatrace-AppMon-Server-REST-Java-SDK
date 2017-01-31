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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;

import static org.hamcrest.Matchers.nullValue;

import org.junit.Rule;
import org.junit.Test;

import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.testautomation.adapters.DateStringIso8601Adapter;
import com.dynatrace.sdk.server.testautomation.models.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class TestAutomationTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private TestAutomation testAutomation = new TestAutomation(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void createTestRun() throws Exception {
        stubFor(post(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(TestAutomation.TEST_RUNS_EP, "easyTravel", "")))
                .withRequestBody(equalToJson("{\n" +
                        "  \"platform\": \"platformValue\",\n" +
                        "  \"category\": \"unit\",\n" +
                        "  \"versionMajor\": \"major1\",\n" +
                        "  \"versionMinor\": \"minor1\",\n" +
                        "  \"versionRevision\": \"revision1\",\n" +
                        "  \"versionBuild\": \"build1\",\n" +
                        "  \"versionMilestone\": \"milestone1\",\n" +
                        "  \"marker\": \"markerValue\",\n" +
                        "  \"includedMetrics\": [\n" +
                        "    {\n" +
                        "      \"group\": \"start1\",\n" +
                        "      \"metric\": \"stop1\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"group\": \"start2\",\n" +
                        "      \"metric\": \"stop2\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")).willReturn(aResponse()
                        .withStatus(201)
                        .withBody("{\n" +
                                "  \"id\": \"9a338757-5007-4bee-aa19-4952c7953a1e\",\n" +
                                "  \"category\": \"unit\",\n" +
                                "  \"versionBuild\": \"build1\",\n" +
                                "  \"versionMajor\": \"major1\",\n" +
                                "  \"versionMilestone\": \"milestone1\",\n" +
                                "  \"versionMinor\": \"minor1\",\n" +
                                "  \"versionRevision\": \"revision1\",\n" +
                                "  \"platform\": \"platformValue\",\n" +
                                "  \"startTime\": \"2017-01-16T16:56:23.518+01:00\",\n" +
                                "  \"systemProfile\": \"easyTravel\",\n" +
                                "  \"marker\": \"string\",\n" +
                                "  \"href\": \"https://localhost:8021/api/v2/profiles/easyTravel/testruns/9a338757-5007-4bee-aa19-4952c7953a1e\",\n" +
                                "  \"creationMode\": \"MANUAL\",\n" +
                                "  \"numDegraded\": 0,\n" +
                                "  \"numFailed\": 0,\n" +
                                "  \"numImproved\": 0,\n" +
                                "  \"numInvalidated\": 0,\n" +
                                "  \"numPassed\": 0,\n" +
                                "  \"numVolatile\": 0,\n" +
                                "  \"finished\": false,\n" +
                                "  \"includedMetrics\": [\n" +
                                "    {\n" +
                                "      \"group\": \"group1\",\n" +
                                "      \"metric\": \"metric1\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"group\": \"group2\",\n" +
                                "      \"metric\": \"metric2\"\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}")));


        CreateTestRunRequest request = new CreateTestRunRequest();
        request.setSystemProfile("easyTravel");
        request.setVersionBuild("build1");
        request.setVersionMajor("major1");
        request.setVersionMilestone("milestone1");
        request.setVersionMinor("minor1");
        request.setVersionRevision("revision1");
        request.setMarker("markerValue");
        request.setPlatform("platformValue");
        request.setCategory(TestCategory.UNIT);
        request.setIncludedMetrics(Arrays.asList(new TestMetricFilter("start1", "stop1"),
                new TestMetricFilter("start2", "stop2")));

        TestRun tr = this.testAutomation.createTestRun(request);
        assertThat(tr.getCategory(), is(TestCategory.UNIT));
        assertThat(tr.getVersionBuild(), is("build1"));
        assertThat(tr.getId(), is("9a338757-5007-4bee-aa19-4952c7953a1e"));
        assertThat(tr.getSystemProfile(), is("easyTravel"));
        assertThat(tr.getCreationMode(), is(CreationMode.MANUAL));
        assertThat(tr.isFinished(), is(false));
        assertThat(tr.getHref(), is("https://localhost:8021/api/v2/profiles/easyTravel/testruns/9a338757-5007-4bee-aa19-4952c7953a1e"));
        assertThat(tr.getIncludedMetrics().size(), is(2));
        assertThat(tr.getIncludedMetrics().get(0).getGroup(), is("group1"));
        assertThat(tr.getIncludedMetrics().get(0).getMetric(), is("metric1"));
        assertThat(tr.getIncludedMetrics().get(1).getGroup(), is("group2"));
        assertThat(tr.getIncludedMetrics().get(1).getMetric(), is("metric2"));
    }

    @Test
    public void fetchTestRun() throws Exception {
        stubFor(get(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(TestAutomation.TEST_RUNS_EP, "easyTravel", "a75964f5-7a41-4794-b929-fbd694456ad4")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("fetchSingleTestrunResponse.json")));

        TestRun tr = this.testAutomation.fetchTestRun("easyTravel", "a75964f5-7a41-4794-b929-fbd694456ad4");

        assertThat(tr.getId(), is("a75964f5-7a41-4794-b929-fbd694456ad4"));
        assertThat(tr.getPassedCount(), is(6));
        assertThat(tr.getFailedCount(), is(1));
        assertThat(tr.getSystemProfile(), is("easyTravel"));
        assertThat(tr.getCreationMode(), is(CreationMode.MANUAL));
        assertThat(tr.getCategory(), is(TestCategory.UNIT));
        assertThat(tr.getVersionBuild(), is("3"));
        assertThat(tr.getVersionMajor(), is("1"));
        assertThat(tr.getVersionMinor(), is("2"));
        assertThat(tr.getVersionRevision(), is("3"));
        assertThat(tr.getPlatform(), is("Windows"));
        assertThat(tr.getStartTime(), is(DateStringIso8601Adapter.getAsDate("2017-01-10T14:56:54.722+01:00")));
        assertThat(tr.getTestResults().size(), is(6));

        //Test a single test result unmarshalling
        TestResult result = tr.getTestResults().get(0);
        assertThat(result.getName(), is("SumTest.sumWithoutAdding"));
        assertThat(result.getStatus(), is(TestStatus.PASSED));
        assertThat(result.getExecutionTime(), is(DateStringIso8601Adapter.getAsDate("2017-01-10T14:56:56.785+01:00")));
        assertThat(result.getPackageName(), is("com.example.simple"));
        assertThat(result.getPlatform(), is("Windows"));
        assertThat(result.getMeasures().size(), is(1));

        //Test it's measures
        TestMeasure measure = result.getMeasures().get(0);
        assertThat(measure.getName(), is("Failed Transaction Count"));
        assertThat(measure.getMetricGroup(), is("Error Detection"));
        assertThat(measure.getValue(), is(0.0));
        assertThat(measure.getUnit(), is("num"));
        assertThat(measure.getViolationPercentage(), is(0.0));
        assertThat(measure.getFailingOrInvalidatedRunsCount(), is(2));
        assertThat(measure.getValidRunsCount(), is(9));
        assertThat(measure.getImprovedRunsCount(), is(0));
        assertThat(measure.getDegradedRunsCount(), is(0));

        //check edge cases
        TestResult tr2 = tr.getTestResults().get(2);
        assertThat(tr2.getMeasures().get(0).getValue(), is(nullValue()));
        assertThat(tr2.getMeasures().get(0).getExpectedMax(), is(Double.POSITIVE_INFINITY));
        assertThat(tr2.getMeasures().get(0).getExpectedMin(), is(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void fetchTestRuns() throws Exception {
        stubFor(get(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(TestAutomation.TEST_RUNS_EP, "easyTravel", "")))
                .withQueryParam("extend", equalTo("runs"))
                .withQueryParam("category", equalTo("unit"))
                .willReturn(aResponse()
                        .withStatus(200).withBodyFile("fetchTestrunsResponse.json")));
        FetchTestRunsRequest request = new FetchTestRunsRequest("easyTravel");
        request.setExtend(FetchTestRunsRequest.Extension.TEST_RUNS);
        request.setCategoryFilter(TestCategory.UNIT);

        TestRuns testRuns = this.testAutomation.fetchTestRuns(request);
        assertThat(testRuns.getTestRuns().size(), is(3));
    }

    @Test
    public void finishTestRunShouldBeExecuted() throws Exception {
        stubFor(post(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(TestAutomation.FINISH_TEST_RUN_EP, "easyTravel", "078e961b-9e6e-44ec-ab12-ab0d31be93fc")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "  \"id\": \"078e961b-9e6e-44ec-ab12-ab0d31be93fc\",\n" +
                                "  \"category\": \"unit\",\n" +
                                "  \"versionBuild\": \"3\",\n" +
                                "  \"versionMajor\": \"1\",\n" +
                                "  \"versionMilestone\": \"m4\",\n" +
                                "  \"versionMinor\": \"2\",\n" +
                                "  \"versionRevision\": \"3\",\n" +
                                "  \"platform\": \"Windows\",\n" +
                                "  \"startTime\": \"2017-01-10T15:21:20.618+01:00\",\n" +
                                "  \"systemProfile\": \"easyTravel\",\n" +
                                "  \"marker\": \"marker\",\n" +
                                "  \"creationMode\": \"MANUAL\",\n" +
                                "  \"additionalMetaData\": {\n" +
                                "    \"somekey\": \"somevalue\"\n" +
                                "  },\n" +
                                "  \"numDegraded\": 0,\n" +
                                "  \"numFailed\": 0,\n" +
                                "  \"numImproved\": 0,\n" +
                                "  \"numInvalidated\": 0,\n" +
                                "  \"numPassed\": 6,\n" +
                                "  \"numVolatile\": 0,\n" +
                                "  \"finished\": true,\n" +
                                "  \"testResults\": [\n" +
                                "    {\n" +
                                "      \"name\": \"SumTest.sumWithoutAdding\",\n" +
                                "      \"status\": \"passed\",\n" +
                                "      \"exectime\": \"2017-01-10T15:21:22.737+01:00\",\n" +
                                "      \"package\": \"com.example.simple\",\n" +
                                "      \"platform\": \"Windows\",\n" +
                                "      \"measures\": [\n" +
                                "        {\n" +
                                "          \"name\": \"Failed Transaction Count\",\n" +
                                "          \"metricGroup\": \"Error Detection\",\n" +
                                "          \"value\": 0,\n" +
                                "          \"unit\": \"num\",\n" +
                                "          \"expectedMin\": 0,\n" +
                                "          \"expectedMax\": 0,\n" +
                                "          \"numFailingOrInvalidatedRuns\": 0,\n" +
                                "          \"numValidRuns\": 10,\n" +
                                "          \"numImprovedRuns\": 0,\n" +
                                "          \"numDegradedRuns\": 0,\n" +
                                "          \"violationPercentage\": 0\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"name\": \"StringMergeTest.doMergeFromSetter\",\n" +
                                "      \"status\": \"passed\",\n" +
                                "      \"exectime\": \"2017-01-10T15:21:22.736+01:00\",\n" +
                                "      \"package\": \"com.example.simple\",\n" +
                                "      \"platform\": \"Windows\",\n" +
                                "      \"measures\": [\n" +
                                "        {\n" +
                                "          \"name\": \"Failed Transaction Count\",\n" +
                                "          \"metricGroup\": \"Error Detection\",\n" +
                                "          \"value\": 0,\n" +
                                "          \"unit\": \"num\",\n" +
                                "          \"expectedMin\": 0,\n" +
                                "          \"expectedMax\": 0,\n" +
                                "          \"numFailingOrInvalidatedRuns\": 0,\n" +
                                "          \"numValidRuns\": 10,\n" +
                                "          \"numImprovedRuns\": 0,\n" +
                                "          \"numDegradedRuns\": 0,\n" +
                                "          \"violationPercentage\": 0\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}")));
        TestRun tr = this.testAutomation.finishTestRun("easyTravel", "078e961b-9e6e-44ec-ab12-ab0d31be93fc");
        assertThat(tr.isFinished(), is(true));
        assertThat(tr.getStartTime(), is(DateStringIso8601Adapter.getAsDate("2017-01-10T15:21:20.618+01:00")));
    }
}
