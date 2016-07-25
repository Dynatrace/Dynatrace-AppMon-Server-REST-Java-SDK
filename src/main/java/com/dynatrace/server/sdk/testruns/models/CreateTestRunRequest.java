package com.dynatrace.server.sdk.testruns.models;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "testRun")
public class CreateTestRunRequest {

    @XmlAttribute
    private String platform;
    @XmlAttribute(required = true)
    private String systemProfile;
    @XmlAttribute
    private TestCategory category;

    @XmlAttribute
    private String versionMajor;
    @XmlAttribute
    private String versionMinor;
    @XmlAttribute
    private String versionRevision;
    @XmlAttribute(required = true)
    private String versionBuild;
    @XmlAttribute
    private String versionMilestone;

    @XmlElement
    private TestMetaData additionalMetaData;

    public CreateTestRunRequest() {
    }

    public CreateTestRunRequest(String systemProfile, String versionBuild) {
        this.systemProfile = systemProfile;
        this.versionBuild = versionBuild;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSystemProfile() {
        return systemProfile;
    }

    public void setSystemProfile(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    public TestCategory getCategory() {
        return category;
    }

    public void setCategory(TestCategory category) {
        this.category = category;
    }

    public String getVersionMajor() {
        return versionMajor;
    }

    public void setVersionMajor(String versionMajor) {
        this.versionMajor = versionMajor;
    }

    public String getVersionMinor() {
        return versionMinor;
    }

    public void setVersionMinor(String versionMinor) {
        this.versionMinor = versionMinor;
    }

    public String getVersionRevision() {
        return versionRevision;
    }

    public void setVersionRevision(String versionRevision) {
        this.versionRevision = versionRevision;
    }

    public String getVersionBuild() {
        return versionBuild;
    }

    public void setVersionBuild(String versionBuild) {
        this.versionBuild = versionBuild;
    }

    public String getVersionMilestone() {
        return versionMilestone;
    }

    public void setVersionMilestone(String versionMilestone) {
        this.versionMilestone = versionMilestone;
    }

    public TestMetaData getAdditionalMetaData() {
        return additionalMetaData;
    }

    public void setAdditionalMetaData(TestMetaData additionalMetaData) {
        this.additionalMetaData = additionalMetaData;
    }
}
