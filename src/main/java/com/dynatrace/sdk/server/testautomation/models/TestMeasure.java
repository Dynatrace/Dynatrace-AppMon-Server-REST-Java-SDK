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

import com.dynatrace.sdk.server.testautomation.adapters.DoubleMeasureAdapter;

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

    public TestMeasure(String name, String metricGroup, Double expectedMin, Double expectedMax, Double value, String unit, Double violationPercentage, Integer failingOrInvalidatedRunsCount, Integer validRunsCount, Integer improvedRunsCount, Integer degradedRunsCount) {
        this.name = name;
        this.metricGroup = metricGroup;
        this.expectedMin = expectedMin;
        this.expectedMax = expectedMax;
        this.value = value;
        this.unit = unit;
        this.violationPercentage = violationPercentage;
        this.failingOrInvalidatedRunsCount = failingOrInvalidatedRunsCount;
        this.validRunsCount = validRunsCount;
        this.improvedRunsCount = improvedRunsCount;
        this.degradedRunsCount = degradedRunsCount;
    }

    public TestMeasure() {
    }

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
