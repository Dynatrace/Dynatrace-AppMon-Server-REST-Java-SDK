package com.dynatrace.server.sdk.testruns.models;

import java.util.*;

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

    private String systemProfile;
    private Long startTime;
    private Long endTime;
    private Integer maxTestRuns;
    private Integer maxBuilds;
    private Extension extend;

    private HashMap<String, List<String>> filters = new HashMap<>();

    // Required by Jaxb
    public FetchTestRunsRequest() {
    }

    public FetchTestRunsRequest(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    public String getSystemProfile() {
        return this.systemProfile;
    }

    public void setSystemProfile(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    /**
     * @return Start timestamp of timeframe, in milisecond since epoch (January 1, 1970 UTC).
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

    public void setVersionBuildFilter(String... versionBuilds) {
        this.filters.put("versionBuild", Arrays.asList(versionBuilds));
    }

    public void setVersionMajorFilter(String... versionMajors) {
        this.filters.put("versionMajor", Arrays.asList(versionMajors));
    }

    public void setVersionMilestoneFilter(String... versionMilestones) {
        this.filters.put("versionMilestone", Arrays.asList(versionMilestones));
    }

    public void setVersionMinorFilter(String... versionMinors) {
        this.filters.put("versionMinor", Arrays.asList(versionMinors));
    }

    public void setCategoryFilter(TestCategory... categories) {
        ArrayList<String> cats = new ArrayList<>();
        for (TestCategory cat : categories) {
            cats.add(cat.getInternal());
        }
        this.filters.put("category", cats);
    }

    public void setAgentGroupFilter(String... agentGroups) {
        this.filters.put("agentGroup", Arrays.asList(agentGroups));
    }

    public void setMarkerNameFilter(String... markerNames) {
        this.filters.put("markerName", Arrays.asList(markerNames));
    }

    public void setPlatformFilter(String... platforms) {
        this.filters.put("platform", Arrays.asList(platforms));
    }

    public HashMap<String, List<String>> getFilters() {
        return this.filters;
    }
}
