package com.dynatrace.server.sdk.testruns.models;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchTestRunsRequest {

    public enum Extension {
        TEST_RUNS("testRuns"), TEST_RESULTS("testResults"), MEASURES("measures");

        private final String internal;

        Extension(String internal) {
            this.internal = internal;
        }

        public String getInternal() {
            return this.internal;
        }
    }

    private final String systemProfile;
    private Long startTime;
    private Long endTime;
    private Integer maxTestRuns;
    private Integer maxBuilds;
    private Extension extend;

    private ArrayList<NameValuePair> additionalParameters = new ArrayList<>();

    public FetchTestRunsRequest(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    public String getSystemProfile() {
        return this.systemProfile;
    }

    /**
     * @return
     */
    public Long getStartTime() {
        return this.startTime;
    }

    /**
     * @param startTime - Start timestamp of timeframe, in milisecond since epoch (January 1, 1970 UTC).
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime.getTime();
    }

    public Long getEndTime() {
        return this.endTime;
    }

    /**
     * @param endTime - End timestamp of timeframe, in milisecond since epoch (January 1, 1970 UTC).
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime.getTime();
    }

    /**
     * @return Max number of testruns to be returned in this call.
     */
    public Integer getMaxTestRuns() {
        return this.maxTestRuns;
    }

    /**
     * @param maxTestRuns - Max number of testruns to be returned in this call.
     */
    public void setMaxTestRuns(int maxTestRuns) {
        this.maxTestRuns = maxTestRuns;
    }

    /**
     * @return Max number of different versionBuild parameters that {@link TestRuns} returned in this call will have.
     */
    public Integer getMaxBuilds() {
        return this.maxBuilds;
    }

    /**
     * @param maxBuilds - Max number of different versionBuild parameters that {@link TestRuns} returned in this call will have.
     */
    public void setMaxBuilds(int maxBuilds) {
        this.maxBuilds = maxBuilds;
    }

    /**
     * @return - Response detail level.
     */
    public Extension getExtend() {
        return this.extend;
    }

    /**
     * @param extend - Defines response detail level.
     */
    public void setExtend(Extension extend) {
        this.extend = extend;
    }

    public List<NameValuePair> getAdditionalParameters() {
        return this.additionalParameters;
    }

    /**
     * Adds an additional parameter based on it's {@link ParameterType}, multiple values for a single type can be added.
     * @param values values to add
     * @param <T> value type
     */
    public <T> void addAdditionalParameter(ParameterType<T> type, T... values) {
        for (T val : values) {
            this.additionalParameters.add(new BasicNameValuePair(type.getInternal(), val.toString()));
        }
    }

    public List<NameValuePair> getParameters() {
        ArrayList<NameValuePair> nvps = new ArrayList<>(this.additionalParameters);
        if (this.getStartTime() != null) {
            nvps.add(new BasicNameValuePair("startTime", String.valueOf(this.getStartTime())));
        }
        if (this.getEndTime() != null) {
            nvps.add(new BasicNameValuePair("endTime", String.valueOf(this.getEndTime())));
        }
        if (this.getExtend() != null) {
            nvps.add(new BasicNameValuePair("extend", this.getExtend().getInternal()));
        }
        if (this.getMaxTestRuns() != null) {
            nvps.add(new BasicNameValuePair("lastNTestruns", String.valueOf(this.getMaxTestRuns())));
        }
        if (this.getMaxBuilds() != null) {
            nvps.add(new BasicNameValuePair("lastNBuilds", String.valueOf(this.getMaxBuilds())));
        }
        return nvps;
    }

    public static class ParameterType<T> {
        public static final ParameterType<String> VERSION_BUILD = new ParameterType<>("versionBuild");
        public static final ParameterType<String> VERSION_MAJOR = new ParameterType<>("versionMajor");
        public static final ParameterType<String> VERSION_MILESTONE = new ParameterType<>("versionMilestone");
        public static final ParameterType<String> VERSION_MINOR = new ParameterType<>("versionMinor");
        public static final ParameterType<TestCategory> TEST_CATEGORY = new ParameterType<>("category");
        public static final ParameterType<String> AGENT_GROUP = new ParameterType<>("agentGroup");
        public static final ParameterType<String> MARKER_NAME = new ParameterType<>("markerName");
        public static final ParameterType<String> PLATFORM = new ParameterType<>("platform");

        private final String internal;

        public ParameterType(String internal) {
            this.internal = internal;
        }

        public String getInternal() {
            return this.internal;
        }
    }

}
