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

package com.dynatrace.sdk.server.resourcedumps;

import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.resourcedumps.models.CreateThreadDumpRequest;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class ResourceDumpsTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private ResourceDumps resourceDumps = new ResourceDumps(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void createThreadDump() throws Exception {
        stubFor(post(urlPathEqualTo(String.format(ResourceDumps.CREATE_THREAD_DUMP_EP, "easyTravel")))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withRequestBody(containing("agentName=easyTravelBackend"))
                .withRequestBody(containing("hostName=GRABS"))
                .withRequestBody(containing("processId=6612"))
                .withRequestBody(containing("isSessionLocked=false"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"Thread Dump [1250108367765]\"/>")
                )
        );

        CreateThreadDumpRequest request = new CreateThreadDumpRequest("easyTravel", "easyTravelBackend", "GRABS", 6612);
        request.setSessionLocked(false);

        String scheduleId = this.resourceDumps.createThreadDump(request);
        assertThat(scheduleId, is("Thread Dump [1250108367765]"));
    }

    @Test
    public void createThreadDumpAgentNotFound() throws Exception {
        stubFor(post(urlPathEqualTo(String.format(ResourceDumps.CREATE_THREAD_DUMP_EP, "easyTravel")))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withRequestBody(containing("agentName=easyTravelBackendNotFound"))
                .withRequestBody(containing("hostName=GRABS"))
                .withRequestBody(containing("processId=6612"))
                .withRequestBody(containing("isSessionLocked=false"))
                .willReturn(
                        aResponse()
                                .withStatus(404)
                                .withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"No agent for pattern 'easyTravelBackendNotFound@GRABS:6612' in system profile 'easyTravel' found\"/>")
                )
        );

        CreateThreadDumpRequest request = new CreateThreadDumpRequest("easyTravel", "easyTravelBackendNotFound", "GRABS", 6612);
        request.setSessionLocked(false);

        try {
            String scheduleId = this.resourceDumps.createThreadDump(request);
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException e) {
            assertThat(e.getMessage(), new StringContains("easyTravelBackendNotFound"));
        }
    }
}
