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

package com.dynatrace.sdk.server.memorydumps;

import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.memorydumps.models.*;
import com.dynatrace.sdk.server.sessions.models.SessionType;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MemoryDumpsTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private MemoryDumps memoryDumps = new MemoryDumps(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void getMemoryDump() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(MemoryDumps.MEMORY_DUMP_EP, "test", "test")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("memoryDumpResponse.xml")));
        MemoryDump dump = this.memoryDumps.getMemoryDump("test", "test");
        assertThat(dump.getSessionId(), is("JavaWorld/20130521074603_0.memdump"));
        assertThat(dump.getResourceId(), is("20130521074603_0"));
        assertThat(dump.getState(), is(JobState.FINISHED));
        assertThat(dump.getName(), is("Tue May 21 07:46:03 CEST 2013"));
        assertThat(dump.getDescription(), is("Dynatrace!"));
        assertThat(dump.isPostProcessed(), is(true));
        assertThat(dump.isSessionLocked(), is(true));
        assertThat(dump.isCapturePrimitives(), is(true));
        assertThat(dump.isCaptureStrings(), is(true));
        assertThat(dump.isDogc(), is(true));
        assertThat(dump.getStoredSessionType(), is(StoredSessionType.SIMPLE));
        assertThat(dump.getUsedMemory(), is(1788984L));
        assertThat(dump.getProcessMemory(), is(35418112L));
        assertThat(dump.getClasses(), is(909));
        assertThat(dump.getObjects(), is(14454));
        assertThat(dump.getAgentPattern(), notNullValue());
        AgentPattern ap = dump.getAgentPattern();
        assertThat(ap.getAgentName(), is("JavaWorld"));
        assertThat(ap.getHostname(), is("lnz123456"));
        assertThat(ap.getProcessId(), is(9508));
    }

    @Test
    public void getMemoryDumpJob() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(MemoryDumps.MEMORY_DUMP_JOBS_EP, "test", "test")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("memoryDumpJobResponse.xml")));
        MemoryDumpJob dump = this.memoryDumps.getMemoryDumpJob("test", "test");
        assertThat(dump.getId(), is("Memory Dump [5552484470037]"));
        assertThat(dump.getState(), is(JobState.FINISHED));
        assertThat(dump.isPostProcessed(), is(true));
        assertThat(dump.isSessionLocked(), is(true));
        assertThat(dump.isCapturePrimitives(), is(true));
        assertThat(dump.isCaptureStrings(), is(true));
        assertThat(dump.isDogc(), is(true));
        assertThat(dump.getDuration().getValue(), is(526));
        assertThat(dump.getDuration().getUnit(), is("MILLISECONDS"));
        assertThat(dump.getProgress().getValue(), is(100));
        assertThat(dump.getProgress().getUnit(), is("percent"));
        assertThat(dump.getAgentPattern(), notNullValue());
        AgentPattern ap = dump.getAgentPattern();
        assertThat(ap.getAgentName(), is("JavaWorld"));
        assertThat(ap.getHostname(), is("lnz124984d01"));
        assertThat(ap.getProcessId(), is(9508));
        assertThat(dump.getSessionReference(), notNullValue());
        SessionReference reference = dump.getSessionReference();
        assertThat(reference.getHref(), is("https://localhost:8021/rest/management/profiles/JavaWorld/memorydumps/20130521074603_0"));
        assertThat(reference.getSessionId(), is("JavaWorld/20130521074603_0.memdump"));
        assertThat(reference.getSessionType(), is(SessionType.STORED));
    }
}
