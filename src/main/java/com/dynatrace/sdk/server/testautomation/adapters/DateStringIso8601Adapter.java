/***************************************************
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 *
 * @file: DateStringIso8601Adapter.java
 * @date: Jan 17, 2017
 * @author: tomasz.chojnacki
 */
package com.dynatrace.sdk.server.testautomation.adapters;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author tomasz.chojnacki
 */
public class DateStringIso8601Adapter extends XmlAdapter<String, Date> {

	@Override
	public Date unmarshal(String v) throws Exception {
		return getAsDate(v);
	}

	@Override
	public String marshal(Date v) throws Exception {
		return getAsString(v);
	}

	public static Date getAsDate(String dateString) {
		return DatatypeConverter.parseDateTime(dateString).getTime();
	}

	public static String getAsString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return DatatypeConverter.printDateTime(calendar);
	}

}
