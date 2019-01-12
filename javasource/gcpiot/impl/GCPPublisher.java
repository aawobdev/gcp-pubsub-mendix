package gcpiot.impl;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.stub.PublisherStub;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.mendix.core.Core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GCPPublisher {
	
	private String publisherId;
	private Publisher publisher;
	private ProjectTopicName topicName;
	private InputStream authStream;
	List<ApiFuture<String>> futures = null;
	
	public GCPPublisher(String publisherId, String topicId, String projectId, InputStream inputStream) {
		this.publisherId = publisherId;
		this.topicName = ProjectTopicName.of(projectId, topicId);
		this.authStream = inputStream;
		
		
		//No idea
		this.futures = new ArrayList<>();
		
		Core.getLogger(GCPAgent.LogNode).debug("Trying to create publisher...");
		
		try {
			this.publisher = Publisher.newBuilder(topicName).setCredentialsProvider(new GCPCredentialProvider(authStream)).build();
		}
		catch(Exception e)
		{
			Core.getLogger(GCPAgent.LogNode).error("Error creating publisher: " + e.getStackTrace());
		}
	}
	
	public ApiFuture<String> publish(String Message) throws Exception {
		try {
			// convert message to bytes
	        ByteString data = ByteString.copyFromUtf8(Message);
	        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
	            .setData(data)
	            .build();

	        // Schedule a message to be published. Messages are automatically batched.
	        ApiFuture<String> future = publisher.publish(pubsubMessage);
	        this.futures.add(future);
	        Core.getLogger(GCPAgent.LogNode).debug("Future: " + future.toString());
	        
			return future;
		}
		finally {
		      // Wait on any pending requests
		      List<String> messageIds = ApiFutures.allAsList(this.futures).get();

		      for (String messageId : messageIds) {
		        Core.getLogger(GCPAgent.LogNode).trace("Future: " + messageId);
		      }

		      if (publisher != null) {
		        // When finished with the publisher, shutdown to free up resources.
		    	  Core.getLogger(GCPAgent.LogNode).warn("NOT shutting down publisher");
		        //publisher.shutdown();
		      }
		}
	}
	
	public String getPublisherId()
	{
		return publisherId;
	}

}
