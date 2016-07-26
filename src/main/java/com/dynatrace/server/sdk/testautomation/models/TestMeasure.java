package com.dynatrace.server.sdk.testautomation.models;

import com.dynatrace.server.sdk.testautomation.adapters.DoubleMeasureAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class TestMeasure {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String metricGroup;
    @XmlAttribute
    @XmlJavaTypeAdapter(DoubleMeasureAdapter.class)
    private Double expectedMin;
    @XmlAttribute
    @XmlJavaTypeAdapter(DoubleMeasureAdapter.class)
    private Double expectedMax;
    @XmlAttribute
    @XmlJavaTypeAdapter(DoubleMeasureAdapter.class)
    private Double value;
    @XmlAttribute
    private String unit;
    @XmlAttribute
    @XmlJavaTypeAdapter(DoubleMeasureAdapter.class)
    private Double violationPercentage;

    @XmlAttribute(name = "numFailingOrInvalidatedRuns")
    private Integer failingOrInvalidatedRunsCount;
    @XmlAttribute(name = "numValidRuns")
    private Integer validRunsCount;
    @XmlAttribute(name = "numImprovedRuns")
    private Integer improvedRunsCount;
    @XmlAttribute(name = "numDegradedRuns")
    private Integer degradedRunsCount;

    public String getName() {
        return this.name;
    }

    public String getMetricGroup() {
        return this.metricGroup;
    }

    public Double getExpectedMin() {
        return this.expectedMin;
    }

    public Double getExpectedMax() {
        return this.expectedMax;
    }

    public Double getValue() {
        return this.value;
    }

    public String getUnit() {
        return this.unit;
    }

    public Double getViolationPercentage() {
        return this.violationPercentage;
    }

    public Integer getFailingOrInvalidatedRunsCount() {
        return this.failingOrInvalidatedRunsCount;
    }

    public Integer getValidRunsCount() {
        return this.validRunsCount;
    }

    public Integer getImprovedRunsCount() {
        return this.improvedRunsCount;
    }

    public Integer getDegradedRunsCount() {
        return this.degradedRunsCount;
    }

    @Override
    public String toString() {
        return "TestMeasure{" +
                "name='" + this.name + '\'' +
                ", metricGroup='" + this.metricGroup + '\'' +
                ", expectedMin=" + this.expectedMin +
                ", expectedMax=" + this.expectedMax +
                ", value=" + this.value +
                ", unit='" + this.unit + '\'' +
                ", violationPercentage=" + this.violationPercentage +
                ", failingOrInvalidatedRunsCount=" + this.failingOrInvalidatedRunsCount +
                ", validRunsCount=" + this.validRunsCount +
                ", improvedRunsCount=" + this.improvedRunsCount +
                ", degradedRunsCount=" + this.degradedRunsCount +
                '}';
    }
}
