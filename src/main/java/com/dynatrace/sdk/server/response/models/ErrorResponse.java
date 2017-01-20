/***************************************************
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 *
 * @file: ErrorResponse.java
 * @date: 11.01.2017
 * @author: tomasz.chojnacki
 */
package com.dynatrace.sdk.server.response.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tomasz.chojnacki
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ErrorResponse {

	@XmlAttribute
	private Integer code;

	@XmlAttribute
	private String message;

	public ErrorResponse() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}



}
