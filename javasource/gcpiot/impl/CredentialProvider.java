package gcpiot.impl;

import java.io.IOException;
import java.io.InputStream;
import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

public class CredentialProvider implements CredentialsProvider {

	private InputStream authStream;
	
	public CredentialProvider(InputStream inputStream) {
		this.authStream = inputStream;
	}
	
	@Override
	public Credentials getCredentials() throws IOException {
		// TODO Auto-generated method stub
		return GoogleCredentials.fromStream(authStream)
			      .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

	}

}
