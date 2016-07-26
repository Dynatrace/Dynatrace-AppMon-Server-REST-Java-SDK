package com.dynatrace.server.sdk.testautomation;

import com.dynatrace.server.sdk.BasicServerConfiguration;
import com.dynatrace.server.sdk.DynatraceClient;
import com.dynatrace.server.sdk.testautomation.models.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestAutomationTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private TestAutomation testAutomation = new TestAutomation(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void createTestRun() throws Exception {
        stubFor(post(urlPathEqualTo(String.format(TestAutomation.TEST_RUNS_EP, "Test", "")))
                .withRequestBody(equalToXml("<testRun systemProfile=\"Test\" category=\"performance\" versionBuild=\"Build\"/>"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testRun category=\"performance\" versionBuild=\"Build\" platform=\"\" startTime=\"1469452303746\" id=\"6c0e95b3-e51b-411f-bb49-da85e8b36261\" numPassed=\"0\" numFailed=\"0\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"Test\" creationMode=\"MANUAL\" href=\"https://localhost:8021/rest/management/profiles/Jenkins/testruns/6c0e95b3-e51b-411f-bb49-da85e8b36261.xml\"/>")));
        CreateTestRunRequest request = new CreateTestRunRequest("Test", "Build");
        request.setCategory(TestCategory.PERFORMANCE);
        TestRun tr = this.testAutomation.createTestRun(request);
        assertThat(tr.getCategory(), is(TestCategory.PERFORMANCE));
        assertThat(tr.getVersionBuild(), is("Build"));
        assertThat(tr.getStartTime(), is(1469452303746L));
        assertThat(tr.getId(), is("6c0e95b3-e51b-411f-bb49-da85e8b36261"));
        assertThat(tr.getSystemProfile(), is("Test"));
        assertThat(tr.getCreationMode(), is(CreationMode.MANUAL));
        assertThat(tr.getHref(), is("https://localhost:8021/rest/management/profiles/Jenkins/testruns/6c0e95b3-e51b-411f-bb49-da85e8b36261.xml"));
    }

    @Test
    public void fetchTestRun() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(TestAutomation.TEST_RUNS_EP, "Test", "078e961b-9e6e-44ec-ab12-ab0d31be93fc")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<testRun category=\"unit\" versionBuild=\"13:42:05\" versionMajor=\"2016\" versionMinor=\"7\" versionRevision=\"2\" platform=\"Linux 4.4.13-1-MANJARO x86_64\" startTime=\"1469446925361\" id=\"078e961b-9e6e-44ec-ab12-ab0d31be93fc\" numPassed=\"3\" numFailed=\"1\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"Test\" creationMode=\"MANUAL\">\n" +
                                "  <testResult name=\"ITDaoTest.testRemoveDestinations\" status=\"passed\" exectime=\"1469446929087\" package=\"com.compuware.apm.samples.simplewebapp\" platform=\"Linux 4.4.13-1-MANJARO x86_64\">\n" +
                                "    <measure name=\"Count\" metricGroup=\"Exceptions\" value=\"2.0\" unit=\"num\" expectedMin=\"2.0\" expectedMax=\"2.0\" numFailingOrInvalidatedRuns=\"0\" numValidRuns=\"10\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "    <measure name=\"Failed Transaction Count\" metricGroup=\"Error Detection\" value=\"0.0\" unit=\"num\" expectedMin=\"0.0\" expectedMax=\"0.0\" numFailingOrInvalidatedRuns=\"0\" numValidRuns=\"10\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "    <measure name=\"DB Count\" metricGroup=\"Database\" value=\"4.0\" unit=\"num\" expectedMin=\"4.0\" expectedMax=\"4.0\" numFailingOrInvalidatedRuns=\"0\" numValidRuns=\"10\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "  </testResult>\n" +
                                "  <testResult name=\"ITDaoTest.testNotExistingDestination\" status=\"passed\" exectime=\"1469446930433\" package=\"com.compuware.apm.samples.simplewebapp\" platform=\"Linux 4.4.13-1-MANJARO x86_64\">\n" +
                                "    <measure name=\"Failed Transaction Count\" metricGroup=\"Error Detection\" value=\"0.0\" unit=\"num\" expectedMin=\"0.0\" expectedMax=\"0.0\" numFailingOrInvalidatedRuns=\"0\" numValidRuns=\"10\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "    <measure name=\"DB Count\" metricGroup=\"Database\" value=\"1.0\" unit=\"num\" expectedMin=\"1.0\" expectedMax=\"1.0\" numFailingOrInvalidatedRuns=\"0\" numValidRuns=\"10\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "  </testResult>\n" +
                                "  <testResult name=\"ITDaoTest.testGetAllDestinations\" status=\"failed\" exectime=\"1469446929383\" package=\"com.compuware.apm.samples.simplewebapp\" platform=\"Linux 4.4.13-1-MANJARO x86_64\">\n" +
                                "    <measure name=\"Count\" metricGroup=\"Exceptions\" value=\"1.0\" unit=\"num\" numFailingOrInvalidatedRuns=\"1\" numValidRuns=\"0\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "    <measure name=\"Failed Transaction Count\" metricGroup=\"Error Detection\" value=\"-INF\" unit=\"num\" numFailingOrInvalidatedRuns=\"1\" numValidRuns=\"0\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "    <measure name=\"DB Count\" metricGroup=\"Database\" value=\"INF\" unit=\"num\" numFailingOrInvalidatedRuns=\"1\" numValidRuns=\"0\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "  </testResult>\n" +
                                "  <testResult name=\"ITDaoTest.testGetDestination\" status=\"passed\" exectime=\"1469446930466\" package=\"com.compuware.apm.samples.simplewebapp\" platform=\"Linux 4.4.13-1-MANJARO x86_64\">\n" +
                                "    <measure name=\"Failed Transaction Count\" metricGroup=\"Error Detection\" value=\"0.0\" unit=\"num\" expectedMin=\"0.0\" expectedMax=\"0.0\" numFailingOrInvalidatedRuns=\"0\" numValidRuns=\"10\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "    <measure name=\"DB Count\" metricGroup=\"Database\" value=\"1.0\" unit=\"num\" expectedMin=\"1.0\" expectedMax=\"1.0\" numFailingOrInvalidatedRuns=\"0\" numValidRuns=\"10\" numImprovedRuns=\"0\" numDegradedRuns=\"0\" violationPercentage=\"0.0\"/>\n" +
                                "  </testResult>\n" +
                                "</testRun>")));
        TestRun tr = this.testAutomation.fetchTestRun("Test", "078e961b-9e6e-44ec-ab12-ab0d31be93fc");
        assertThat(tr.getId(), is("078e961b-9e6e-44ec-ab12-ab0d31be93fc"));
        assertThat(tr.getNumPassed(), is(3));
        assertThat(tr.getNumFailed(), is(1));
        assertThat(tr.getSystemProfile(), is("Test"));
        assertThat(tr.getCreationMode(), is(CreationMode.MANUAL));
        assertThat(tr.getCategory(), is(TestCategory.UNIT));
        assertThat(tr.getVersionBuild(), is("13:42:05"));
        assertThat(tr.getVersionMajor(), is("2016"));
        assertThat(tr.getVersionMinor(), is("7"));
        assertThat(tr.getVersionRevision(), is("2"));
        assertThat(tr.getPlatform(), is("Linux 4.4.13-1-MANJARO x86_64"));
        assertThat(tr.getStartTime(), is(1469446925361L));
        assertThat(tr.getTestResults().size(), is(4));

        //Test a single test result unmarshalling
        TestResult result = tr.getTestResults().get(2);
        assertThat(result.getName(), is("ITDaoTest.testGetAllDestinations"));
        assertThat(result.getStatus(), is(TestStatus.FAILED));
        assertThat(result.getExecutionTime(), is(1469446929383L));
        assertThat(result.getPackageName(), is("com.compuware.apm.samples.simplewebapp"));
        assertThat(result.getPlatform(), is("Linux 4.4.13-1-MANJARO x86_64"));
        assertThat(result.getMeasures().size(), is(3));

        //Test it's measures
        TestMeasure measure = result.getMeasures().get(0);
        assertThat(measure.getName(), is("Count"));
        assertThat(measure.getMetricGroup(), is("Exceptions"));
        assertThat(measure.getValue(), is(1.0));
        //unit="num" numFailingOrInvalidatedRuns="1" numValidRuns="0" numImprovedRuns="0" numDegradedRuns="0" violationPercentage="0.0"/>
        assertThat(measure.getUnit(), is("num"));
        assertThat(measure.getViolationPercentage(), is(0.0));
        assertThat(measure.getFailingOrInvalidatedRunsCount(), is(1));
        assertThat(measure.getValidRunsCount(), is(0));
        assertThat(measure.getImprovedRunsCount(), is(0));
        assertThat(measure.getDegradedRunsCount(), is(0));

        //check edge case
        assertThat(result.getMeasures().get(1).getValue(), is(Double.NEGATIVE_INFINITY));
        assertThat(result.getMeasures().get(2).getValue(), is(Double.POSITIVE_INFINITY));
    }

    @Test
    public void fetchTestRuns() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(TestAutomation.TEST_RUNS_EP, "IntelliJ", ""))).withQueryParam("extend", equalTo("testRuns")).withQueryParam("category", equalTo("unit"))
                .willReturn(aResponse()
                        .withStatus(200).withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<testRuns>\n" +
                                "  <testRun category=\"unit\" versionBuild=\"13:42:05\" versionMajor=\"2016\" versionMinor=\"7\" versionRevision=\"2\" platform=\"Linux 4.4.13-1-MANJARO x86_64\" startTime=\"1469446925361\" id=\"078e961b-9e6e-44ec-ab12-ab0d31be93fc\" numPassed=\"3\" numFailed=\"1\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"IntelliJ\" creationMode=\"MANUAL\" href=\"https://localhost:8021/rest/management/profiles/IntelliJ/testRuns/078e961b-9e6e-44ec-ab12-ab0d31be93fc.xml\"/>\n" +
                                "  <testRun category=\"unit\" versionBuild=\"15:52:11\" versionMajor=\"2016\" versionMinor=\"7\" versionRevision=\"5\" platform=\"Linux 4.4.13-1-MANJARO x86_64\" startTime=\"1469109132002\" id=\"3283730b-364b-419a-adfd-c5653c62c789\" numPassed=\"4\" numFailed=\"0\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"IntelliJ\" creationMode=\"MANUAL\" href=\"https://localhost:8021/rest/management/profiles/IntelliJ/testRuns/3283730b-364b-419a-adfd-c5653c62c789.xml\"/>\n" +
                                "  <testRun category=\"unit\" versionBuild=\"15:24:40\" versionMajor=\"2016\" versionMinor=\"7\" versionRevision=\"5\" platform=\"Linux 4.4.13-1-MANJARO x86_64\" startTime=\"1469107480773\" id=\"966d9418-9e80-405b-bb2f-97220d684732\" numPassed=\"4\" numFailed=\"0\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"IntelliJ\" creationMode=\"MANUAL\" href=\"https://localhost:8021/rest/management/profiles/IntelliJ/testRuns/966d9418-9e80-405b-bb2f-97220d684732.xml\"/>\n" +
                                "  <testRun category=\"unit\" versionBuild=\"08:29:53\" versionMajor=\"2016\" versionMinor=\"7\" versionRevision=\"5\" platform=\"Linux 4.4.13-1-MANJARO x86_64\" startTime=\"1469082594103\" id=\"2b6b7482-a704-433c-b84f-5b7bb1509c45\" numPassed=\"3\" numFailed=\"1\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"IntelliJ\" creationMode=\"MANUAL\" href=\"https://localhost:8021/rest/management/profiles/IntelliJ/testRuns/2b6b7482-a704-433c-b84f-5b7bb1509c45.xml\"/>\n" +
                                "  <testRun category=\"unit\" versionBuild=\"13:08:29\" versionMajor=\"2016\" versionMinor=\"7\" versionRevision=\"4\" platform=\"Linux 4.4.13-1-MANJARO x86_64\" startTime=\"1469012910062\" id=\"be72fc18-9f15-4ce5-b1af-1ed7cdd284f6\" numPassed=\"3\" numFailed=\"1\" numVolatile=\"0\" numImproved=\"0\" numDegraded=\"0\" numInvalidated=\"0\" systemProfile=\"IntelliJ\" creationMode=\"MANUAL\" href=\"https://localhost:8021/rest/management/profiles/IntelliJ/testRuns/be72fc18-9f15-4ce5-b1af-1ed7cdd284f6.xml\"/>\n" +
                                "</testRuns>")));
        FetchTestRunsRequest request = new FetchTestRunsRequest("IntelliJ");
        request.setExtend(FetchTestRunsRequest.Extension.TEST_RUNS);
        request.setCategoryFilter(TestCategory.UNIT);
        TestRuns testRuns = this.testAutomation.fetchTestRuns(request);
        assertThat(testRuns.getTestRuns().size(), is(5));
    }
}
