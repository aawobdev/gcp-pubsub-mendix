package system;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.mendix.core.actionmanagement.IActionRegistrator;

@Component(immediate = true)
public class UserActionsRegistrar
{
  @Reference
  public void registerActions(IActionRegistrator registrator)
  {
    registrator.bundleComponentLoaded();
    registrator.registerUserAction(gcp.actions.J_AddListenerToSubscription.class);
    registrator.registerUserAction(gcp.actions.J_LoadCredentialFile.class);
    registrator.registerUserAction(gcp.actions.J_PublishToTopic.class);
    registrator.registerUserAction(gcp.actions.J_RemoveListenerFromSubscription.class);
    registrator.registerUserAction(gcp.actions.J_StartPublisherAgent.class);
    registrator.registerUserAction(gcp.actions.J_StartRegistryAgent.class);
    registrator.registerUserAction(gcp.actions.J_StartSubscriberAgent.class);
    registrator.registerUserAction(system.actions.VerifyPassword.class);
  }
}
