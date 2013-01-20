package com.example.client.ssl;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import android.util.Base64;


public class CertificateAuthorityTrustManager implements X509TrustManager
{
	public static final String CERT_SHA1 = "myhash="; // encoded representation of the trusted certificate
	
	private X509TrustManager mDefaultTrustManager = null;


	public CertificateAuthorityTrustManager() throws NoSuchAlgorithmException, KeyStoreException
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
	}
	
	
	@Override
	public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException
	{
		// check client certificate
		//Logcat.d("checkClientTrusted() with default trust manager...");
		mDefaultTrustManager.checkClientTrusted(certificates, authType);
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
				if(certHash.trim().equals(CERT_SHA1))
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
		
		if(expectedCertificateFound == false)
		{
			// if manual verification failed, throw exception
			throw new CertificateException("Expected certificate not found.");
		}
		
		// if manual verification successfull, check server certificate
		//Logcat.d("checkServerTrusted() with default trust manager...");
		mDefaultTrustManager.checkServerTrusted(certificates, authType);
	}
	
	
	@Override
	public X509Certificate[] getAcceptedIssuers()
	{
		return mDefaultTrustManager.getAcceptedIssuers();
	}
	
	
	private String SHA1Base64Encoded(byte[] data) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(data);
		byte messageDigest[] = md.digest();
		String encodedData = Base64.encodeToString(messageDigest, Base64.NO_WRAP);
		return encodedData;
	}
}
