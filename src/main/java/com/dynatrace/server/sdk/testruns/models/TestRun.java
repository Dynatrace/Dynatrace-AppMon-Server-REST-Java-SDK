package com.dynatrace.server.sdk.testruns.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "testRun")
public class TestRun {
    //todo Date
    @XmlAttribute
    private String creationTime;
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

    public String getCreationTime() {
        return creationTime;
    }

    public String getPlatform() {
        return platform;
    }

    public String getSystemProfile() {
        return systemProfile;
    }

    public TestCategory getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public String getVersionMajor() {
        return versionMajor;
    }

    public String getVersionMinor() {
        return versionMinor;
    }

    public String getVersionRevision() {
        return versionRevision;
    }

    public String getVersionBuild() {
        return versionBuild;
    }
}
