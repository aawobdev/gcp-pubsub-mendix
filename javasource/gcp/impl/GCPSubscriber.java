package gcp.impl;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.auth.Credentials;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.mendix.core.Core;
import gcp.impl.CredentialProvider;
import gcp.impl.GCPMessageReceiver;


public class GCPSubscriber {

	private String subscriberId;
	private String projectId;
	private Subscriber subscriber;
	private Credentials credentials;
	private ProjectSubscriptionName subscriptionName;
	private MessageReceiver receiver;
	private String microflowToExecute;
	private ExecutorService pool;
	private InputStream authStream;
	
	public GCPSubscriber(String subName, String projectId, InputStream inputStream, String mf) {
		this.subscriptionName = ProjectSubscriptionName.of(projectId, subName);
		 Logger.debug("ProjectSubscriptionName: " + this.subscriptionName.getProject() + "-" + this.subscriptionName.getSubscription());
		this.projectId = projectId;
		this.subscriberId = this.subscriptionName.getSubscription() + "-" + this.projectId;
		this.microflowToExecute = mf;
		this.authStream = inputStream;
	}
	
	public GCPSubscriber subscribe() {
		this.receiver = null;
		
		try {
	
			GCPMessageReceiver receiver =  new GCPMessageReceiver();
			this.receiver = receiver;
			setSubscriber(Subscriber.newBuilder(getSubscriptionName(), receiver).setCredentialsProvider(new CredentialProvider(this.authStream)).build());
			receiver.setSubber(this);
			
		} 

		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public GCPSubscriber start() {
		
		this.pool = Executors.newCachedThreadPool();
		getSubscriber().addListener(
		        new Subscriber.Listener() {
		          public void failed(Subscriber.State from, Throwable failure) {
		        	  //TODO
		        	  Logger.error("Listen Failure: " + from + " " + failure.getMessage());
		          }
		        },
		        this.pool);
		getSubscriber().startAsync().awaitRunning();//.awaitRunning();
		Logger.debug("State: "+ getSubscriber().state());
		
		return this;
	}
	
	public void stop() {
	    
		getSubscriber().stopAsync().awaitTerminated();
		//this.pool.shutdown();

	}
	
	public String getMicroflowToExecute() {
		return this.microflowToExecute;
	}
	
	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriptionId) {
		this.subscriberId = subscriptionId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public InputStream getAuthPath() {
		return authStream;
	}

	public void setAuthPath(InputStream authStream) {
		this.authStream = authStream;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public ProjectSubscriptionName getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(ProjectSubscriptionName subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public MessageReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(MessageReceiver receiver) {
		this.receiver = receiver;
	}
}