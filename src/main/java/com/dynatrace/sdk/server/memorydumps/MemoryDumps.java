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

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.memorydumps.models.MemoryDump;
import com.dynatrace.sdk.server.memorydumps.models.MemoryDumpJob;

/**
 * Wraps Dynatrace Server MemoryDumps REST API providing an easy to use set of methods.
 * <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=175966087">Wiki entry</a>
 */
public class MemoryDumps extends Service {
    public static final String MEMORY_DUMP_EP = "/rest/management/profiles/%s/memorydumps/%s";
    public static final String MEMORY_DUMP_JOB_EP = "/rest/management/profiles/%s/memorydumpjob";
    public static final String MEMORY_DUMP_JOBS_EP = "/rest/management/profiles/%s/memorydumpjobs/%s";

    public static final String RESPONSE_LOCATION_HEADER_NAME = "Location";

    public MemoryDumps(DynatraceClient client) {
        super(client);
    }

    /**
     * Fetches a specific {@link MemoryDump}
     *
     * @param profileName profileName associated with {@link MemoryDump}
     * @param resourceId  {@link MemoryDump#getResourceId()}
     * @return {@link MemoryDump} instance containing all information associated with it
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public MemoryDump getMemoryDump(String profileName, String resourceId) throws ServerConnectionException, ServerResponseException {

    	return this.doGetRequest(String.format(MEMORY_DUMP_EP, profileName, resourceId), MemoryDump.class);
    }

    /**
     * Fetches a specific {@link MemoryDumpJob}
     *
     * @param profileName     profileName associated with {@link MemoryDump}
     * @param memoryDumpJobId {@link MemoryDumpJob#getId()}
     * @return {@link MemoryDumpJob} instance contianing all information associated with it
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public MemoryDumpJob getMemoryDumpJob(String profileName, String memoryDumpJobId) throws ServerConnectionException, ServerResponseException {
    	return this.doGetRequest(String.format(MEMORY_DUMP_JOBS_EP, profileName, memoryDumpJobId), MemoryDumpJob.class);
    }

    /**
     * Creates a new {@link MemoryDumpJob}
     *
     * @param profileName - profileName associated with {@link MemoryDump}
     * @param parameters - memory dump job parameters
     * @return reference to the created job
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public String createMemoryDumpJob(String profileName, MemoryDumpJob parameters) throws ServerConnectionException, ServerResponseException {

    	try (CloseableHttpResponse response = this.doPutRequest(buildURI(String.format(MEMORY_DUMP_JOB_EP, profileName)), Service.xmlObjectToEntity(parameters))) {

    		Header locationHeader = response.getLastHeader(RESPONSE_LOCATION_HEADER_NAME);

            if (locationHeader != null) {
                return locationHeader.getValue();
            } else {
                throw new ServerResponseException(response.getStatusLine().getStatusCode(), String.format("Invalid server response: %s header is not set", RESPONSE_LOCATION_HEADER_NAME));
            }

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("Invalid profileName[%s] or parameters[%s]: %s", profileName, parameters, e.getMessage()), e);
        } catch (IOException e) {
            throw new RuntimeException("Could not close http response:" + e.getMessage(), e);
        }
    }
}
