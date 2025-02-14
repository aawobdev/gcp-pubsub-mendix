// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package gcp.actions;

import java.util.Map;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import gcp.impl.GCPSubscriber;
import gcp.impl.Logger;
import gcp.impl.SubscribeAgent;

public class J_RemoveListenerFromSubscription extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String SubscriptionId;
	private java.lang.String ProjectId;

	public J_RemoveListenerFromSubscription(IContext context, java.lang.String SubscriptionId, java.lang.String ProjectId)
	{
		super(context);
		this.SubscriptionId = SubscriptionId;
		this.ProjectId = ProjectId;
	}

	@Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
		Logger.debug("Trying to unsubscribe: " + SubscriptionId);
		SubscribeAgent agent = new SubscribeAgent();
		agent.initialize();
		Map<String, GCPSubscriber> gsubs = agent.getGcpSubscribers();
		GCPSubscriber sub = gsubs.get(SubscriptionId+"-"+ProjectId);
		
		if(sub == null)
		{
			Logger.warn("No Subscriber found!");
		}
		else
		{
			Logger.debug("Subscriber to stop: " + sub.getSubscriber());
		}

		try {
			Logger.debug("Trying to stop: " + sub.getSubscriber());
			sub.stop();
		}
		catch(Exception e)
		{
			Logger.error("Error stopping sub: "+ e.getMessage());
		}
		
		agent.pullSub(SubscriptionId+"-"+sub.getProjectId());
		Logger.debug("Listening to: "+agent.getGcpSubscribers().size() + " Subscriptions");

		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "J_RemoveListenerFromSubscription";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
