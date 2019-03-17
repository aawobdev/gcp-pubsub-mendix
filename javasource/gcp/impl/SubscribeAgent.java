package gcp.impl;

import java.util.HashMap;
import java.util.Map;

import gcp.impl.GCPSubscriber;

public class SubscribeAgent {	

	private static Map<String, GCPSubscriber> gcpSubscribers;

	
	public Map<String, GCPSubscriber> getGcpSubscribers() {
		Logger.debug("Current Subs: "+gcpSubscribers.toString());
		return gcpSubscribers;
	}
	
	public void initialize()
	{
		if(gcpSubscribers == null)
		{
			Logger.debug("New Sub Hasmap created");
			gcpSubscribers = new HashMap<String, GCPSubscriber>();
		}
	}
	
	public GCPSubscriber getSubscriber(String key) {
		return gcpSubscribers.get(key);
	}
	
	public void pushSub(GCPSubscriber subscriber) {
		gcpSubscribers.put(subscriber.getSubscriberId(), subscriber);
	}
	
	public void pullSub(String key) {
		gcpSubscribers.remove(key);
	}
}
