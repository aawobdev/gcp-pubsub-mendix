package gcpiot.impl;

import java.io.IOException;
import java.io.InputStream;

import com.google.cloud.iot.v1.DeviceManagerClient;
import com.google.cloud.iot.v1.DeviceManagerSettings;
import com.google.cloud.iot.v1.DeviceName;
import com.google.cloud.iot.v1.SendCommandToDeviceRequest;
import com.google.cloud.iot.v1.SendCommandToDeviceResponse;
import com.google.protobuf.ByteString;

public class Device {
	
	private String deviceId;
	private String projectId;
	private String registryId;
	private String regionId;
	private String subFolder;

	
	public Device(String deviceId, String projectId, String registryId, String regionId, String subFolder) {
		this.deviceId = deviceId;
		this.projectId = projectId;
		this.registryId = registryId;
		this.regionId = regionId;
		this.subFolder = subFolder;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getRegistryId() {
		return registryId;
	}
	public void setRegistryId(String registryId) {
		this.registryId = registryId;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getSubFolder() {
		return subFolder;
	}
	public void setSubFolder(String subFolder) {
		this.subFolder = subFolder;
	}
	
}
