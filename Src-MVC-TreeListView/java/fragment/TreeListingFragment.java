package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArraySet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.R;
import com.example.adapter.TreeListingAdapter;
import com.example.entity.ProductEntity;
import com.example.task.LoadDataTask;

import org.alfonz.utility.Logcat;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.TreeBuilder;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewList;


public class TreeListingFragment extends TaskFragment implements
		LoadDataTask.OnLoadDataListener,
		AdapterView.OnItemClickListener
{
	private static final int TREEVIEW_DEPTH = 4;

	private View mRootView;
	private StatefulLayout mStatefulLayout;
	private TreeListingAdapter mAdapter;
	private TreeStateManager<Long> mTreeStateManager;
	private Set<Long> mSelectedSet = new ArraySet<>();
	private LoadDataTask mLoadDataTask;
	private List<ProductEntity> mProductList = new ArrayList<>();


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
		mRootView = inflater.inflate(R.layout.fragment_tree_listing, container, false);
		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// setup stateful layout
		setupStatefulLayout(savedInstanceState);

		// load data
		if(mProductList == null || mProductList.isEmpty() || mTreeStateManager == null)
		{
			loadData();
		}
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
		if(mAdapter != null) mAdapter.stop();
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
		if(mLoadDataTask != null) mLoadDataTask.cancel(true);
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
		if(mStatefulLayout != null) mStatefulLayout.saveInstanceState(outState);
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
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
		getActivity().getMenuInflater().inflate(R.menu.treeview, menu);

		AdapterView.AdapterContextMenuInfo adapterInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long id = adapterInfo.id;
		TreeNodeInfo<Long> nodeInfo = mTreeStateManager.getNodeInfo(id);

		if(nodeInfo.isWithChildren())
		{
			if(nodeInfo.isExpanded())
			{
				menu.findItem(R.id.menu_treeview_expand).setVisible(false);
				menu.findItem(R.id.menu_treeview_expand_all).setVisible(false);
			}
			else
			{
				menu.findItem(R.id.menu_treeview_collapse_all).setVisible(false);
			}
		}
		else
		{
			menu.findItem(R.id.menu_treeview_expand).setVisible(false);
			menu.findItem(R.id.menu_treeview_expand_all).setVisible(false);
			menu.findItem(R.id.menu_treeview_collapse_all).setVisible(false);
		}

		// hide delete because deleting is dangerous
		menu.findItem(R.id.menu_treeview_delete).setVisible(false);

		super.onCreateContextMenu(menu, v, menuInfo);
	}


	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		long id = menuInfo.id;
		if(item.getItemId() == R.id.menu_treeview_collapse_all)
		{
			mTreeStateManager.collapseChildren(id);
			return true;
		}
		else if(item.getItemId() == R.id.menu_treeview_expand_all)
		{
			mTreeStateManager.expandEverythingBelow(id);
			return true;
		}
		else if(item.getItemId() == R.id.menu_treeview_expand)
		{
			mTreeStateManager.expandDirectChildren(id);
			return true;
		}
		else if(item.getItemId() == R.id.menu_treeview_delete)
		{
			mTreeStateManager.removeNodeRecursively(id);
			return true;
		}
		else
		{
			return super.onContextItemSelected(item);
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View clickedView, int position, long id)
	{
		// list position
		int listPosition = getListPosition((int) id);

		// TODO
	}


	@Override
	public void onLoadData()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				// get data
				final int size = mProductList.size();
				final int[] nodes = new int[]{0, 0, 1, 1, 1, 2, 2, 1, 1, 2, 1, 0, 0, 0, 1, 2, 3, 2, 0, 0, 1, 2, 0, 1, 2, 0, 1};
				for(int i = 0; i < nodes.length; i++)
				{
					ProductEntity p = new ProductEntity();
					p.setName("Product " + (size + i));
					mProductList.add(p);
				}

				// create tree manager
				mTreeStateManager = new InMemoryTreeStateManager<>();
				TreeBuilder<Long> treeBuilder = new TreeBuilder<>(mTreeStateManager);
				for(int i = 0; i < nodes.length; i++)
				{
					treeBuilder.sequentiallyAddNextNode((long) i, nodes[i]);
				}
				Logcat.d(mTreeStateManager.toString());

				// show content
				mStatefulLayout.showContent();
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


	private void setupView()
	{
		// reference
		TreeViewList treeView = mRootView.findViewById(android.R.id.list);
		ViewGroup emptyView = mRootView.findViewById(android.R.id.empty);

		// treeview content
		if(mAdapter == null)
		{
			// create adapter
			mAdapter = new TreeListingAdapter(getActivity(), mProductList, mSelectedSet, mTreeStateManager, TREEVIEW_DEPTH);
		}
		else
		{
			// refresh adapter
			mAdapter.refresh();
			mAdapter.notifyDataSetChanged();
		}

		// set adapter
		treeView.setAdapter(mAdapter);

		// treeview parameters
		treeView.setCollapsible(true);
		treeView.setEmptyView(emptyView);
		treeView.setOnItemClickListener(this);

		// context menu
		registerForContextMenu(treeView);
	}


	private void setupStatefulLayout(Bundle savedInstanceState)
	{
		// reference
		mStatefulLayout = (StatefulLayout) mRootView;

		// state change listener
		mStatefulLayout.setOnStateChangeListener(new StatefulLayout.OnStateChangeListener()
		{
			@Override
			public void onStateChange(View view, @StatefulLayout.State int state)
			{
				Logcat.d(String.valueOf(state));

				if(state == StatefulLayout.CONTENT)
				{
					TreeViewList treeView = mRootView.findViewById(android.R.id.list);
					if(treeView.getAdapter() != null)
					{
						mAdapter.refresh();
						mAdapter.notifyDataSetChanged();
					}
					else
					{
						if(mProductList != null && mTreeStateManager != null) setupView();
					}
				}
			}
		});

		// restore state
		mStatefulLayout.restoreInstanceState(savedInstanceState);
	}


	private int getListPosition(int globalPosition)
	{
		// reference
		TreeViewList treeView = mRootView.findViewById(android.R.id.list);

		// list position without headers, should be used for getting data entities from collections
		int listPosition = globalPosition;
		if(treeView != null) listPosition = globalPosition - treeView.getHeaderViewsCount();
		return listPosition;
	}


	private void expandAll()
	{
		if(mTreeStateManager != null) mTreeStateManager.expandEverythingBelow(null);
	}


	private void collapseAll()
	{
		if(mTreeStateManager != null) mTreeStateManager.collapseChildren(null);
	}
}
