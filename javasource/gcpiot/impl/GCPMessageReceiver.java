package gcpiot.impl;

import java.util.Map;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.pubsub.v1.PubsubMessage;
import com.mendix.core.Core;
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
			attributes =  convertMapToJSONObjectString(message.getAttributesMap());
			String payload = message.getData().toStringUtf8();
			
			Core.getLogger(GCPAgent.LogNode).trace("RECEIVED: Attributes: " + attributes);
			Core.getLogger(GCPAgent.LogNode).debug("Subscription: " + subscription.getSubscriberId());
			Core.getLogger(GCPAgent.LogNode).debug("RECEIVED: Payload: " + payload);

			final ImmutableMap map = ImmutableMap.of("Attributes", attributes, "Payload", payload);

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

	public String convertMapToJSONObjectString(Map<String, String> map) throws Exception
	{
		JsonObject o =  new JsonObject();
		o.addProperty("subscriptionId", getSubber().getSubscriptionName().getSubscription());
		
		for (String key : map.keySet())
		{
			String attr = map.get(key);
			o.addProperty(key, attr);
		}

		return o.toString();

	}
}