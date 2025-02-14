// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package gcp.actions;

import java.util.ArrayList;
import java.util.List;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import gcp.impl.CredentialProvider;
import gcp.impl.FileHelper;
import gcp.impl.Logger;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class J_GetDatasets extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private IMendixObject __CredentialsFile;
	private system.proxies.FileDocument CredentialsFile;
	private java.lang.String DatasetType;

	public J_GetDatasets(IContext context, IMendixObject CredentialsFile, java.lang.String DatasetType)
	{
		super(context);
		this.__CredentialsFile = CredentialsFile;
		this.DatasetType = DatasetType;
	}

	@Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		this.CredentialsFile = __CredentialsFile == null ? null : system.proxies.FileDocument.initialize(getContext(), __CredentialsFile);

		// BEGIN USER CODE
		IContext ctx = getContext();
		CredentialProvider credProvider = new CredentialProvider(FileHelper.getCredentialsFileStream(ctx, __CredentialsFile));
		
		BigQuery bigquery =
		        BigQueryOptions.newBuilder().setCredentials(credProvider.getCredentials()).build().getService();
		
		List<IMendixObject> obList = new ArrayList<IMendixObject>();
		
		Logger.debug("Datasets");
		for (Dataset dataset : bigquery.listDatasets().iterateAll()) {
		      IMendixObject newOb = Core.instantiate(ctx, DatasetType);
		      try {
		    	  newOb.setValue(ctx, "Name",  dataset.getDatasetId().getDataset());
		    	  obList.add(newOb);
		      }
		      catch (Exception e) {
		    	  Logger.error("Could not set member:Name, " + e.getMessage());
		      }
		      
		    }
		
		return obList;
		
		
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "J_GetDatasets";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
