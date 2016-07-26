package com.dynatrace.server.sdk.testruns.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class TestResult {

    @XmlAttribute(name = "exectime")
    private Long execTime;
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "package")
    private String packageName;
    @XmlAttribute
    private String platform;
    @XmlAttribute
    private TestStatus status;

    @XmlElement(name = "measure")
    private List<TestMeasure> measures;

    public Long getExecutionTime() {
        return this.execTime;
    }

    public String getName() {
        return this.name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPlatform() {
        return this.platform;
    }

    public TestStatus getStatus() {
        return this.status;
    }

    public List<TestMeasure> getMeasures() {
        return this.measures;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "execTime=" + this.execTime +
                ", name='" + this.name + '\'' +
                ", packageName='" + this.packageName + '\'' +
                ", platform='" + this.platform + '\'' +
                ", status=" + this.status +
                ", measures=" + this.measures +
                '}';
    }
}
