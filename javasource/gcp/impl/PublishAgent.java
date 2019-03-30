package gcp.impl;

import java.util.HashMap;
import java.util.Map;
import gcp.impl.GCPPublisher;

public class PublishAgent {	

	private static Map<String, GCPPublisher> gcpPublishers;
	
	public Map<String, GCPPublisher> getGcpPublishers() {
		Logger.debug("Current Pubs: "+gcpPublishers.toString());
		return gcpPublishers;
	}
	
	public void initialize()
	{		
		if(gcpPublishers == null)
		{
			Logger.debug("New Pub Hasmap created");
			gcpPublishers = new HashMap<String, GCPPublisher>();
		}
	}
	
	public GCPPublisher getPublisher(String key) {
		return gcpPublishers.get(key);
	}
	
	public void pushPub(GCPPublisher publisher) {
		gcpPublishers.put(publisher.getPublisherId(), publisher);
	}
	
	public void pullPub(String key) {
		gcpPublishers.remove(key);
	}
}
