package com.dynatrace.server.sdk.testautomation.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "testRuns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestRuns {
    @XmlElement(name = "testRun")
    private List<TestRun> testRuns = new ArrayList<>();

    public List<TestRun> getTestRuns() {
        return this.testRuns;
    }
}
