package com.dynatrace.server.sdk.testruns.models;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

public class TestRuns {
    @XmlElementWrapper(name = "testRuns")
    private final List<TestRun> testRuns = new ArrayList<>();

    public List<TestRun> getTestRuns() {
        return this.testRuns;
    }
}
