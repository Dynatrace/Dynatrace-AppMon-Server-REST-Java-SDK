package com.dynatrace.server.sdk.agentsandcollectors.models;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "agentProperties")
public class AgentPropertiesInformation {
    private String agentHost;
    private String agentId;
    private String agentVersion;
    private String agentBootstrapVersion;
    private String agentHostAddress;
    private String agentPlatform;
    private String bufferCount;
    private String bufferSaturationThreshold;
    private String bufferSize;
    private String clockFrequency;
    private String cloud;
    private String hiResClock;
    private String hotSensorPlaceable;
    private String hypervisor;
    private String osHyperVFriendly;
    private String instrumentationState;
    private String logFileLocation;
    private String maximumMemory;
    private String operatingSystem;
    private String osArchitecture;
    private String osEdition;
    private String osProductId;
    private String osVersion;
    private String processors;
    private String recoveryEnabled;
    private String startDate;
    private String startUp;
    private String timer;

    //dotNet only
    private String applicationServerVersionDetected;
    private String applicationServerDetected;
    private String cellNameDetected;
    private String clrVendor;
    private String clrVersion;
    private String runtimeVersion;
    private String commandLine;
    private String commandLineMayBeTruncated;
    private String hasCommandLine;
    private String workingDirectory;
    private String hasWorkingDirectory;

    // Mainframe only
    private String smfId;
    private String snaId;
    private String subsystem;
    private String jobname;
    private String queueType;
    private String ccsid;
    private String zosType;
    private String zosRelease;
    private String asid;
    private String regionId;

    /* getters */
    public String getAgentHost() {
        return this.agentHost;
    }

    public String getAgentId() {
        return this.agentId;
    }

    public String getAgentVersion() {
        return this.agentVersion;
    }

    public String getAgentBootstrapVersion() {
        return this.agentBootstrapVersion;
    }

    public String getAgentHostAddress() {
        return this.agentHostAddress;
    }

    public String getAgentPlatform() {
        return this.agentPlatform;
    }

    public String getBufferCount() {
        return this.bufferCount;
    }

    public String getBufferSaturationThreshold() {
        return this.bufferSaturationThreshold;
    }

    public String getBufferSize() {
        return this.bufferSize;
    }

    public String getClockFrequency() {
        return this.clockFrequency;
    }

    public String getCloud() {
        return this.cloud;
    }

    public String getHiResClock() {
        return this.hiResClock;
    }

    public String getHotSensorPlaceable() {
        return this.hotSensorPlaceable;
    }

    public String getHypervisor() {
        return this.hypervisor;
    }

    public String getOsHyperVFriendly() {
        return this.osHyperVFriendly;
    }

    public String getInstrumentationState() {
        return this.instrumentationState;
    }

    public String getLogFileLocation() {
        return this.logFileLocation;
    }

    public String getMaximumMemory() {
        return this.maximumMemory;
    }

    public String getOperatingSystem() {
        return this.operatingSystem;
    }

    public String getOsArchitecture() {
        return this.osArchitecture;
    }

    public String getOsEdition() {
        return this.osEdition;
    }

    public String getOsProductId() {
        return this.osProductId;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public String getProcessors() {
        return this.processors;
    }

    public String getRecoveryEnabled() {
        return this.recoveryEnabled;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getStartUp() {
        return this.startUp;
    }

    public String getTimer() {
        return this.timer;
    }

    public String getApplicationServerVersionDetected() {
        return this.applicationServerVersionDetected;
    }

    public String getApplicationServerDetected() {
        return this.applicationServerDetected;
    }

    public String getCellNameDetected() {
        return this.cellNameDetected;
    }

    public String getClrVendor() {
        return this.clrVendor;
    }

    public String getClrVersion() {
        return this.clrVersion;
    }

    public String getRuntimeVersion() {
        return this.runtimeVersion;
    }

    public String getCommandLine() {
        return this.commandLine;
    }

    public String getCommandLineMayBeTruncated() {
        return this.commandLineMayBeTruncated;
    }

    public String getHasCommandLine() {
        return this.hasCommandLine;
    }

    public String getWorkingDirectory() {
        return this.workingDirectory;
    }

    public String getHasWorkingDirectory() {
        return this.hasWorkingDirectory;
    }

    public String getSmfId() {
        return this.smfId;
    }

    public String getSnaId() {
        return this.snaId;
    }

    public String getSubsystem() {
        return this.subsystem;
    }

    public String getJobname() {
        return this.jobname;
    }

    public String getQueueType() {
        return this.queueType;
    }

    public String getCcsid() {
        return this.ccsid;
    }

    public String getZosType() {
        return this.zosType;
    }

    public String getZosRelease() {
        return this.zosRelease;
    }

    public String getAsid() {
        return this.asid;
    }

    public String getRegionId() {
        return this.regionId;
    }
}