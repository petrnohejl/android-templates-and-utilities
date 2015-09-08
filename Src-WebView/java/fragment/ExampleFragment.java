package com.example.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.R;
import com.example.view.StatefulLayout;


public class ExampleFragment extends Fragment
{
	private View mRootView;
	private StatefulLayout mStatefulLayout;
	private String mUrl = "about:blank";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}
	
	
	private void bindData()
	{
		// reference
		final WebView webView = (WebView) mRootView.findViewById(R.id.fragment_example_webview);
		
		// webview settings
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginState(WebSettings.PluginState.ON);
		webView.setBackgroundColor(getResources().getColor(R.color.global_bg_front));
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // fixes scrollbar on Froyo
		webView.setWebChromeClient(new WebChromeClient()); // http://stackoverflow.com/questions/8541443/whats-causing-this-nullpointerexception
		webView.setWebViewClient(new WebViewClient()
		{
			@Override
			public void onPageFinished(WebView view, String url)
			{
				if(getActivity()!=null)
				{
					Toast.makeText(getActivity(), mUrl, Toast.LENGTH_LONG).show();
					mStatefulLayout.showContent();
				}
			}
			
			
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
			{
				if(getActivity()!=null)
				{
					webView.loadUrl("about:blank");
					Toast.makeText(getActivity(), errorCode + ": " + description, Toast.LENGTH_LONG).show();
					mStatefulLayout.showEmpty();
				}
			}
			
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				if(url!=null && (url.startsWith("http://") || url.startsWith("https://")))
				{
					view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					return true;
				}
				else
				{
					return false;
				}
			}
		});
		webView.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if(event.getAction() == KeyEvent.ACTION_DOWN)
				{
					WebView webView = (WebView) v;
					
					switch(keyCode)
					{
						case KeyEvent.KEYCODE_BACK:
							if(webView.canGoBack())
							{
								webView.goBack();
								return true;
							}
							break;
					}
				}
				
				return false;
			}
		});
		webView.requestFocus(View.FOCUS_DOWN); // http://android24hours.blogspot.cz/2011/12/android-soft-keyboard-not-showing-on.html
		webView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_UP:
						if(!v.hasFocus())
						{
							v.requestFocus();
						}
						break;
				}
				
				return false;
			}
		});
		
		// load web url
		mStatefulLayout.showProgress();
		webView.loadUrl(mUrl);
	}


	private void controlBack()
	{
		final WebView webView = (WebView) mRootView.findViewById(R.id.fragment_example_webview);
		if(webView.canGoBack()) webView.goBack();
	}
	
	
	private void controlForward()
	{
		final WebView webView = (WebView) mRootView.findViewById(R.id.fragment_example_webview);
		if(webView.canGoForward()) webView.goForward();
	}
	
	
	private void controlStop()
	{
		final WebView webView = (WebView) mRootView.findViewById(R.id.fragment_example_webview);
		webView.stopLoading();
	}
	
	
	private void controlReload()
	{
		final WebView webView = (WebView) mRootView.findViewById(R.id.fragment_example_webview);
		webView.reload();
	}
}
