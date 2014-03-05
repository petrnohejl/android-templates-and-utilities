package com.example.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.R;
import com.example.adapter.ListingAdapter;
import com.example.entity.ProductEntity;
import com.example.listener.OnDualPaneShowListener;
import com.example.task.TaskListFragment;


public class ListingFragment extends TaskListFragment
{
	private View mRootView;
	private ListingAdapter mAdapter;
	private OnDualPaneShowListener mDualPaneShowListener;

	private ArrayList<ProductEntity> mProductList = new ArrayList<ProductEntity>();
	
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		// set dual pane listener
		try
		{
			mDualPaneShowListener = (OnDualPaneShowListener) activity;
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(activity.getClass().getName() + " must implement " + OnDualPaneShowListener.class.getName());
		}
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.fragment_listing, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		renderView();
	}
	
	
	@Override
	public void onListItemClick(ListView listView, View clickedView, int position, long id)
	{
		// list position
		int listPosition = position; // TODO

		// listview item onclick
		if(mAdapter!=null) mAdapter.setSelectedPosition(listPosition);

		mDualPaneShowListener.onDualPaneShow(SimpleFragment.class, listPosition);
	}
	
	
	private void renderView()
	{
		// testing data
		for(int i=0; i<32; i++)
		{
			ProductEntity p = new ProductEntity();
			p.setName("Product " + (i));
			mProductList.add(p);
		}

		// activity has dual pane layout
		View dualPaneContainer = getActivity().findViewById(R.id.container_dual_pane);
		boolean dualPane = dualPaneContainer != null && dualPaneContainer.getVisibility() == View.VISIBLE;
		
		// listview content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new ListingAdapter(getActivity(), dualPane, mProductList);
			
			// initial fragment in second pane
			if(dualPane && mProductList!=null && mProductList.size()>0)
			{
				mAdapter.setSelectedPosition(0);
				mDualPaneShowListener.onDualPaneShow(SimpleFragment.class, 0);
			}
		}
		else
		{
			// refill adapter
			mAdapter.refill(getActivity(), dualPane, mProductList);
		}
		
		// set adapter
		setListAdapter(mAdapter);
	}
}
