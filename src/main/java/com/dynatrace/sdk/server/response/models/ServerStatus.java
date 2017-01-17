/***************************************************
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 *
 * @file: StatusResponse.java
 * @date: Jan 13, 2017
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
public class ServerStatus {

	@XmlAttribute
	ServerStatusEnum status;

	public ServerStatus() {
	}


	public ServerStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ServerStatusEnum status) {
		this.status = status;
	}


	public boolean getResultAsBoolean() {
		return status == ServerStatusEnum.SUCCESS;
	}

}
