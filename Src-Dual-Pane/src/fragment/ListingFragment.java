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
import com.example.entity.Product;
import com.example.listener.OnDualPaneShowListener;
import com.example.task.TaskSherlockListFragment;


public class ListingFragment extends TaskSherlockListFragment
{
	private View mRootView;
	private ListingAdapter mAdapter;
	private OnDualPaneShowListener mDualPaneShowListener;

	private ArrayList<Product> mProducts = new ArrayList<Product>();
	
	
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
			throw new ClassCastException(getTargetFragment().toString() + " must implement OnDualPaneShowListener");
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
			Product p = new Product();
			p.setName("Product " + (i));
			mProducts.add(p);
		}

		// activity has dual pane layout
		View dualPaneContainer = getActivity().findViewById(R.id.container_dual_pane);
		boolean dualPane = dualPaneContainer != null && dualPaneContainer.getVisibility() == View.VISIBLE;
		
		// listview content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new ListingAdapter(getActivity(), dualPane, mProducts);
			
			// initial fragment in second pane
			if(dualPane && mProducts!=null && mProducts.size()>0)
			{
				mAdapter.setSelectedPosition(0);
				mDualPaneShowListener.onDualPaneShow(SimpleFragment.class, 0);
			}
		}
		else
		{
			// refill adapter
			mAdapter.refill(getActivity(), dualPane, mProducts);
		}
		
		// set adapter
		setListAdapter(mAdapter);
	}
}
