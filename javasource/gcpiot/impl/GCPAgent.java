package gcpiot.impl;


import java.util.HashMap;
import java.util.Map;

import com.mendix.core.Core;

public class GCPAgent
{	
	public static final String LogNode = gcpiot.proxies.constants.Constants.getLogNode();

	private static Map<String, GCPSubscriber> gcpSubscribers;
	
	private static Map<String, GCPPublisher> gcpPublishers;
	
	public Map<String, GCPSubscriber> getGcpSubscribers() {
		Core.getLogger(GCPAgent.LogNode).warn("Current Subs: "+gcpSubscribers.toString());
		return gcpSubscribers;
	}
	
	public Map<String, GCPPublisher> getGcpPublishers() {
		Core.getLogger(GCPAgent.LogNode).warn("Current Pubs: "+gcpPublishers.toString());
		return gcpPublishers;
	}
	
	public void initialize()
	{
		if(gcpSubscribers == null)
		{
			Core.getLogger(GCPAgent.LogNode).debug("New Sub Hasmap created");
			gcpSubscribers = new HashMap<String, GCPSubscriber>();
		}
		
		if(gcpPublishers == null)
		{
			Core.getLogger(GCPAgent.LogNode).debug("New Pub Hasmap created");
			gcpPublishers = new HashMap<String, GCPPublisher>();
		}
	}
	
	
	public GCPSubscriber getSubscriber(String key) {
		return gcpSubscribers.get(key);
	}
	
	public GCPPublisher getPublisher(String key) {
		return gcpPublishers.get(key);
	}
	
	public void pushSub(GCPSubscriber subscriber) {
		gcpSubscribers.put(subscriber.getSubscriberId(), subscriber);
	}
	
	public void pushPub(GCPPublisher publisher) {
		gcpPublishers.put(publisher.getPublisherId(), publisher);
	}
	
	public void pullSub(String key) {
		gcpSubscribers.remove(key);
	}
	
	public void pullPub(String key) {
		gcpPublishers.remove(key);
	}
}
