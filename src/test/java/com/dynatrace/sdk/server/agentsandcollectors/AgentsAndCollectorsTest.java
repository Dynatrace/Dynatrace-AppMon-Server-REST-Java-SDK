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

package com.dynatrace.sdk.server.agentsandcollectors;

import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.agentsandcollectors.models.*;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class AgentsAndCollectorsTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private AgentsAndCollectors agentsAndCollectors = new AgentsAndCollectors(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void hotSensorPlacement() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(AgentsAndCollectors.HOT_SENSOR_PLACEMENT_EP, 1234)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"false\"/>")));

        stubFor(get(urlPathEqualTo(String.format(AgentsAndCollectors.HOT_SENSOR_PLACEMENT_EP, 5678)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"true\"/>")));

        assertThat(this.agentsAndCollectors.placeHotSensor(1234), is(false));
        assertThat(this.agentsAndCollectors.placeHotSensor(5678), is(true));
    }

    @Test
    public void fetchCollector() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(AgentsAndCollectors.COLLECTORS_EP, "Embedded dynaTrace Collector@GRABS")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
                                        "<collectorinformation href=\"https://localhost:8021/rest/management/collectors/Embedded%20dynaTrace%20Collector@GRABS\">\n" +
                                        "  <connected>true</connected>\n" +
                                        "  <embedded>true</embedded>\n" +
                                        "  <local>true</local>\n" +
                                        "  <host>GRABS</host>\n" +
                                        "  <name>Embedded dynaTrace Collector</name>\n" +
                                        "  <version>6.3.4.1034</version>\n" +
                                        "</collectorinformation>"
                        )));

        stubFor(get(urlMatching(String.format(AgentsAndCollectors.COLLECTORS_EP, "1234")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"Collector with symbolic name '1234' not found.\"/>"
                        )));


        CollectorInformation ci = this.agentsAndCollectors.fetchCollector("Embedded dynaTrace Collector@GRABS");
        assertThat(ci.getHref(), is("https://localhost:8021/rest/management/collectors/Embedded%20dynaTrace%20Collector@GRABS"));
        assertThat(ci.getName(), is("Embedded dynaTrace Collector"));
        assertThat(ci.getHost(), is("GRABS"));
        assertThat(ci.getVersion(), is("6.3.4.1034"));
        assertThat(ci.isConnected(), is(true));
        assertThat(ci.isEmbedded(), is(true));
        assertThat(ci.isLocal(), is(true));

        try {
            this.agentsAndCollectors.fetchCollector("1234");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getStatusCode(), is(404));
            assertThat(ex.getMessage(), new StringContains("1234"));
        }
    }

    @Test
    public void fetchCollectors() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(AgentsAndCollectors.COLLECTORS_EP, "")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
                                "<collectors href=\"https://localhost:8021/rest/management/collectors\">\n" +
                                "  <collectorinformation href=\"https://localhost:8021/rest/management/collectors/Embedded%20dynaTrace%20Collector@GRABS\">\n" +
                                "    <connected>true</connected>\n" +
                                "    <embedded>true</embedded>\n" +
                                "\t<local>true</local>\n" +
                                "    <host>GRABS</host>\n" +
                                "    <name>Embedded dynaTrace Collector</name>\n" +
                                "\t<version>6.3.4.1034</version>\n" +
                                "  </collectorinformation>\n" +
                                "  <collectorinformation href=\"https://localhost:8021/rest/management/collectors/Embedded%20dynaTrace%20Collector%202@GRABS%202\">\n" +
                                "    <connected>true</connected>\n" +
                                "    <embedded>true</embedded>\n" +
                                "\t<local>true</local>\n" +
                                "    <host>GRABS 2</host>\n" +
                                "    <name>Embedded dynaTrace Collector 2</name>\n" +
                                "\t<version>6.3.4.1034</version>\n" +
                                "  </collectorinformation>\n" +
                                "</collectors>")));

        Collectors c = this.agentsAndCollectors.fetchCollectors();
        assertThat(c.getCollectors().size(), is(2));

        CollectorInformation ci = c.getCollectors().get(0);
        assertThat(ci.getHref(), is("https://localhost:8021/rest/management/collectors/Embedded%20dynaTrace%20Collector@GRABS"));
        assertThat(ci.getName(), is("Embedded dynaTrace Collector"));
        assertThat(ci.getHost(), is("GRABS"));
        assertThat(ci.getVersion(), is("6.3.4.1034"));
        assertThat(ci.isConnected(), is(true));
        assertThat(ci.isEmbedded(), is(true));
        assertThat(ci.isLocal(), is(true));

        ci = c.getCollectors().get(1);
        assertThat(ci.getHref(), is("https://localhost:8021/rest/management/collectors/Embedded%20dynaTrace%20Collector%202@GRABS%202"));
        assertThat(ci.getName(), is("Embedded dynaTrace Collector 2"));
        assertThat(ci.getHost(), is("GRABS 2"));
        assertThat(ci.getVersion(), is("6.3.4.1034"));
        assertThat(ci.isConnected(), is(true));
        assertThat(ci.isEmbedded(), is(true));
        assertThat(ci.isLocal(), is(true));
    }

    @Test
    public void restartCollector() throws Exception {
        stubFor(post(urlPathEqualTo(String.format(AgentsAndCollectors.COLLECTOR_RESTART_EP, "Embedded dynaTrace Collector")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"true\"/>"
                        )));

        stubFor(post(urlPathEqualTo(String.format(AgentsAndCollectors.COLLECTOR_RESTART_EP, "Another Collector")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"No collector 'Another Collector' found\"/>")));

        Boolean result = this.agentsAndCollectors.restartCollector("Embedded dynaTrace Collector");
        assertThat(result, is(true));

        try {
            this.agentsAndCollectors.restartCollector("Another Collector");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getStatusCode(), is(404));
            assertThat(ex.getMessage(), new StringContains("Another Collector"));
        }
    }

    @Test
    public void shutdownCollector() throws Exception {
        stubFor(post(urlPathEqualTo(String.format(AgentsAndCollectors.COLLECTOR_SHUTDOWN_EP, "Embedded dynaTrace Collector")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"true\"/>"
                        )));

        stubFor(post(urlPathEqualTo(String.format(AgentsAndCollectors.COLLECTOR_SHUTDOWN_EP, "Another Collector")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"No collector 'Another Collector' found\"/>")));

        Boolean result = this.agentsAndCollectors.shutdownCollector("Embedded dynaTrace Collector");
        assertThat(result, is(true));

        try {
            this.agentsAndCollectors.shutdownCollector("Another Collector");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getStatusCode(), is(404));
            assertThat(ex.getMessage(), new StringContains("Another Collector"));
        }
    }

    @Test
    public void fetchAgents() throws Exception {
        stubFor(get(urlPathEqualTo(AgentsAndCollectors.AGENTS_EP))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("fetchAgentsResponse.xml")));

        Agents agents = this.agentsAndCollectors.fetchAgents();
        assertThat(agents.getAgents().size(), is(2));

        /* general agent information */
        AgentInformation ai = agents.getAgents().get(0);

        assertThat(ai.isAgentConfigured(), is(true));
        assertThat(ai.getAgentGroupLabel(), is("Payment Backend (.NET)"));
        assertThat(ai.getAgentGroupId(), is("Payment Backend (.NET)"));
        assertThat(ai.getAgentId(), is(945367843));
        assertThat(ai.getAgentInstanceName(), is("dotNetBackend_easyTravel@tag00944657691:6036"));
        assertThat(ai.getAgentMappingId(), is("dotNETBackend_easyTravel"));
        assertThat(ai.isCapture(), is(true));
        assertThat(ai.isCaptureCPUTimes(), is(false));
        assertThat(ai.getClassLoadCount(), is(0));
        assertThat(ai.getCollectorName(), is("dynaTrace Collector"));
        assertThat(ai.getConfigurationName(), is("Default"));
        assertThat(ai.getConfigurationId(), is("Default"));
        assertThat(ai.isConnected(), is(false));
        assertThat(ai.getSkippedEvents(), is(-1L));
        assertThat(ai.getSkippedPurePaths(), is(-1L));
        assertThat(ai.getEventCount(), is(-1L));
        assertThat(ai.isFromCmdb(), is(false));
        assertThat(ai.getHost(), is("tag00944657691"));
        assertThat(ai.isHotUpdateCritical(), is(true));
        assertThat(ai.isHotUpdateable(), is(false));
        assertThat(ai.getInstanceName(), is("dotNetBackend_easyTravel@tag00944657691:6036"));
        assertThat(ai.isvLicenseSupported(), is(false));
        assertThat(ai.isLicenseOk(), is(true));
        assertThat(ai.getName(), is("dotNetBackend_easyTravel"));
        assertThat(ai.getProcessId(), is(6036));
        assertThat(ai.getProcessorCount(), is(4));
        assertThat(ai.isRequired(), is(false));
        assertThat(ai.getSkippedEvents(), is(-1L));
        assertThat(ai.getSkippedPurePaths(), is(-1L));
        assertThat(ai.getSourceGroupId(), is("Payment Backend (.NET)"));
        assertThat(ai.getStartupTimeUTC(), is(1469613777527L));
        assertThat(ai.isHotSensorPlacementSupported(), is(false));
        assertThat(ai.getSyncThreshold(), is(0.0));
        assertThat(ai.getSystemProfile(), is("easyTravel"));
        assertThat(ai.getSystemProfileName(), is("easyTravel"));
        assertThat(ai.getTechnologyType(), is(".NET"));
        assertThat(ai.getTechnologyTypeId(), is((byte) 2));
        assertThat(ai.getTimestamp(), is(1469613777057L));
        assertThat(ai.getTotalClassLoadCount(), is(0));
        assertThat(ai.getTotalCpuTime(), is(0.0));
        assertThat(ai.getTotalExecutionTime(), is(0.0));
        assertThat(ai.getTotalPurePathCount(), is(0L));
        assertThat(ai.getVirtualTimeUTC(), is(1469613777527L));
        assertThat(ai.getVmVendor(), is("Microsoft Corporation"));

        /* collector information */
        CollectorInformation ci = ai.getCollectorinformation();

        assertThat(ci.isConnected(), is(true));
        assertThat(ci.isEmbedded(), is(false));
        assertThat(ci.isLocal(), new IsNull<Boolean>());
        assertThat(ci.getHost(), is("tag00944657691.clients.dynatrace.org"));
        assertThat(ci.getName(), is("dynaTrace Collector"));
        assertThat(ci.getVersion(), is("6.3.4.1034"));

        /* agent properties */
        AgentPropertiesInformation api = ai.getAgentProperties();
        assertThat(api.getAgentBootstrapVersion(), is("6.3.0.1305"));
        assertThat(api.getAgentHost(), is("TAG00944657691"));
        assertThat(api.getAgentHostAddress(), is("127.0.0.1"));
        assertThat(api.getAgentId(), is("38592b23"));
        assertThat(api.getAgentPlatform(), is("Windows"));
        assertThat(api.getAgentVersion(), is("6.3.4.1034"));
        assertThat(api.getApplicationServerDetected(), is("false"));
        assertThat(api.getApplicationServerVersionDetected(), is("false"));
        assertThat(api.getCellNameDetected(), is("false"));
        assertThat(api.getClrVendor(), is("Microsoft Corporation"));
        assertThat(api.getClrVersion(), is("v2.0.50727"));
        assertThat(api.getCommandLine(), is("\"C:\\Program Files (x86)\\dynaTrace\\easyTravel\\dotNET\\cassini20\\UltiDevCassinWebServer2.exe\" /run \"C:\\Program Files (x86)\\dynaTrace\\easyTravel\\dotNET\\dotNetPaymentBackend\" WebService\\PaymentService.asmx 9010 nobrowser -propertyfile \"C:\\Users\\some.user\\.dynaTrace\\easyTravel 2.0.0\\easyTravel\\tmp\\easyTravelConfig5534948657307800136.properties\""));
        assertThat(api.getCommandLineMayBeTruncated(), is("true"));
        assertThat(api.getHasCommandLine(), is("true"));
        assertThat(api.getHasWorkingDirectory(), is("true"));
        assertThat(api.getHotSensorPlaceable(), is("false"));
        assertThat(api.getInstrumentationState(), is("Unknown (-1)"));
        assertThat(api.getLogFileLocation(), is("c:/program files/dynatrace/dynatrace 6.3/log/dt_dotNetBackend_easyTravel_6036.0.log"));
        assertThat(api.getMaximumMemory(), is("8471023616"));
        assertThat(api.getOperatingSystem(), is("Windows 10"));
        assertThat(api.getOsArchitecture(), is("x64"));
        assertThat(api.getOsEdition(), is("Standard"));
        assertThat(api.getOsHyperVFriendly(), is("Unknown"));
        assertThat(api.getOsProductId(), is("Unknown"));
        assertThat(api.getOsVersion(), is("10.0.10586"));
        assertThat(api.getProcessors(), is("4"));
        assertThat(api.getRuntimeVersion(), is("CP852"));
        assertThat(api.getStartDate(), is("Wed Jul 27 12:02:57 CEST 2016"));
        assertThat(api.getStartUp(), is("248916954115260"));

        /* second agent */
        ai = agents.getAgents().get(1);

        assertThat(ai.getAgentGroupLabel(), is("CreditCardAuthorization (C++)"));
    }
}
