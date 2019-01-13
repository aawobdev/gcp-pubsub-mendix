package gcpiot.impl;


import java.util.HashMap;
import java.util.Map;

import com.mendix.core.Core;

public class Agent
{	
	public static final String LogNode = gcpiot.proxies.constants.Constants.getLogNode();

	private static Map<String, GCPSubscriber> gcpSubscribers;
	
	private static Map<String, GCPPublisher> gcpPublishers;
	
	private static Map<String, GCPRegistry> gcpRegistries;
	
	public Map<String, GCPSubscriber> getGcpSubscribers() {
		Core.getLogger(Agent.LogNode).debug("Current Subs: "+gcpSubscribers.toString());
		return gcpSubscribers;
	}
	
	public Map<String, GCPRegistry> getGcpRegistries() {
		Core.getLogger(Agent.LogNode).debug("Current Subs: "+gcpSubscribers.toString());
		return gcpRegistries;
	}
	
	public Map<String, GCPPublisher> getGcpPublishers() {
		Core.getLogger(Agent.LogNode).debug("Current Pubs: "+gcpPublishers.toString());
		return gcpPublishers;
	}
	
	public void initialize()
	{
		if(gcpSubscribers == null)
		{
			Core.getLogger(Agent.LogNode).debug("New Sub Hasmap created");
			gcpSubscribers = new HashMap<String, GCPSubscriber>();
		}
		
		if(gcpPublishers == null)
		{
			Core.getLogger(Agent.LogNode).debug("New Pub Hasmap created");
			gcpPublishers = new HashMap<String, GCPPublisher>();
		}
		
		if(gcpRegistries == null)
		{
			Core.getLogger(Agent.LogNode).debug("New Registries Hasmap created");
			gcpRegistries = new HashMap<String, GCPRegistry>();
		}
	}
	
	
	public GCPSubscriber getSubscriber(String key) {
		return gcpSubscribers.get(key);
	}
	
	public GCPPublisher getPublisher(String key) {
		return gcpPublishers.get(key);
	}
	public GCPRegistry getRegistry(String key) {
		return gcpRegistries.get(key);
	}
	
	public void pushSub(GCPSubscriber subscriber) {
		gcpSubscribers.put(subscriber.getSubscriberId(), subscriber);
	}
	
	public void pushPub(GCPPublisher publisher) {
		gcpPublishers.put(publisher.getPublisherId(), publisher);
	}
	
	public void pushReg(GCPRegistry registry) {
		gcpRegistries.put(registry.getProjectId()+'-'+registry.getRegionId(), registry);
	}
	
	public void pullSub(String key) {
		gcpSubscribers.remove(key);
	}
	
	public void pullReg(String key) {
		gcpRegistries.remove(key);
	}
	
	public void pullPub(String key) {
		gcpPublishers.remove(key);
	}
}
