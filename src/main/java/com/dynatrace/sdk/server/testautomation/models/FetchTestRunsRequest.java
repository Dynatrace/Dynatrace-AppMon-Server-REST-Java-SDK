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

package com.dynatrace.sdk.server.testautomation.models;

import java.util.*;

public class FetchTestRunsRequest {

    public enum Extension {
        TEST_RUNS("runs"), TEST_RESULTS("results"), MEASURES("measures");

        private final String internal;

        Extension(String internal) {
            this.internal = internal;
        }

        public String getInternal() {
            return this.internal;
        }
    }

    private String systemProfile;
    private Date startTime;
    private Date endTime;
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

    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
     * @return Max number of test runs to be returned in this call.
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

    public void setVersionRevisionFilter(String... versionRevisions) {
        this.filters.put("versionRevision", Arrays.asList(versionRevisions));
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

    @Override
    public String toString() {
        return "FetchTestRunsRequest{" +
                "systemProfile='" + this.systemProfile + '\'' +
                ", startTime=" + this.startTime +
                ", endTime=" + this.endTime +
                ", maxTestRuns=" + this.maxTestRuns +
                ", maxBuilds=" + this.maxBuilds +
                ", extend=" + this.extend +
                ", filters=" + this.filters +
                '}';
    }
}