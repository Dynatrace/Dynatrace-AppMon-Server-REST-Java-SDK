/***************************************************
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 *
 * @file: RecordingStatus.java
 * @date: Jan 16, 2017
 * @author: tomasz.chojnacki
 */
package com.dynatrace.sdk.server.sessions.models;

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
public class RecordingStatus {

	@XmlAttribute
	private boolean recording;

	public RecordingStatus() {
	}

	public RecordingStatus(boolean recording) {
		this.recording = recording;
	}

	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
	}

}
