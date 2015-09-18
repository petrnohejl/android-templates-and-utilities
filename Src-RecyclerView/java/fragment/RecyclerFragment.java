package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.adapter.RecyclerAdapter;
import com.example.entity.ProductEntity;
import com.example.listener.OnLoadDataListener;
import com.example.task.LoadDataTask;
import com.example.utility.Logcat;
import com.example.utility.NetworkUtility;
import com.example.view.LinearDividerItemDecoration;
import com.example.view.StatefulLayout;

import java.util.ArrayList;
import java.util.List;


public class RecyclerFragment extends TaskFragment implements OnLoadDataListener, RecyclerAdapter.ProductViewHolder.OnItemClickListener
{
	private static final int LAZY_LOADING_TAKE = 16;
	private static final int LAZY_LOADING_OFFSET = 4;
	
	private boolean mLazyLoading = false;
	private View mRootView;
	private StatefulLayout mStatefulLayout;
	private RecyclerAdapter mAdapter;
	private LoadDataTask mLoadDataTask;
	private List<String> mHeaderList = new ArrayList<>();
	private List<ProductEntity> mProductList = new ArrayList<>();
	private List<Object> mFooterList = new ArrayList<>();
	
	
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.fragment_recycler, container, false);
		setupRecyclerView();
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// setup stateful layout
		setupStatefulLayout(savedInstanceState);

		// load data
		if(mStatefulLayout.getState()==null) loadData();

		// lazy loading progress
		if(mLazyLoading) showLazyLoadingProgress(true);
	}
	
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		// stop adapter
		if(mAdapter!=null) mAdapter.stop();
	}
	
	
	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	
	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		mRootView = null;
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		// cancel async tasks
		if(mLoadDataTask!=null) mLoadDataTask.cancel(true);
	}
	
	
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// save current instance state
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);

		// stateful layout state
		if(mStatefulLayout!=null) mStatefulLayout.saveInstanceState(outState);
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// action bar menu
		super.onCreateOptionsMenu(menu, inflater);
		
		// TODO
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// action bar menu behavior
		return super.onOptionsItemSelected(item);
		
		// TODO
	}


	@Override
	public void onItemClick(View view, int position, long id, int viewType)
	{
		// position
		int productPosition = mAdapter.getProductPosition(position);

		// TODO
	}


	@Override
	public void onItemLongClick(View view, int position, long id, int viewType)
	{
		// position
		int productPosition = mAdapter.getProductPosition(position);

		// TODO
	}
	
	
	@Override
	public void onLoadData()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView==null) return; // view was destroyed
				
				// get data
				final int size = mProductList.size();
				for(int i=0; i<LAZY_LOADING_TAKE; i++)
				{
					ProductEntity p = new ProductEntity();
					p.setName("Product " + (size + i));
					mProductList.add(p);
				}
				if(mHeaderList.size()==0)
				{
					mHeaderList.add("One");
					mHeaderList.add("Two");
					mHeaderList.add("Three");
				}

				// show content
				if(mProductList!=null && mProductList.size()>0) mStatefulLayout.showContent();
				else mStatefulLayout.showEmpty();
				showLazyLoadingProgress(false);
			}
		});
	}
	
	
	private void loadData()
	{
		if(NetworkUtility.isOnline(getActivity()))
		{
			// show progress
			mStatefulLayout.showProgress();
			
			// run async task
			mLoadDataTask = new LoadDataTask(this);
			executeTask(mLoadDataTask);
		}
		else
		{
			mStatefulLayout.showOffline();
		}
	}
	
	
	private void lazyLoadData()
	{
		if(NetworkUtility.isOnline(getActivity()))
		{
			// show lazy loading progress
			showLazyLoadingProgress(true);
			
			// run async task
			mLoadDataTask = new LoadDataTask(this);
			executeTask(mLoadDataTask);
		}
	}
	
	
	private void showLazyLoadingProgress(boolean visible)
	{
		if(visible)
		{
			mLazyLoading = true;

			// show footer
			if(mFooterList.size()<=0)
			{
				mFooterList.add(new Object());
				mAdapter.notifyItemInserted(mAdapter.getRecyclerPositionByFooter(0));
			}
		}
		else
		{
			// hide footer
			if(mFooterList.size()>0)
			{
				mFooterList.remove(0);
				mAdapter.notifyItemRemoved(mAdapter.getRecyclerPositionByFooter(0));
			}

			mLazyLoading = false;
		}
	}
	
	
	private void bindData()
	{
		// reference
		RecyclerView recyclerView = getRecyclerView();

		// content
		if(recyclerView.getAdapter()==null)
		{
			// create adapter
			mAdapter = new RecyclerAdapter(mHeaderList, mProductList, mFooterList, this);
		}
		else
		{
			// refill adapter
			mAdapter.refill(mHeaderList, mProductList, mFooterList, this);
		}

		// set fixed size
		recyclerView.setHasFixedSize(true);

		// add decoration
		RecyclerView.ItemDecoration itemDecoration = new LinearDividerItemDecoration(getActivity(), null);
		recyclerView.addItemDecoration(itemDecoration);

		// set animator
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		// set adapter
		recyclerView.setAdapter(mAdapter);

		// lazy loading
		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState)
			{
				super.onScrollStateChanged(recyclerView, newState);
			}


			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy)
			{
				super.onScrolled(recyclerView, dx, dy);

				LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
				int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
				int visibleItemCount = layoutManager.getChildCount();
				int totalItemCount = layoutManager.getItemCount();
				int lastVisibleItem = firstVisibleItem + visibleItemCount;

				if(totalItemCount-lastVisibleItem <= LAZY_LOADING_OFFSET && mProductList.size() % LAZY_LOADING_TAKE==0 && !mProductList.isEmpty())
				{
					if(!mLazyLoading) lazyLoadData();
				}
			}
		});
	}


	private void setupStatefulLayout(Bundle savedInstanceState)
	{
		// reference
		mStatefulLayout = (StatefulLayout) mRootView;

		// state change listener
		mStatefulLayout.setOnStateChangeListener(new StatefulLayout.OnStateChangeListener()
		{
			@Override
			public void onStateChange(View v, StatefulLayout.State state)
			{
				Logcat.d("" + (state==null ? "null" : state.toString()));

				if(state==StatefulLayout.State.CONTENT)
				{
					RecyclerView recyclerView = getRecyclerView();
					if(mLazyLoading && recyclerView.getAdapter()!=null)
					{
						mAdapter.notifyDataSetChanged();
					}
					else
					{
						if(mProductList!=null) bindData();
					}
				}
			}
		});

		// restore state
		mStatefulLayout.restoreInstanceState(savedInstanceState);
	}


	private RecyclerView getRecyclerView()
	{
		return mRootView!=null ? (RecyclerView) mRootView.findViewById(R.id.fragment_recycler_recycler) : null;
	}


	private void setupRecyclerView()
	{
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		RecyclerView recyclerView = getRecyclerView();
		recyclerView.setLayoutManager(linearLayoutManager); // TODO: use LinearLayoutManager, GridLayoutManager or StaggeredGridLayoutManager
	}
}
