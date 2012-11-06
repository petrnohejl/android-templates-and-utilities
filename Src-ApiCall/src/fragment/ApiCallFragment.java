package com.example.fragment;

import java.util.ArrayList;
import java.util.Iterator;

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
import com.example.client.entity.Message;
import com.example.client.request.ExampleRequest;
import com.example.client.response.ExampleResponse;
import com.example.client.response.Response;
import com.example.task.TaskSherlockListFragment;
import com.example.utility.Logcat;
import com.example.utility.ViewState;


public class ApiCallFragment extends TaskSherlockListFragment implements OnApiCallListener
{
	private final int LAZY_LOADING_TAKE = 16;
	private final int LAZY_LOADING_OFFSET = 4;
	private final int LAZY_LOADING_MAX = LAZY_LOADING_TAKE * 10;
	private final String EXTRA_REFRESH = "refresh";
	
	private boolean mLazyLoading = false;
	private boolean mActionBarProgress = false;
	private ViewState.Visibility mViewState = null;
	private View mRootView;
	private View mFooterView;
	private ExampleAdapter mAdapter;
	private RequestManager mRequestManager = new RequestManager();
	
	private ArrayList<Message> mMessages = new ArrayList<Message>();

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.layout_example, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// load and show data
		if(mViewState==null || mViewState==ViewState.Visibility.OFFLINE)
		{
			loadData();
		}
		else if(mViewState==ViewState.Visibility.CONTENT)
		{
			if(mMessages!=null) renderView();
			showList();
		}
		else if(mViewState==ViewState.Visibility.PROGRESS)
		{
			showProgress();
		}

		// progress in action bar
		showActionBarProgress(mActionBarProgress);
		
		// lazy loading
		if(mLazyLoading) startLazyLoadData();
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
				if(response.getClass().getSimpleName().equalsIgnoreCase("ExampleResponse"))
				{
					ExampleResponse exampleResponse = (ExampleResponse) response;
					
					// error
					if(exampleResponse.isError())
					{
						Logcat.d("EXAMPLE", "onApiCallRespond: example error " + exampleResponse.getErrorType() + ": " + exampleResponse.getErrorMessage());
						Logcat.d("EXAMPLE", "onApiCallRespond status code: " + status.getStatusCode());
						Logcat.d("EXAMPLE", "onApiCallRespond status message: " + status.getStatusMessage());

						// hide progress
						stopLazyLoadData();
						showList();

						// handle error
						handleError(exampleResponse.getErrorType(), exampleResponse.getErrorMessage());
					}
					
					// response
					else
					{
						Logcat.d("EXAMPLE", "onApiCallRespond: example ok");
						Logcat.d("EXAMPLE", "onApiCallRespond status code: " + status.getStatusCode());
						Logcat.d("EXAMPLE", "onApiCallRespond status message: " + status.getStatusMessage());

						// check metda data
						if(call.getRequest().getMetaData()!=null && call.getRequest().getMetaData().getBoolean(EXTRA_REFRESH, false))
						{
							// refresh
							mMessages.clear();
						}
						
						// get data
						Iterator<Message> iterator = exampleResponse.getMessages().iterator();
						while(iterator.hasNext())
						{
							Message message = iterator.next();
							mMessages.add(new Message(message));
						}
						
						// render view
						if(mLazyLoading && mViewState==ViewState.Visibility.CONTENT && mAdapter!=null)
						{
							mAdapter.notifyDataSetChanged();
						}
						else
						{
							if(mMessages!=null) renderView();
						}

						// hide progress
						stopLazyLoadData();
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
	public void onApiCallFail(final ApiCall call, final ResponseStatus status, final boolean parseFail)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(call.getRequest().getClass().getSimpleName().equalsIgnoreCase("ExampleRequest"))
				{
					Logcat.d("EXAMPLE", "onApiCallFail: example " + parseFail);
					Logcat.d("EXAMPLE", "onApiCallFail status code: " + status.getStatusCode());
					Logcat.d("EXAMPLE", "onApiCallFail status message: " + status.getStatusMessage());
					
					// hide progress
					stopLazyLoadData();
					showList();

					// handle fail
					handleFail();
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


	private void handleFail()
	{
		Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
	}
	
	
	private void loadData()
	{
		if(RequestManager.isOnline(getActivity()))
		{
			if(!mRequestManager.hasRunningRequest(ExampleRequest.class))
			{
				// show progress
				showProgress();

				// show progress in action bar
				showActionBarProgress(true);
				
				// example request with paging
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
		if(RequestManager.isOnline(getActivity()))
		{
			if(!mRequestManager.hasRunningRequest(ExampleRequest.class))
			{
				// show progress in action bar
				showActionBarProgress(true);
				
				// meta data
				Bundle bundle = new Bundle();
				bundle.putBoolean(EXTRA_REFRESH, true);
				
				// example request with paging
				int take = mMessages.size() <= LAZY_LOADING_MAX ? mMessages.size() : LAZY_LOADING_TAKE;
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
		if(RequestManager.isOnline(getActivity()))
		{
			// show progress in footer
			startLazyLoadData();
			
			// example request with paging
			ExampleRequest request = new ExampleRequest(mMessages.size(), LAZY_LOADING_TAKE);
			mRequestManager.executeRequest(request, this);
		}
	}
	
	
	private void startLazyLoadData()
	{
		mLazyLoading = true;

		// show footer
		ListView listView = getListView();
		listView.addFooterView(mFooterView);
	}
	
	
	private void stopLazyLoadData()
	{
		// hide footer
		ListView listView = getListView();
		listView.removeFooterView(mFooterView);
		
		mLazyLoading = false;
	}


	private void showActionBarProgress(boolean visible)
	{
		// show action bar progress
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
		mViewState = ViewState.Visibility.CONTENT;
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
		mViewState = ViewState.Visibility.PROGRESS;
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
		mViewState = ViewState.Visibility.OFFLINE;
	}

	
	private void renderView()
	{
		// TODO
	}
}
