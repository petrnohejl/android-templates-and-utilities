package com.example.client.ssl;

import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;


public class CertificateAuthoritySslUtility
{
	public static void setSslConnection(HttpsURLConnection connection, URL requestUrl) throws GeneralSecurityException
	{
		SSLContext sslContext = createSslContext();
		HostnameVerifier sslHostnameVerifier = createSslHostnameVerifier(requestUrl.getHost());
		
		connection.setSSLSocketFactory(sslContext.getSocketFactory());
		connection.setHostnameVerifier(sslHostnameVerifier);
		//HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	}
	
	
	public static SSLContext createSslContext() throws GeneralSecurityException
	{
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new X509TrustManager[] { new CertificateAuthorityTrustManager() }, new SecureRandom());
		return context;
	}
	
	
	public static HostnameVerifier createSslHostnameVerifier(final String apiHostname)
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
}
