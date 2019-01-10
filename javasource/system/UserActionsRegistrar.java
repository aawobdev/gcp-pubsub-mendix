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
    registrator.registerUserAction(gcpiot.actions.getProjectId.class);
    registrator.registerUserAction(gcpiot.actions.publishToGCPTopic.class);
    registrator.registerUserAction(gcpiot.actions.startGCPAgent.class);
    registrator.registerUserAction(gcpiot.actions.stopGCPAgent.class);
    registrator.registerUserAction(gcpiot.actions.subscribeToGCPSub.class);
    registrator.registerUserAction(gcpiot.actions.unsubscribeFromGCPSub.class);
    registrator.registerUserAction(system.actions.VerifyPassword.class);
  }
}
