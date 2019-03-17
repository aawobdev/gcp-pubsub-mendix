package gcp.impl;

import java.util.Map;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.pubsub.v1.PubsubMessage;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import gcp.impl.GCPSubscriber;

public class GCPMessageReceiver implements MessageReceiver {

	private GCPSubscriber subscription;

	public GCPMessageReceiver() {
		super();
		Logger.debug("Message Receiver registered");
	}

	@Override
	public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
		String attributes = null;
		try {
			attributes =  convertMapToJSONObjectString(message.getAttributesMap());
			String payload = message.getData().toStringUtf8();
			
			Logger.trace("RECEIVED: Attributes: " + attributes);
			Logger.debug("Subscription: " + subscription.getSubscriberId());
			Logger.debug("RECEIVED: Payload: " + payload);

			final ImmutableMap map = ImmutableMap.of("Attributes", attributes, "Payload", payload);

			IContext ctx = Core.createSystemContext();
			Logger.debug("MF: "+subscription.getMicroflowToExecute());

			Core.executeAsync(ctx, subscription.getMicroflowToExecute(), true, map);
		} catch (Exception e1) {
			Logger.error(e1.getMessage());
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