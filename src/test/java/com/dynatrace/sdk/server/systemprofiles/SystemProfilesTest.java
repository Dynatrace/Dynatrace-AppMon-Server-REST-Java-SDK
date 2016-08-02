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

import com.dynatrace.sdk.server.BasicServerConfiguration;
import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.systemprofiles.models.Profiles;
import com.dynatrace.sdk.server.systemprofiles.models.SystemProfile;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class SystemProfilesTest {
    @Rule
    public WireMockRule wireMock = new WireMockRule();
    private SystemProfiles systemProfiles = new SystemProfiles(new DynatraceClient(new BasicServerConfiguration("admin", "admin", false, "localhost", 8080, false, 2000)));

    @Test
    public void getSystemProfiles() throws Exception {
        stubFor(get(urlPathEqualTo(SystemProfiles.PROFILES_EP)).willReturn(aResponse().withBody(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
                        "<profiles href=\"https://localhost:8021/rest/management/profiles\">\n" +
                        "    <systemprofile isrecording=\"false\" id=\"dynaTrace Self-Monitoring\" href=\"https://localhost:8021/rest/management/profiles/dynaTrace%20Self-Monitoring\"/>\n" +
                        "    <systemprofile isrecording=\"true\" id=\"easyTravel\" href=\"http://localhost:8020/rest/management/profiles/easyTravel\"/>\n" +
                        "</profiles>"
        )));
        Profiles profiles = this.systemProfiles.getSystemProfiles();
        assertThat(profiles.getHref(), is("https://localhost:8021/rest/management/profiles"));
        assertThat(profiles.getProfiles().size(), is(2));
        SystemProfile profile = profiles.getProfiles().get(1);
        assertThat(profile.isRecording(), is(true));
        assertThat(profile.getId(), is("easyTravel"));
        assertThat(profile.getHref(), is("http://localhost:8020/rest/management/profiles/easyTravel"));
    }

    @Test
    public void activateProfileConfiguration() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(SystemProfiles.ACTIVATE_PROFILE_CONFIGURATION_EP, "profile", "configuration")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"true\"/>"
                        )));


        stubFor(get(urlPathEqualTo(String.format(SystemProfiles.ACTIVATE_PROFILE_CONFIGURATION_EP, "unknownprofile", "configuration")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"System profile 'unknownprofile' not found\"/>"
                        )));

        stubFor(get(urlPathEqualTo(String.format(SystemProfiles.ACTIVATE_PROFILE_CONFIGURATION_EP, "profile", "unknownconfiguration")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"System profile configuration 'unknownconfiguration' not found\"/>"
                        )));

        boolean result = this.systemProfiles.activateProfileConfiguration("profile", "configuration");
        assertThat(result, is(true));

        try {
            this.systemProfiles.activateProfileConfiguration("unknownprofile", "configuration");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getMessage(), new StringContains("unknownprofile"));
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
        stubFor(get(urlPathEqualTo(String.format(SystemProfiles.PROFILE_ENABLE_EP, "profile")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"true\"/>"
                        )));


        stubFor(get(urlPathEqualTo(String.format(SystemProfiles.PROFILE_ENABLE_EP, "unknownprofile")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"System profile 'unknownprofile' not found\"/>"
                        )));

        Boolean result = this.systemProfiles.enableProfile("profile");
        assertThat(result, is(true));

        try {
            this.systemProfiles.enableProfile("unknownprofile");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getMessage(), new StringContains("unknownprofile"));
        }
    }

    @Test
    public void disableProfile() throws Exception {
        stubFor(get(urlPathEqualTo(String.format(SystemProfiles.PROFILE_DISABLE_EP, "profile")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result value=\"true\"/>"
                        )));


        stubFor(get(urlPathEqualTo(String.format(SystemProfiles.PROFILE_DISABLE_EP, "unknownprofile")))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error reason=\"System profile 'unknownprofile' not found\"/>"
                        )));

        Boolean result = this.systemProfiles.disableProfile("profile");
        assertThat(result, is(true));

        try {
            this.systemProfiles.disableProfile("unknownprofile");
            fail("Exception was expected to be thrown");
        } catch (ServerResponseException ex) {
            assertThat(ex.getMessage(), new StringContains("unknownprofile"));
        }
    }
}
