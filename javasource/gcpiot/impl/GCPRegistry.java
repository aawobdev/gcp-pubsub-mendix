package gcpiot.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.iot.v1.DeviceManagerClient;
import com.google.cloud.iot.v1.DeviceManagerSettings;
import com.google.cloud.iot.v1.DeviceName;
import com.google.cloud.iot.v1.SendCommandToDeviceRequest;
import com.google.cloud.iot.v1.SendCommandToDeviceResponse;
import com.google.protobuf.ByteString;
import com.mendix.core.Core;

public class GCPRegistry {
	
	private static Map<String, Device> gcpDevices;
	private String projectId;
	private String registryId;
	private String regionId;
	private DeviceManagerClient client;
	private DeviceManagerSettings settings;
	private InputStream authStream;
	
	public GCPRegistry(String projectId, String registryId, String regionId, InputStream inputStream) {
		super();
		this.projectId = projectId;
		this.registryId = registryId;
		this.regionId = regionId;
		this.authStream = inputStream;
	}
	
	public GCPRegistry startClient() throws IOException {
		
		if(gcpDevices == null)
		{
			Core.getLogger(Agent.LogNode).debug("New Registries Hasmap created");
			gcpDevices = new HashMap<String, Device>();
		}
		
		settings = DeviceManagerSettings.newBuilder().setCredentialsProvider(new CredentialProvider(authStream)).build();
		client = DeviceManagerClient.create(settings);
		return this;
	}
	
	public SendCommandToDeviceResponse sendCommand(String deviceId, String subFolder,String command) {
		
		Core.getLogger(Agent.LogNode).debug("Attempting command: "+ deviceId + "," + subFolder + "," + command);
		
		Device device = getDevice(deviceId);
		if (device == null)
		{
			device = new Device(deviceId, projectId, registryId, regionId, subFolder);
		}
		
		DeviceName name = DeviceName.of(projectId, regionId, registryId, deviceId);
	      ByteString binaryData = ByteString.copyFromUtf8(command);
	      SendCommandToDeviceRequest request = SendCommandToDeviceRequest.newBuilder()
	        .setName(name.toString())
	        .setBinaryData(binaryData)
	        .build();
		
		return client.sendCommandToDevice(request);
	}
	
	public Device getDevice(String key) {
		return gcpDevices.get(key);
	}
	
	public void push(Device device){
		gcpDevices.put(device.getDeviceId(), device);
	}
	
	public void pull(String key) {
		gcpDevices.remove(key);
	}
	
	public static Map<String, Device> getDevices() {
		return gcpDevices;
	}
	public static void setDevices(Map<String, Device> gcpDevices) {
		GCPRegistry.gcpDevices = gcpDevices;
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



}