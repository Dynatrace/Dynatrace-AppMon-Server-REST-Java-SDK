/***************************************************
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 *
 * @file: ProfileStatus.java
 * @date: Jan 16, 2017
 * @author: tomasz.chojnacki
 */
package com.dynatrace.sdk.server.systemprofiles.models;

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
public class ProfileStatus {

	@XmlAttribute
	private ProfileStatusEnum status;

	public ProfileStatus() {
	}

	public ProfileStatus(ProfileStatusEnum status) {
		this.status = status;
	}

	public ProfileStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ProfileStatusEnum status) {
		this.status = status;
	}



}
