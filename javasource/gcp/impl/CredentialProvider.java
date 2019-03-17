package gcp.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

public class CredentialProvider implements CredentialsProvider {

	private InputStream authStream;
	private boolean usePath=false;
	private String path;
	
	public CredentialProvider(InputStream inputStream) {
		this.authStream = inputStream;
		this.usePath=false;
	}
	
	public CredentialProvider(String inputPath) {
		this.path = inputPath;
		this.usePath=true;
	}
	
	@Override
	public Credentials getCredentials() throws IOException {
		// TODO Auto-generated method stub
		if (this.usePath)
		{
			return GoogleCredentials.fromStream(new FileInputStream(path))
				      .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			
		}
		else
		{
			return GoogleCredentials.fromStream(authStream)
				      .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		}
	}

}