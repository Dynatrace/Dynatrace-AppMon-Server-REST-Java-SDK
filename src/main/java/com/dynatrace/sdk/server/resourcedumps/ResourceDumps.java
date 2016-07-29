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

import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.resourcedumps.models.CreateThreadDumpRequest;
import com.dynatrace.sdk.server.resourcedumps.models.GetThreadDumpStatus;
import com.dynatrace.sdk.server.resourcedumps.models.GetThreadDumpStatusMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a Resource Dumps REST API, providing an easy to use set of methods to control server.
 * <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=175965955">Community Page</a>
 */

public class ResourceDumps extends Service {
    public static final String CREATE_THREAD_DUMP_EP = "/rest/management/profiles/%s/threaddump";
    public static final String GET_THREAD_DUMP_STATUS_EP = "/rest/management/profiles/%s/threaddumpcreated/%s";

    protected ResourceDumps(DynatraceClient client) {
        super(client);
    }

    /**
     * Requests a thread dump for a specific {@link CreateThreadDumpRequest#getSystemProfile() system profile}.
     *
     * @param request - parameters of the Agent for which the thread dump will be created
     * @return schedule ID
     */
    public String createThreadDump(CreateThreadDumpRequest request) throws ServerConnectionException, ServerResponseException {
        ArrayList<NameValuePair> nvps = new ArrayList<>();

        if (request.getAgentName() != null) {
            nvps.add(new BasicNameValuePair("agentName", request.getAgentName()));
        }
        if (request.getHostName() != null) {
            nvps.add(new BasicNameValuePair("hostName", request.getHostName()));
        }
        if (request.getProcessId() != null) {
            nvps.add(new BasicNameValuePair("processId", String.valueOf(request.getProcessId())));
        }
        if (request.isSessionLocked() != null) {
            nvps.add(new BasicNameValuePair("isSessionLocked", String.valueOf(request.isSessionLocked())));
        }

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps);
            try (CloseableHttpResponse response = this.doPostRequest(this.buildURI(String.format(CREATE_THREAD_DUMP_EP, request.getSystemProfile())), entity, entity.getContentType().getValue())) {
                try (InputStream is = response.getEntity().getContent()) {
                    return Service.compileValueExpression().evaluate(new InputSource(is));
                } catch (XPathExpressionException | IOException e) {
                    throw new ServerResponseException(response.getStatusLine().getStatusCode(), "Could not parse server response: " + e.getMessage(), e);
                }
            }
        } catch (UnsupportedEncodingException | URISyntaxException e) {
            throw new IllegalArgumentException("Invalid parameters format", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not close http response", e);
        }
    }


    /**
     * Requests a thread dump status for a specific {@param systemProfile} and (@param scheduleId).
     *
     * @param profileName - system profile name
     * @param scheduleId  - previously requested Thread Dump schedule ID
     * @return {@link GetThreadDumpStatus} instance containing a {@link List} of {@link GetThreadDumpStatusMessage}
     */
    public GetThreadDumpStatus getThreadDumpStatus(String profileName, String scheduleId) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(GET_THREAD_DUMP_STATUS_EP, profileName, scheduleId));
            return this.doGetRequest(uri, GetThreadDumpStatus.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("Invalid profileName[%s] or scheduleId[%s] format.", profileName, scheduleId), e);
        }
    }
}
