package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.R;
import com.example.adapter.ListingAdapter;
import com.example.entity.ProductEntity;
import com.example.listener.OnDualPaneShowListener;

import java.util.ArrayList;
import java.util.List;


public class ListingFragment extends Fragment
{
	private View mRootView;
	private ListingAdapter mAdapter;
	private OnDualPaneShowListener mDualPaneShowListener;
	private List<ProductEntity> mProductList = new ArrayList<>();


	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);

		// set dual pane listener
		try
		{
			mDualPaneShowListener = (OnDualPaneShowListener) getActivity();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getActivity().getClass().getName() + " must implement " + OnDualPaneShowListener.class.getName());
		}
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
		setupView();
	}


	private void setupView()
	{
		boolean init = false;

		// reference
		ListView listView = getListView();
		ViewGroup emptyView = (ViewGroup) mRootView.findViewById(android.R.id.empty);

		// testing data
		for(int i = 0; i < 32; i++)
		{
			ProductEntity p = new ProductEntity();
			p.setName("Product " + (i));
			mProductList.add(p);
		}

		// activity has dual pane layout
		View dualPaneContainer = getActivity().findViewById(R.id.container_dual_pane);
		boolean dualPane = dualPaneContainer != null && dualPaneContainer.getVisibility() == View.VISIBLE;

		// listview content
		if(mAdapter == null)
		{
			// create adapter
			mAdapter = new ListingAdapter(getActivity(), mProductList, dualPane);

			// initial fragment in second pane
			if(dualPane && mProductList != null && mProductList.size() > 0)
			{
				mDualPaneShowListener.onDualPaneShow(SimpleFragment.class, 0);
				init = true;
			}
		}
		else
		{
			// refill adapter
			mAdapter.refill(getActivity(), mProductList, dualPane);
		}

		// set adapter
		listView.setAdapter(mAdapter);

		// listview empty view
		listView.setEmptyView(emptyView);

		// set first item checked
		if(init) listView.setItemChecked(0, true);

		// listview item onclick
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// list position
				int listPosition = getListPosition(position);

				// listview item onclick
				mDualPaneShowListener.onDualPaneShow(SimpleFragment.class, listPosition);
			}
		});
	}
}
