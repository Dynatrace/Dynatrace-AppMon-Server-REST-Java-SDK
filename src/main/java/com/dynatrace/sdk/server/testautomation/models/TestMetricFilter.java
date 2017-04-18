/***************************************************
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 *
 * @file: TestMetric.java
 * @date: Jan 23, 2017
 * @author: tomasz.chojnacki
 */
package com.dynatrace.sdk.server.testautomation.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author tomasz.chojnacki
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TestMetricFilter {

	@XmlAttribute
	private String group;
	@XmlAttribute
	private String metric;


	public TestMetricFilter() {
	}

	public TestMetricFilter(String group, String metric) {
		this.group = group;
		this.metric = metric;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

}
