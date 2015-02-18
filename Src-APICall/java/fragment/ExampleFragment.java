package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.R;
import com.example.adapter.ExampleAdapter;
import com.example.client.APICallListener;
import com.example.client.APICallManager;
import com.example.client.APICallTask;
import com.example.client.ResponseStatus;
import com.example.client.request.ExampleRequest;
import com.example.client.response.Response;
import com.example.entity.ProductEntity;
import com.example.utility.Logcat;
import com.example.utility.NetworkManager;
import com.example.view.ViewState;

import org.codehaus.jackson.JsonParseException;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExampleFragment extends TaskFragment implements APICallListener
{
	private static final String META_REFRESH = "refresh";

	private static final int LAZY_LOADING_TAKE = 16;
	private static final int LAZY_LOADING_OFFSET = 4;
	private static final int LAZY_LOADING_MAX = LAZY_LOADING_TAKE * 10;
	
	private boolean mLazyLoading = false;
	private ViewState mViewState = null;
	private View mRootView;
	private View mFooterView;
	private ExampleAdapter mAdapter;
	private APICallManager mAPICallManager = new APICallManager();
	
	private ArrayList<ProductEntity> mProductList = new ArrayList<ProductEntity>();
	
	
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
			showContent();
		}
		else if(mViewState==ViewState.PROGRESS)
		{
			showProgress();
		}

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
		mAPICallManager.cancelAllTasks();
	}


	@Override
	public void onAPICallRespond(final APICallTask task, final ResponseStatus status, final Response<?> response)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView==null) return; // view was destroyed
				
				if(task.getRequest().getClass().equals(ExampleRequest.class))
				{
					Response<List<ProductEntity>> exampleResponse = (Response<List<ProductEntity>>) response;
					
					// error
					if(exampleResponse.isError())
					{
						Logcat.d("Fragment.onAPICallRespond(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage() + 
								" / error / " + exampleResponse.getErrorType() + " / " + exampleResponse.getErrorMessage());

						// hide progress
						showLazyLoadingProgress(false);
						showContent();

						// handle error
						handleError(exampleResponse.getErrorType(), exampleResponse.getErrorMessage());
					}
					
					// response
					else
					{
						Logcat.d("Fragment.onAPICallRespond(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage());

						// check meta data
						if(task.getRequest().getMetaData()!=null && task.getRequest().getMetaData().getBoolean(META_REFRESH, false))
						{
							// refresh
							mProductList.clear();
						}
						
						// get data
						List<ProductEntity> productList = exampleResponse.getResponseObject();
						Iterator<ProductEntity> iterator = productList.iterator();
						while(iterator.hasNext())
						{
							ProductEntity product = iterator.next();
							mProductList.add(new ProductEntity(product));
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
						showContent();
					}
				}
				
				// finish request
				mAPICallManager.finishTask(task);

				// hide progress in action bar
				if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
			}
		});
	}


	@Override
	public void onAPICallFail(final APICallTask task, final ResponseStatus status, final Exception exception)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView==null) return; // view was destroyed
				
				if(task.getRequest().getClass().equals(ExampleRequest.class))
				{
					Logcat.d("Fragment.onAPICallFail(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage() +
							" / " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
					
					// hide progress
					showLazyLoadingProgress(false);
					showContent();

					// handle fail
					handleFail(exception);
				}
				
				// finish request
				mAPICallManager.finishTask(task);

				// hide progress in action bar
				if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
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
		else if(exception!=null && exception.getClass().equals(FileNotFoundException.class)) messageId = R.string.global_apicall_not_found_toast;
		else if(exception!=null && exception.getClass().equals(SocketTimeoutException.class)) messageId = R.string.global_apicall_timeout_toast;
		else if(exception!=null && exception.getClass().equals(JsonParseException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception!=null && exception.getClass().equals(ParseException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception!=null && exception.getClass().equals(NumberFormatException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception!=null && exception.getClass().equals(ClassCastException.class)) messageId = R.string.global_apicall_parse_fail_toast;
		else messageId = R.string.global_apicall_fail_toast;
		Toast.makeText(getActivity(), messageId, Toast.LENGTH_LONG).show();
	}
	
	
	private void loadData()
	{
		if(NetworkManager.isOnline(getActivity()))
		{
			if(!mAPICallManager.hasRunningTask(ExampleRequest.class))
			{
				// show progress
				showProgress();

				// show progress in action bar
				showActionBarProgress(true);
				
				// execute request
				ExampleRequest request = new ExampleRequest(0, LAZY_LOADING_TAKE);
				mAPICallManager.executeTask(request, this);
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
			if(!mAPICallManager.hasRunningTask(ExampleRequest.class))
			{
				// show progress in action bar
				showActionBarProgress(true);
				
				// meta data
				Bundle bundle = new Bundle();
				bundle.putBoolean(META_REFRESH, true);
				
				// execute request
				int take = (mProductList.size() <= LAZY_LOADING_MAX && mProductList.size() > 0) ? mProductList.size() : LAZY_LOADING_TAKE;
				ExampleRequest request = new ExampleRequest(0, take);
				request.setMetaData(bundle);
				mAPICallManager.executeTask(request, this);
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
			mAPICallManager.executeTask(request, this);
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
	
	
	private void showContent()
	{
		// show list container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerContent.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.CONTENT;
	}
	
	
	private void showProgress()
	{
		// show progress container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.PROGRESS;
	}
	
	
	private void showOffline()
	{
		// show offline container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		mViewState = ViewState.OFFLINE;
	}

	
	private void renderView()
	{
		// TODO
	}
}
