package com.dynatrace.server.sdk.testruns.models;

import javax.xml.bind.annotation.*;
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
    private List<TestResult> testResults;

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

    public Integer getNumPassed() {
        return this.numPassed;
    }

    public Integer getNumFailed() {
        return this.numFailed;
    }

    public Integer getNumVolatile() {
        return this.numVolatile;
    }

    public Integer getNumImproved() {
        return this.numImproved;
    }

    public Integer getNumDegraded() {
        return this.numDegraded;
    }

    public Integer getNumInvalidated() {
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
