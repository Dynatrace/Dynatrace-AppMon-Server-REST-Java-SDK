package com.dynatrace.server.sdk.sessions;

import com.dynatrace.server.sdk.DynatraceClient;
import com.dynatrace.server.sdk.Service;
import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import com.dynatrace.server.sdk.sessions.models.StartRecordingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Wraps a Live Session REST API, providing an easy to use set of methods to control sessions.
 * <a href="https://community.dynatrace.com/community/pages/viewpage.action?pageId=175966050">Community Page</a>
 */
public class Sessions extends Service {
    public static final String SESSIONS_EP = "/rest/management/profiles/%s/%s";
    private static final XPathExpression VALUE_EXPRESSION;

    static {
        try {
            VALUE_EXPRESSION = XPathFactory.newInstance().newXPath().compile("/result/@value");
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public Sessions(DynatraceClient client) {
        super(client);
    }

    /**
     * Starts session recording for a specific {@link StartRecordingRequest#getSystemProfile() system profile}.
     * <strong>Starting session recording is not possible if Continuous Transaction Storage is enabled.</strong>
     *
     * @param request - session parameters
     * @return session name
     */
    public String startRecording(StartRecordingRequest request) throws ServerConnectionException, ServerResponseException {
        ArrayList<NameValuePair> nvps = new ArrayList<>();
        if (request.getPresentableName() != null) {
            nvps.add(new BasicNameValuePair("presentableName", request.getPresentableName()));
        }
        if (request.getDescription() != null) {
            nvps.add(new BasicNameValuePair("description", request.getDescription()));
        }
        if (request.isTimestampAllowed() != null) {
            nvps.add(new BasicNameValuePair("isTimeStampAllowed", String.valueOf(request.isTimestampAllowed())));
        }
        if (request.getRecordingOption() != null) {
            nvps.add(new BasicNameValuePair("recordingOption", request.getRecordingOption().getInternal()));
        }
        if (request.isSessionLocked() != null) {
            nvps.add(new BasicNameValuePair("isSessionLocked", String.valueOf(request.isSessionLocked())));
        }
        for (String label : request.getLabels()) {
            nvps.add(new BasicNameValuePair("label", label));
        }
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps);
            try (CloseableHttpResponse response = this.doPostRequest(this.buildURI(String.format(SESSIONS_EP, request.getSystemProfile(), "startrecording")), entity, entity.getContentType().getValue())) {
                try (InputStream is = response.getEntity().getContent()) {
                    return VALUE_EXPRESSION.evaluate(new InputSource(is));
                } catch (XPathExpressionException | IOException e) {
                    throw new ServerResponseException(response.getStatusLine().getStatusCode(), "Could not parse server response", e);
                }
            }
        } catch (UnsupportedEncodingException | URISyntaxException e) {
            throw new IllegalArgumentException("Invalid parameters format", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not close http response", e);
        }
    }

    /**
     * Stops live session recording
     * <strong>This call does complete until all recorded data is fully processed on the Server. Depending on the environment, it can take a few minutes until an HTTP response message is received.</strong>
     * <strong>Stopping session recording is not possible if Continuous Transaction Storage is enabled.</strong>
     *
     * @param profileName - profile name to stop the session of
     * @return session name
     */
    public String stopRecording(String profileName) throws ServerResponseException, ServerConnectionException {
        try (CloseableHttpResponse response = this.doGetRequest(this.buildURI(String.format(SESSIONS_EP, profileName, "stoprecording")))) {
            try (InputStream is = response.getEntity().getContent()) {
                return VALUE_EXPRESSION.evaluate(new InputSource(is));
            } catch (XPathExpressionException | IOException e) {
                throw new ServerResponseException(response.getStatusLine().getStatusCode(), "Could not parse server response", e);
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid profileName format.", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not close http response", e);
        }
    }

    /**
     * Clears the live session.
     * <strong>Clearing a session is not possible if Continuous Transaction Storage is enabled.</strong>
     *
     * @param profileName - profile name to clean the live session of
     */
    public boolean clear(String profileName) throws ServerResponseException, ServerConnectionException {
        try (CloseableHttpResponse response = this.doGetRequest(this.buildURI(String.format(SESSIONS_EP, profileName, "clear")))) {
            try (InputStream is = response.getEntity().getContent()) {
                return VALUE_EXPRESSION.evaluate(new InputSource(is)).equals("true");
            } catch (XPathExpressionException | IOException e) {
                throw new ServerResponseException(response.getStatusLine().getStatusCode(), "Could not parse server response", e);
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid profileName format.", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not close http response", e);
        }
    }
}
