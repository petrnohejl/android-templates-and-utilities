package com.example.fragment;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jackson.JsonParseException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.R;
import com.example.adapter.ExampleAdapter;
import com.example.client.ApiCall;
import com.example.client.OnApiCallListener;
import com.example.client.RequestManager;
import com.example.client.ResponseStatus;
import com.example.client.request.ExampleRequest;
import com.example.client.response.ExampleResponse;
import com.example.client.response.Response;
import com.example.entity.Product;
import com.example.task.TaskSherlockListFragment;
import com.example.utility.Logcat;
import com.example.utility.NetworkManager;
import com.example.utility.ViewState;


public class ExampleFragment extends TaskSherlockListFragment implements OnApiCallListener
{
	private final int LAZY_LOADING_TAKE = 16;
	private final int LAZY_LOADING_OFFSET = 4;
	private final int LAZY_LOADING_MAX = LAZY_LOADING_TAKE * 10;
	private final String EXTRA_REFRESH = "refresh";
	
	private boolean mLazyLoading = false;
	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;
	private View mFooterView;
	private ExampleAdapter mAdapter;
	private RequestManager mRequestManager = new RequestManager();
	
	private ArrayList<Product> mProductList = new ArrayList<Product>();

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// load and show data
		if(mViewState==null || mViewState==ViewState.OFFLINE)
		{
			loadData();
		}
		else if(mViewState==ViewState.CONTENT)
		{
			if(mProductList!=null) renderView();
			showList();
		}
		else if(mViewState==ViewState.PROGRESS)
		{
			showProgress();
		}

		// progress in action bar
		showActionBarProgress(mActionBarProgress);
		
		// lazy loading progress
		if(mLazyLoading) showLazyLoadingProgress(true);
	}
	
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		// stop adapter
		if(mAdapter!=null) mAdapter.stop();
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		// cancel async tasks
		mRequestManager.cancelAllRequests();
	}


	@Override
	public void onApiCallRespond(final ApiCall call, final ResponseStatus status, final Response response)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(call.getRequest().getClass().equals(ExampleRequest.class))
				{
					ExampleResponse exampleResponse = (ExampleResponse) response;
					
					// error
					if(exampleResponse.isError())
					{
						Logcat.d("Fragment.onApiCallRespond(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage() + 
								" / error / " + exampleResponse.getErrorType() + " / " + exampleResponse.getErrorMessage());

						// hide progress
						showLazyLoadingProgress(false);
						showList();

						// handle error
						handleError(exampleResponse.getErrorType(), exampleResponse.getErrorMessage());
					}
					
					// response
					else
					{
						Logcat.d("Fragment.onApiCallRespond(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage());

						// check meta data
						if(call.getRequest().getMetaData()!=null && call.getRequest().getMetaData().getBoolean(EXTRA_REFRESH, false))
						{
							// refresh
							mProductList.clear();
						}
						
						// get data
						Iterator<Product> iterator = exampleResponse.getProductList().iterator();
						while(iterator.hasNext())
						{
							Product product = iterator.next();
							mProductList.add(new Product(product));
						}
						
						// render view
						if(mLazyLoading && mViewState==ViewState.CONTENT && mAdapter!=null)
						{
							mAdapter.notifyDataSetChanged();
						}
						else
						{
							if(mProductList!=null) renderView();
						}

						// hide progress
						showLazyLoadingProgress(false);
						showList();
					}
				}
				
				// finish request
				mRequestManager.finishRequest(call);

				// hide progress in action bar
				if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
			}
		});
	}


	@Override
	public void onApiCallFail(final ApiCall call, final ResponseStatus status, final Exception exception)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(call.getRequest().getClass().equals(ExampleRequest.class))
				{
					Logcat.d("Fragment.onApiCallFail(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage() +
							" / " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
					
					// hide progress
					showLazyLoadingProgress(false);
					showList();

					// handle fail
					handleFail(exception);
				}
				
				// finish request
				mRequestManager.finishRequest(call);

				// hide progress in action bar
				if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
			}
		});
	}


	private void handleError(String errorType, String errorMessage)
	{
		// TODO: show dialog
	}


	private void handleFail(Exception exception)
	{
		int messageId;
		if(exception!=null && exception.getClass().equals(UnknownHostException.class)) messageId = R.string.global_apicall_unknown_host_toast;
		else if(exception!=null && exception.getClass().equals(SocketTimeoutException.class)) messageId = R.string.global_apicall_timeout_toast;
		else if(exception!=null && exception.getClass().equals(JsonParseException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else messageId = R.string.global_apicall_fail_toast;
		Toast.makeText(getActivity(), messageId, Toast.LENGTH_LONG).show();
	}
	
	
	private void loadData()
	{
		if(NetworkManager.isOnline(getActivity()))
		{
			if(!mRequestManager.hasRunningRequest(ExampleRequest.class))
			{
				// show progress
				showProgress();

				// show progress in action bar
				showActionBarProgress(true);
				
				// execute request
				ExampleRequest request = new ExampleRequest(0, LAZY_LOADING_TAKE);
				mRequestManager.executeRequest(request, this);
			}
		}
		else
		{
			showOffline();
		}
	}


	public void refreshData()
	{
		if(NetworkManager.isOnline(getActivity()))
		{
			if(!mRequestManager.hasRunningRequest(ExampleRequest.class))
			{
				// show progress in action bar
				showActionBarProgress(true);
				
				// meta data
				Bundle bundle = new Bundle();
				bundle.putBoolean(EXTRA_REFRESH, true);
				
				// execute request
				int take = (mProductList.size() <= LAZY_LOADING_MAX && mProductList.size() > 0) ? mProductList.size() : LAZY_LOADING_TAKE;
				ExampleRequest request = new ExampleRequest(0, take);
				request.setMetaData(bundle);
				mRequestManager.executeRequest(request, this);
			}
		}
		else
		{
			Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
		}
	}
	
	
	private void lazyLoadData()
	{
		if(NetworkManager.isOnline(getActivity()))
		{
			// show lazy loading progress
			showLazyLoadingProgress(true);
			
			// execute request
			ExampleRequest request = new ExampleRequest(mProductList.size(), LAZY_LOADING_TAKE);
			mRequestManager.executeRequest(request, this);
		}
	}
	
	
	private void showLazyLoadingProgress(boolean visible)
	{
		if(visible)
		{
			mLazyLoading = true;
		
			// show footer
			ListView listView = getListView();
			listView.addFooterView(mFooterView);
		}
		else
		{
			// hide footer
			ListView listView = getListView();
			listView.removeFooterView(mFooterView);
			
			mLazyLoading = false;
		}
	}
	
	
	private void showActionBarProgress(boolean visible)
	{
		// show progress in action bar
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}
	
	
	private void showList()
	{
		// show list container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		FrameLayout containerProgress = (FrameLayout) mRootView.findViewById(R.id.container_progress);
		FrameLayout containerOffline = (FrameLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.CONTENT;
	}
	
	
	private void showProgress()
	{
		// show progress container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		FrameLayout containerProgress = (FrameLayout) mRootView.findViewById(R.id.container_progress);
		FrameLayout containerOffline = (FrameLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.PROGRESS;
	}
	
	
	private void showOffline()
	{
		// show offline container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		FrameLayout containerProgress = (FrameLayout) mRootView.findViewById(R.id.container_progress);
		FrameLayout containerOffline = (FrameLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		mViewState = ViewState.OFFLINE;
	}

	
	private void renderView()
	{
		// TODO
	}
}
