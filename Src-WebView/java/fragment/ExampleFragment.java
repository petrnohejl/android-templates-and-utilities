package com.example.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
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
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setAppCachePath(getActivity().getCacheDir().getAbsolutePath());
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setDatabaseEnabled(true);
		webView.getSettings().setGeolocationEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(false);

		// webview style
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // fixes scrollbar on Froyo

		// webview hardware acceleration
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
		else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}

		// webview chrome client
		webView.setWebChromeClient(new WebChromeClient()); // http://stackoverflow.com/questions/8541443/whats-causing-this-nullpointerexception

		// webview client
		webView.setWebViewClient(new MyWebViewClient());

		// webview key listener
		webView.setOnKeyListener(new WebViewOnKeyListener());

		// webview touch listener
		webView.requestFocus(View.FOCUS_DOWN); // http://android24hours.blogspot.cz/2011/12/android-soft-keyboard-not-showing-on.html
		webView.setOnTouchListener(new WebViewOnTouchListener());

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


	private class MyWebViewClient extends WebViewClient
	{
		@Override
		public void onPageFinished(WebView view, String url)
		{
			if(getActivity() != null)
			{
				Toast.makeText(getActivity(), mUrl, Toast.LENGTH_LONG).show();
				mStatefulLayout.showContent();
			}
		}


		@SuppressWarnings("deprecation")
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
		{
			if(getActivity() != null)
			{
				final WebView webView = (WebView) mRootView.findViewById(R.id.fragment_example_webview);
				webView.loadUrl("about:blank");
				Toast.makeText(getActivity(), errorCode + ": " + description, Toast.LENGTH_LONG).show();
				mStatefulLayout.showEmpty();
			}
		}


		@TargetApi(Build.VERSION_CODES.M)
		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
		{
			// forward to deprecated method
			onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
		}


		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			if(url != null && (url.startsWith("http://") || url.startsWith("https://")))
			{
				view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
				return true;
			}
			else
			{
				return false;
			}
		}
	}


	private class WebViewOnKeyListener implements OnKeyListener
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
	}


	private class WebViewOnTouchListener implements OnTouchListener
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
	}
}
