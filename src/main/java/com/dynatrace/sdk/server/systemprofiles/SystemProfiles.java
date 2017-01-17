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

import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.systemprofiles.models.ProfileStatus;
import com.dynatrace.sdk.server.systemprofiles.models.ProfileStatusEnum;
import com.dynatrace.sdk.server.systemprofiles.models.Profiles;
import com.dynatrace.sdk.server.systemprofiles.models.SystemProfileMetadata;

/**
 * Wraps a System Profiles REST API, providing an easy to use set of methods to control server.
 * <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=175966053">Community Page</a>
 */

public class SystemProfiles extends Service {
    public static final String ACTIVATE_PROFILE_CONFIGURATION_EP = "/profiles/%s/configurations/%s/status";
    public static final String PROFILE_STATUS_EP = "/profiles/%s/status";
    public static final String PROFILES_EP = "/profiles/%s";

    public SystemProfiles(DynatraceClient client) {
        super(client);
    }

    /**
     * Lists all System Profiles of the Dynatrace Server
     *
     * @return {@link Profiles} instance containing a list of available system profiles
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Profiles getSystemProfiles() throws ServerConnectionException, ServerResponseException {

    	return this.doGetRequest(String.format(PROFILES_EP, ""), getBodyResponseResolver(Profiles.class));
    }

    /**
     * Fetches a {@link SystemProfileMetadata} associated with given {@code profileName}
     *
     * @param profileName to get the metadata of
     * @return {@link SystemProfileMetadata} of a specific {@code profileName}
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public SystemProfileMetadata getSystemProfileMetadata(String profileName) throws ServerConnectionException, ServerResponseException {

    	return this.doGetRequest(String.format(PROFILES_EP, profileName), getBodyResponseResolver(SystemProfileMetadata.class));
    }

    /**
     * Change the activation state of a System Profile. Activating a configuration automatically sets all other configurations to DISABLED.
     * Manually setting the activation state to DISABLED via this call is not allowed.
     *
     * @param profileName       - name of the System Profile
     * @param configurationName - name of the Configuration to activate
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public void activateProfileConfiguration(String profileName, String configurationName) throws ServerConnectionException, ServerResponseException {

    	this.doPutRequest(String.format(ACTIVATE_PROFILE_CONFIGURATION_EP, profileName, configurationName),
    			new ProfileStatus(ProfileStatusEnum.ENABLED), Service.getEmtpyResolver());
    }

    /**
     * Enable System Profile
     *
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public void enableProfile(String profileName) throws ServerConnectionException, ServerResponseException {

    	this.doPutRequest(String.format(PROFILE_STATUS_EP, profileName), new ProfileStatus(ProfileStatusEnum.ENABLED), Service.getEmtpyResolver());
    }

    /**
     * Disable System Profile
     *
     * @param profileName - name of the System Profile
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public void disableProfile(String profileName) throws ServerConnectionException, ServerResponseException {

    	this.doPutRequest(String.format(PROFILE_STATUS_EP, profileName), new ProfileStatus(ProfileStatusEnum.DISABLED), Service.getEmtpyResolver());
    }

}
