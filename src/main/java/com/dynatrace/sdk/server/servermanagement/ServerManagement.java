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

import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Wraps a Server Management REST API, providing an easy to use set of methods to control server.
 * <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=175965889">Community Page</a>
 */

public class ServerManagement extends Service {
    public static final String SERVER_RESTART_EP = "/rest/management/server/restart";
    public static final String SERVER_SHUTDOWN_EP = "/rest/management/server/shutdown";

    protected ServerManagement(DynatraceClient client) {
        super(client);
    }

    /**
     * Performs restart of the Server
     *
     * @return {@link Boolean} that describes that the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public boolean restart() throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(SERVER_RESTART_EP);

            try (CloseableHttpResponse response = this.doPostRequest(uri, null, Service.XML_CONTENT_TYPE);
                 InputStream is = response.getEntity().getContent()) {
                // xpath is reasonable for parsing such a small entity
                try {
                    String result = Service.compileValueExpression().evaluate(new InputSource(is));
                    return result != null && result.equals("true");
                } catch (XPathExpressionException e) {
                    throw new ServerResponseException(response.getStatusLine().getStatusCode(), "Could not parse response: " + e.getMessage(), e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid request", e);
        }
    }

    /**
     * Performs shutdown of the Server
     *
     * @return {@link Boolean} that describes that the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public boolean shutdown() throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(SERVER_SHUTDOWN_EP);

            try (CloseableHttpResponse response = this.doPostRequest(uri, null, Service.XML_CONTENT_TYPE);
                 InputStream is = response.getEntity().getContent()) {
                // xpath is reasonable for parsing such a small entity
                try {
                    String result = Service.compileValueExpression().evaluate(new InputSource(is));
                    return result != null && result.equals("true");
                } catch (XPathExpressionException e) {
                    throw new ServerResponseException(response.getStatusLine().getStatusCode(), "Could not parse response: " + e.getMessage(), e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid request", e);
        }
    }
}
