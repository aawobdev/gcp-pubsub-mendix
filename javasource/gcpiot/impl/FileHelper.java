package gcpiot.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class FileHelper {
	public static InputStream getCredentialsFileStream(IContext context,IMendixObject credentialsFile) throws IOException {
		
		InputStream fis = Core.getFileDocumentContent(context, credentialsFile);
		
		return fis;

	}

	public static String getProjectId(IContext context,IMendixObject credentialsFile) throws IOException{
		
			InputStream stream = getCredentialsFileStream(context, credentialsFile);
			
			JsonParser p = new JsonParser();
			JsonObject j = (JsonObject)p.parse(new InputStreamReader(stream));
			
			String ProjectId = j.get("project_id").getAsString();
			Core.getLogger(Agent.LogNode).debug("ProjectId From Credentials: "+ProjectId);
			
			return ProjectId;
		}
	}
