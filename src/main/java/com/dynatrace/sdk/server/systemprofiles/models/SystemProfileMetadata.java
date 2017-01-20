package com.dynatrace.sdk.server.systemprofiles.models;

import javax.xml.bind.annotation.*;

/**
 *
 * @author tomasz.chojnacki
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="systemprofile")
public class SystemProfileMetadata {

    @XmlAttribute
    private String id;
    @XmlAttribute
    private String description;
	@XmlAttribute
    private Boolean enabled;
    @XmlAttribute(name="isrecording")
    private boolean recording;

    public SystemProfileMetadata() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getRecording() {
		return recording;
	}

	public void setRecording(Boolean recording) {
		this.recording = recording;
	}



}
