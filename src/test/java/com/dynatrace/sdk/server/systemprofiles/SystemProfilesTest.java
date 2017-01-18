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

package com.dynatrace.sdk.server.systemprofiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.systemprofiles.models.Profiles;
import com.dynatrace.sdk.server.systemprofiles.models.SystemProfile;
import com.dynatrace.sdk.server.systemprofiles.models.SystemProfileMetadata;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class SystemProfilesTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private SystemProfiles systemProfiles = new SystemProfiles(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void getSystemProfileMetadata() throws Exception {
        stubFor(get(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.PROFILES_EP, "test"))).willReturn(aResponse().withBody(
        		"{\n" +
        		"  \"id\": \"easyTravel\",\n" +
        		"  \"description\": \"Profile for the easyTravel demo application.\",\n" +
        		"  \"enabled\": true,\n" +
        		"  \"isrecording\": true\n" +
        		"}")));
        SystemProfileMetadata meta = this.systemProfiles.getSystemProfileMetadata("test");
        assertThat(meta.getEnabled(), is(true));
        assertThat(meta.getId(), is("easyTravel"));
        assertThat(meta.getRecording(), is(true));
        assertThat(meta.getDescription(), is("Profile for the easyTravel demo application."));
    }

    @Test
    public void getSystemProfiles() throws Exception {
        stubFor(get(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.PROFILES_EP, ""))).willReturn(aResponse().withBody(
                "{\n" +
                "  \"systemprofiles\": [\n" +
                "    {\n" +
                "      \"id\": \"dynaTrace Self-Monitoring\",\n" +
                "      \"isrecording\": false,\n" +
                "      \"href\": \"https://localhost:8021/api/v2/profiles/dynaTrace%20Self-Monitoring\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"AjaxWorld\",\n" +
                "      \"isrecording\": true,\n" +
                "      \"href\": \"https://localhost:8021/api/v2/profiles/AjaxWorld\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"AjaxWorldTests\",\n" +
                "      \"isrecording\": false,\n" +
                "      \"href\": \"https://localhost:8021/api/v2/profiles/AjaxWorldTests\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        )));

        Profiles profiles = this.systemProfiles.getSystemProfiles();
        assertThat(profiles.getProfiles().size(), is(3));
        SystemProfile profile = profiles.getProfiles().get(1);
        assertThat(profile.isRecording(), is(true));
        assertThat(profile.getId(), is("AjaxWorld"));
        assertThat(profile.getHref(), is("https://localhost:8021/api/v2/profiles/AjaxWorld"));

    }

    @Test
    public void activateProfileConfiguration() throws Exception {
        stubFor(put(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.ACTIVATE_PROFILE_CONFIGURATION_EP, "profile", "configuration")))
                .willReturn(aResponse().withStatus(204)));


        stubFor(put(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.ACTIVATE_PROFILE_CONFIGURATION_EP, "unknownprofile", "configuration")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(
                                "{\n" +
                                "  \"code\": 404,\n" +
                                "  \"message\": \"System Profile 'unknownprofile' not found\"\n" +
                                "}"
                        )));

        stubFor(put(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.ACTIVATE_PROFILE_CONFIGURATION_EP, "profile", "unknownconfiguration")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(
                                "{\n" +
                                "  \"code\": 404,\n" +
                                "  \"message\": \"System Profile configuration 'unknownconfiguration' not found for System Profile 'profile'\"\n" +
                                "}"
                        )));

        // no exception
        this.systemProfiles.activateProfileConfiguration("profile", "configuration");

        try {
            this.systemProfiles.activateProfileConfiguration("unknownprofile", "configuration");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getMessage(), new StringContains("System Profile 'unknownprofile' not found"));
        }

        try {
            this.systemProfiles.activateProfileConfiguration("profile", "unknownconfiguration");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getMessage(), new StringContains("unknownconfiguration"));
        }
    }

    @Test
    public void enableProfile() throws Exception {

        stubFor(put(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.PROFILE_STATUS_EP, "profile")))
                .willReturn(aResponse()
                        .withStatus(204)));


        stubFor(put(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.PROFILE_STATUS_EP, "unknownprofile")))
                .willReturn(aResponse()
                        .withStatus(404).withBody("{\n" +
                        		"  \"code\": 404,\n" +
                        		"  \"message\": \"System Profile 'easyTravel2' not found\"\n" +
                        		"}")));

        // no exception
        this.systemProfiles.enableProfile("profile");

        try {
            this.systemProfiles.enableProfile("unknownprofile");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getMessage(), new StringContains("System Profile 'easyTravel2' not found"));
        }
    }

    @Test
    public void disableProfile() throws Exception {

        stubFor(put(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.PROFILE_STATUS_EP, "profile")))
                .willReturn(aResponse()
                        .withStatus(204)));


        stubFor(put(urlPathEqualTo(Service.API_VER_URI_PREFIX + String.format(SystemProfiles.PROFILE_STATUS_EP, "unknownprofile")))
                .willReturn(aResponse()
                        .withStatus(404).withBody("{\n" +
                        		"  \"code\": 404,\n" +
                        		"  \"message\": \"System Profile 'easyTravel2' not found\"\n" +
                        		"}")));

        // no exception
        this.systemProfiles.disableProfile("profile");

        try {
            this.systemProfiles.disableProfile("unknownprofile");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getMessage(), new StringContains("System Profile 'easyTravel2' not found"));
        }

    }
}
