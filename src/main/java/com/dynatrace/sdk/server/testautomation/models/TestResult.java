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

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.dynatrace.sdk.server.testautomation.adapters.DateStringIso8601Adapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class TestResult {

    @XmlAttribute(name = "exectime")
    @XmlJavaTypeAdapter(DateStringIso8601Adapter.class)
    private Date execTime;
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "package")
    private String packageName;
    @XmlAttribute
    private String platform;
    @XmlAttribute
    private TestStatus status;

    @XmlElement(name = "measures")
    private List<TestMeasure> measures;

    public TestResult(Date execTime, String name, String packageName, String platform, TestStatus status, List<TestMeasure> measures) {
        this.execTime = execTime;
        this.name = name;
        this.packageName = packageName;
        this.platform = platform;
        this.status = status;
        this.measures = measures;
    }

    public TestResult() {}

    public Date getExecutionTime() {
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
