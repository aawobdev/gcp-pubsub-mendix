// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package gcp.actions;

import java.io.InputStream;
import com.google.cloud.iot.v1.SendCommandToDeviceResponse;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import gcp.impl.*;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class J_SendCommand extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String DeviceId;
	private java.lang.String ProjectId;
	private java.lang.String CloudRegion;
	private java.lang.String RegistryName;
	private java.lang.String Command;
	private java.lang.String SubFolder;
	private IMendixObject __CredentialsFile;
	private system.proxies.FileDocument CredentialsFile;

	public J_SendCommand(IContext context, java.lang.String DeviceId, java.lang.String ProjectId, java.lang.String CloudRegion, java.lang.String RegistryName, java.lang.String Command, java.lang.String SubFolder, IMendixObject CredentialsFile)
	{
		super(context);
		this.DeviceId = DeviceId;
		this.ProjectId = ProjectId;
		this.CloudRegion = CloudRegion;
		this.RegistryName = RegistryName;
		this.Command = Command;
		this.SubFolder = SubFolder;
		this.__CredentialsFile = CredentialsFile;
	}

	@Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.CredentialsFile = __CredentialsFile == null ? null : system.proxies.FileDocument.initialize(getContext(), __CredentialsFile);

		// BEGIN USER CODE

		GCPRegistry registry = null;
		
		if (registry == null)
		{

			InputStream credentialsInputFile = FileHelper.getCredentialsFileStream(this.getContext(),__CredentialsFile);			
			registry = new GCPRegistry(ProjectId, RegistryName,CloudRegion,credentialsInputFile);
			registry.startClient();

			Logger.debug("Registry: "+RegistryName);
		}
		
		SendCommandToDeviceResponse response = registry.sendCommand(DeviceId, SubFolder, Command);
	
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "J_SendCommand";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
