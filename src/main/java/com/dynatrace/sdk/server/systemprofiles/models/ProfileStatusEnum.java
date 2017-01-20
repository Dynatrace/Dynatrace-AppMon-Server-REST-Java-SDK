/***************************************************
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 *
 * @file: ActivationStatus.java
 * @date: Jan 16, 2017
 * @author: tomasz.chojnacki
 */
package com.dynatrace.sdk.server.systemprofiles.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tomasz.chojnacki
 */
@XmlType
@XmlEnum(String.class)
public enum ProfileStatusEnum {

	@XmlEnumValue("ENABLED")
	ENABLED,
	@XmlEnumValue("DISABLED")
	DISABLED;

}
