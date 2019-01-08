package gcpiot.impl;


import java.util.HashMap;
import java.util.Map;

import com.google.auth.Credentials;
import com.mendix.core.Core;

public class GCPAgent
{	
	public static final String LogNode = gcpiot.proxies.constants.Constants.getLogNode();

	private static Map<String, GCPSubscriber> gcpSubscribers;
	
	public Map<String, GCPSubscriber> getGcpSubscribers() {
		Core.getLogger(GCPAgent.LogNode).warn("Current Subs: "+gcpSubscribers.toString());
		return gcpSubscribers;
	}
	
	public void initialize()
	{
		if(gcpSubscribers == null)
		{
			Core.getLogger(GCPAgent.LogNode).debug("New Hasmap created");
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
