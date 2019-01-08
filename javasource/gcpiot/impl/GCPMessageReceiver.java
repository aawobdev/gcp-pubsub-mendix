package gcpiot.impl;

import java.util.Map;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.pubsub.v1.PubsubMessage;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;


public class GCPMessageReceiver implements MessageReceiver {

	private GCPSubscriber subscription;

	public GCPMessageReceiver() {
		super();
		Core.getLogger(GCPAgent.LogNode).debug("Message Receiver registered");
	}

	@Override
	public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
		String attributes = null;
		try {
			attributes =  convertMapToJsonObjectString(message.getAttributesMap());
			//Core.getLogger(GCPAgent.LogNode).debug("Attributes: " + attributes);
			String payload = message.getData().toStringUtf8();
			Core.getLogger(GCPAgent.LogNode).debug("Subscription: " + subscription.getSubscriberId() );

			Core.getLogger(GCPAgent.LogNode).debug("Payload: " + payload);

			final ImmutableMap map = ImmutableMap.of("Attributes", attributes, "Payload", payload);
			//Core.getLogger(GCPAgent.LogNode).debug("Params: " + map);

			IContext ctx = Core.createSystemContext();
			Core.getLogger(GCPAgent.LogNode).debug("MF: "+subscription.getMicroflowToExecute());

			Core.executeAsync(ctx, subscription.getMicroflowToExecute(), true, map);
		} catch (Exception e1) {
			Core.getLogger(GCPAgent.LogNode).error(e1.getMessage());
		}
		consumer.ack();
	}

	public GCPSubscriber getSubber() {
		return subscription;
	}

	public void setSubber(GCPSubscriber subscription) {
		this.subscription = subscription;
	}

	public String convertMapToJsonObjectString(Map<String, String> map) throws Exception
	{
		JsonObject o =  new JsonObject();
		//Core.getLogger(GCPAgent.LogNode).debug( map.size() + " Attributes");
		
		o.addProperty("subscriptionId", getSubber().getSubscriptionName().getSubscription());
		
		for (String key : map.keySet())
		{
			//Core.getLogger(GCPAgent.LogNode).debug("Key: "+key);
			String attr = map.get(key);
			o.addProperty(key, attr);
		}

		return o.toString();

	}
}