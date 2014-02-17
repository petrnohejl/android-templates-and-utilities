package com.example.client.ssl;

import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import com.example.ExampleApplication;
import com.example.ExampleConfig;
import com.example.R;


public class SelfSignedSSLUtility
{
	public static void setupSSLConnection(HttpsURLConnection connection, URL requestUrl) throws GeneralSecurityException
	{
		SSLContext sslContext = createSSLContext();
		HostnameVerifier sslHostnameVerifier = createSSLHostnameVerifier(requestUrl.getHost());
		
		connection.setSSLSocketFactory(sslContext.getSocketFactory());
		connection.setHostnameVerifier(sslHostnameVerifier);
	}
	
	
	public static SSLContext createSSLContext() throws GeneralSecurityException
	{
		KeyStore keyStore = loadKeyStore();
		
		SelfSignedTrustManager selfSignedTrustManager = new SelfSignedTrustManager(keyStore);
		TrustManager[] tms = new TrustManager[] { selfSignedTrustManager };
		
		KeyManager[] kms = null;
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(keyStore, ExampleConfig.SSL_KEYSTORE_PASSWORD.toCharArray());
		kms = kmf.getKeyManagers();
		
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(kms, tms, new SecureRandom());
		return context;
	}
	
	
	public static HostnameVerifier createSSLHostnameVerifier(final String apiHostname)
	{
		HostnameVerifier hostnameVerifier = new HostnameVerifier()
		{
			@Override
			public boolean verify(String hostname, SSLSession session)
			{
				//Logcat.d("HostnameVerifier.verify(): " + hostname + " / " + apiHostname);
				return hostname.equals(apiHostname);
			}
		};
		return hostnameVerifier;
	}
	
	
	public static KeyStore loadKeyStore()
	{
		try
		{
			KeyStore keyStore = KeyStore.getInstance("BKS");
			InputStream in = ExampleApplication.getContext().getResources().openRawResource(R.raw.cert_keystore);
			try
			{
				keyStore.load(in, ExampleConfig.SSL_KEYSTORE_PASSWORD.toCharArray());
			}
			finally
			{
				in.close();
			}
			return keyStore;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
