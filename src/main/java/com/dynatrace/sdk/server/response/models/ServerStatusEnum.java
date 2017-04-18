/***************************************************
 *
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 * @file: ResultResponse.java
 * @date: 11.01.2017
 * @author: tomasz.chojnacki
 */
package com.dynatrace.sdk.server.response.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tomasz.chojnacki
 */
@XmlType
@XmlEnum(String.class)
public enum ServerStatusEnum {

    @XmlEnumValue("success")
    SUCCESS,
    @XmlEnumValue("fail")
    FAIL

}
