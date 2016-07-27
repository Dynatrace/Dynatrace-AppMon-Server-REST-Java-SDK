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

package com.dynatrace.server.sdk.testautomation.models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "testRun")
public class TestRun {
    @XmlAttribute
    private Long startTime;
    @XmlAttribute
    private String platform;
    @XmlAttribute
    private String systemProfile;
    @XmlAttribute
    private TestCategory category;
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String href;

    @XmlAttribute
    private String versionMajor;
    @XmlAttribute
    private String versionMinor;
    @XmlAttribute
    private String versionRevision;
    @XmlAttribute
    private String versionBuild;
    @XmlAttribute
    private CreationMode creationMode;

    @XmlAttribute
    private Integer numPassed;
    @XmlAttribute
    private Integer numFailed;
    @XmlAttribute
    private Integer numVolatile;
    @XmlAttribute
    private Integer numImproved;
    @XmlAttribute
    private Integer numDegraded;
    @XmlAttribute
    private Integer numInvalidated;

    @XmlElement(name = "testResult")
    private List<TestResult> testResults = new ArrayList<>();

    public Long getStartTime() {
        return this.startTime;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getSystemProfile() {
        return this.systemProfile;
    }

    public TestCategory getCategory() {
        return this.category;
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public String getVersionMajor() {
        return this.versionMajor;
    }

    public String getVersionMinor() {
        return this.versionMinor;
    }

    public String getVersionRevision() {
        return this.versionRevision;
    }

    public String getVersionBuild() {
        return this.versionBuild;
    }

    public CreationMode getCreationMode() {
        return this.creationMode;
    }

    public List<TestResult> getTestResults() {
        return this.testResults;
    }

    public Integer getPassedCount() {
        return this.numPassed;
    }

    public Integer getFailedCount() {
        return this.numFailed;
    }

    public Integer getVolatileCount() {
        return this.numVolatile;
    }

    public Integer getImprovedCount() {
        return this.numImproved;
    }

    public Integer getDegradedCount() {
        return this.numDegraded;
    }

    public Integer getInvalidatedCount() {
        return this.numInvalidated;
    }

    @Override
    public String toString() {
        return "TestRun{" +
                "startTime=" + this.startTime +
                ", platform='" + this.platform + '\'' +
                ", systemProfile='" + this.systemProfile + '\'' +
                ", category=" + this.category +
                ", id='" + this.id + '\'' +
                ", href='" + this.href + '\'' +
                ", versionMajor='" + this.versionMajor + '\'' +
                ", versionMinor='" + this.versionMinor + '\'' +
                ", versionRevision='" + this.versionRevision + '\'' +
                ", versionBuild='" + this.versionBuild + '\'' +
                ", creationMode=" + this.creationMode +
                ", numPassed=" + this.numPassed +
                ", numFailed=" + this.numFailed +
                ", numVolatile=" + this.numVolatile +
                ", numImproved=" + this.numImproved +
                ", numDegraded=" + this.numDegraded +
                ", numInvalidated=" + this.numInvalidated +
                ", testResults=" + this.testResults +
                '}';
    }
}
