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

package com.dynatrace.sdk.server.servermanagement;

import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class ServerManagementTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private ServerManagement serverManagement = new ServerManagement(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void restart_successInResponse_resultIsTrue() throws Exception {
        stubFor(post(urlPathEqualTo(Service.API_VER_URI_PREFIX + ServerManagement.SERVER_RESTART_EP))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"status\": \"success\" }")));

        Boolean result = this.serverManagement.restart();
        assertThat(result, is(true));
    }

    @Test
    public void restart_failInResponse_resultIsFalse() throws Exception {
        stubFor(post(urlPathEqualTo(Service.API_VER_URI_PREFIX + ServerManagement.SERVER_RESTART_EP))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"status\": \"fail\" }")));

        Boolean result = this.serverManagement.restart();
        assertThat(result, is(false));
    }

    @Test
    public void restart_invalidHost_exceptionThrown() throws ServerResponseException {

        stubFor(post(urlPathEqualTo(Service.API_VER_URI_PREFIX + ServerManagement.SERVER_RESTART_EP))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"status\": \"success\" }")));

        ServerManagement invalidServerManagement = new ServerManagement(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost__invalid", 8080, false, 2000)));

        try {
            invalidServerManagement.restart();
            fail("Exception was expected to be thrown");
        } catch (ServerConnectionException ex) {
            assertThat(ex.getMessage(), new StringContains("localhost__invalid"));
        }

    }

    @Test
    public void shutdown() throws Exception {
        stubFor(post(urlPathEqualTo(Service.API_VER_URI_PREFIX + ServerManagement.SERVER_SHUTDOWN_EP))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"status\": \"success\" }")));

        Boolean result = this.serverManagement.shutdown();
        assertThat(result, is(true));

        ServerManagement invalidServerManagement = new ServerManagement(
        		new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost__invalid", 8080, false, 2000)));

        try {
            invalidServerManagement.shutdown();
            fail("Exception was expected to be thrown");
        } catch (ServerConnectionException ex) {
            assertThat(ex.getMessage(), new StringContains("localhost__invalid"));
        }
    }
}
