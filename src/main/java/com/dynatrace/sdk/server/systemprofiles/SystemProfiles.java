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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Wraps a System Profiles REST API, providing an easy to use set of methods to control server.
 * <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=175966053">Community Page</a>
 */

public class SystemProfiles extends Service {
    public static final String ACTIVATE_PROFILE_CONFIGURATION_EP = "/rest/management/profiles/%s/configurations/%s/activate";
    public static final String PROFILE_ENABLE_EP = "/rest/management/profiles/%s/enable";
    public static final String PROFILE_DISABLE_EP = "/rest/management/profiles/%s/disable";

    private static final XPathExpression SIMPLE_RESULT_EXPRESSION;

    static {
        try {
            SIMPLE_RESULT_EXPRESSION = XPathFactory.newInstance().newXPath().compile("/result/@value");
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    protected SystemProfiles(DynatraceClient client) {
        super(client);
    }

    /**
     * Activate a System Profile configuration, so that all others are set to inactive
     *
     * @param profileName - name of the System Profile
     * @param configurationName - name of the Configuration to activate
     * @return {@link Boolean} that describes that the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Boolean activateProfileConfiguration(String profileName, String configurationName) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(ACTIVATE_PROFILE_CONFIGURATION_EP, profileName, configurationName));
            String result = null;

            try (CloseableHttpResponse response = this.doGetRequest(uri);
                 InputStream is = response.getEntity().getContent()) {
                // xpath is reasonable for parsing such a small entity
                result = SIMPLE_RESULT_EXPRESSION.evaluate(new InputSource(is));
            } catch (XPathExpressionException e) {
                // if it occurs, it means result == false
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return (result != null) && (result.equals("true"));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid profileName or configurationName", e);
        }
    }

    /**
     * Enable System Profile
     *
     * @param profileName - name of the System Profile
     * @return {@link Boolean} that describes that the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Boolean enableProfile(String profileName) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(PROFILE_ENABLE_EP, profileName));
            String result = null;

            try (CloseableHttpResponse response = this.doGetRequest(uri);
                 InputStream is = response.getEntity().getContent()) {
                // xpath is reasonable for parsing such a small entity
                result = SIMPLE_RESULT_EXPRESSION.evaluate(new InputSource(is));
            } catch (XPathExpressionException e) {
                // if it occurs, it means result == false
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return (result != null) && (result.equals("true"));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid profileName", e);
        }
    }

    /**
     * Disable System Profile
     *
     * @param profileName - name of the System Profile
     * @return {@link Boolean} that describes that the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Boolean disableProfile(String profileName) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(PROFILE_DISABLE_EP, profileName));
            String result = null;

            try (CloseableHttpResponse response = this.doGetRequest(uri);
                 InputStream is = response.getEntity().getContent()) {
                // xpath is reasonable for parsing such a small entity
                result = SIMPLE_RESULT_EXPRESSION.evaluate(new InputSource(is));
            } catch (XPathExpressionException e) {
                // if it occurs, it means result == false
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return (result != null) && (result.equals("true"));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid profileName", e);
        }
    }

}
