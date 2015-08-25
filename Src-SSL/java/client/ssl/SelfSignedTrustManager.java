package com.example.client.ssl;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import android.util.Base64;

import com.example.ExampleConfig;


// useful articles about trusting SSL certificates:
// http://blog.crazybob.org/2010/02/android-trusting-ssl-certificates.html
// http://stackoverflow.com/questions/2642777/trusting-all-certificates-using-httpclient-over-https/6378872#6378872
// http://nelenkov.blogspot.cz/2011/12/using-custom-certificate-trust-store-on.html
public class SelfSignedTrustManager implements X509TrustManager
{
	private X509TrustManager mDefaultTrustManager = null;
	private X509TrustManager mLocalTrustManager = null;
	private X509Certificate[] mAcceptedIssuers;


	public SelfSignedTrustManager(KeyStore localKeyStore) throws NoSuchAlgorithmException, KeyStoreException
	{
		super();
		
		// init factory
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		KeyStore keyStore = null;
		trustManagerFactory.init(keyStore);
		
		// create default trust manager checking certificates signed with certificate authority
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
		if(trustManagers.length == 0)
		{
			throw new NoSuchAlgorithmException("No trust manager found.");
		}
		mDefaultTrustManager = (X509TrustManager) trustManagers[0];
		
		// create local trust manager checking self signed certificate
		mLocalTrustManager = new LocalStoreX509TrustManager(localKeyStore);
		
		// create accepted issuers
		List<X509Certificate> allIssuers = new ArrayList<>();
//		for(X509Certificate cert:mDefaultTrustManager.getAcceptedIssuers())
//		{
//			// warning, this cycle takes a lot of time, maybe more than 1 sec
//			allIssuers.add(cert);
//		}
		for(X509Certificate cert:mLocalTrustManager.getAcceptedIssuers())
		{
			allIssuers.add(cert);
		}
		mAcceptedIssuers = allIssuers.toArray(new X509Certificate[allIssuers.size()]);
	}
	
	
	@Override
	public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException
	{
		// check client certificate, first self signed, then default
		try
		{
			//Logcat.d("localTrustManager");
			mLocalTrustManager.checkClientTrusted(certificates, authType);
		}
		catch(CertificateException e)
		{
			//Logcat.d("defaultTrustManager");
			mDefaultTrustManager.checkClientTrusted(certificates, authType);
		}
	}
	
	
	@Override
	public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException
	{
		boolean expectedCertificateFound = false;
		
		// manually verify certificate comparing encoded representation of the certificate
		for(X509Certificate certificate:certificates)
		{
			try
			{
				String certHash = SHA1Base64Encoded(certificate.getEncoded());
				//Logcat.d("certificate public key = " + certificate.getPublicKey().toString().substring(0, 100) + " ...");
				//Logcat.d("certificate hash = " + certHash);
				//Logcat.d("---------------------------------------");
				if(certHash.trim().equals(ExampleConfig.SSL_CERTIFICATE_SHA1))
				{
					// manual verification successfull
					expectedCertificateFound = true;
				}
			}
			catch(Exception e)
			{
				// manual verification failed
				throw new CertificateException("Failed to compare hashes of certificates.");
			}
		}
		
		if(!expectedCertificateFound)
		{
			// if manual verification failed, throw exception
			throw new CertificateException("Expected certificate not found.");
		}
		
		// if manual verification successfull, check server certificate, first self signed, then default
		try
		{
			//Logcat.d("localTrustManager");
			mLocalTrustManager.checkServerTrusted(certificates, authType);
		}
		catch(CertificateException e)
		{
			//Logcat.d("defaultTrustManager");
			mDefaultTrustManager.checkServerTrusted(certificates, authType);
		}
	}
	
	
	@Override
	public X509Certificate[] getAcceptedIssuers()
	{
		return mAcceptedIssuers;
	}
	
	
	private String SHA1Base64Encoded(byte[] data) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(data);
		byte messageDigest[] = md.digest();
		return Base64.encodeToString(messageDigest, Base64.NO_WRAP);
	}
	
	
	static class LocalStoreX509TrustManager implements X509TrustManager
	{
		private X509TrustManager trustManager;
		
		
		public LocalStoreX509TrustManager(KeyStore localTrustStore)
		{
			try
			{
				TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				tmf.init(localTrustStore);
				trustManager = findX509TrustManager(tmf);
				if(trustManager == null)
				{
					throw new IllegalStateException("Couldn't find X509TrustManager.");
				}
			}
			catch(GeneralSecurityException e)
			{
				throw new RuntimeException(e);
			}
		}
		
		
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
		{
			trustManager.checkClientTrusted(chain, authType);
		}
		
		
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
		{
			trustManager.checkServerTrusted(chain, authType);
		}
		
		
		@Override
		public X509Certificate[] getAcceptedIssuers()
		{
			return trustManager.getAcceptedIssuers();
		}
	}
	
	
	static X509TrustManager findX509TrustManager(TrustManagerFactory tmf)
	{
		TrustManager tms[] = tmf.getTrustManagers();
		for(int i = 0; i < tms.length; i++)
		{
			if(tms[i] instanceof X509TrustManager)
			{
				return (X509TrustManager) tms[i];
			}
		}
		return null;
	}
}
