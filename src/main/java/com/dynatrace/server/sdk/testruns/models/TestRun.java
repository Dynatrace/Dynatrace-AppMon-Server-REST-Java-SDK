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
        return this.creationTime;
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
}
