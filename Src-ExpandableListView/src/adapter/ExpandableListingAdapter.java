package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.R;
import com.example.entity.Product;


public class ExpandableListingAdapter extends BaseExpandableListAdapter
{
	private Context mContext;
	private ArrayList<String> mGroups;
	private ArrayList<ArrayList<Product>> mProducts;
	private int mSelectedGroupPosition = -1;
	private int mSelectedChildPosition = -1;
	
	
	public ExpandableListingAdapter(Context context, ArrayList<String> groups, ArrayList<ArrayList<Product>> products)
	{
		mContext = context;
		mGroups = groups;
		mProducts = products;
	}
	
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		// inflate view
		View view = convertView;
		if(view == null) 
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_expandable_listing_item, null);
			
			// view holder
			ViewHolder holder = new ViewHolder();
			holder.textViewName = (TextView) view.findViewById(R.id.fragment_expandable_listing_item_name);
			view.setTag(holder);
		}
		
		// entity
		Product product = (Product) getChild(groupPosition, childPosition);
		
		if(product != null)
		{
			// view holder
			ViewHolder holder = (ViewHolder) view.getTag();
			
			// content
			holder.textViewName.setText(product.getName());
			
			// selected item
			if(mSelectedGroupPosition == groupPosition && mSelectedChildPosition == childPosition)
			{
				view.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.widget_listview_item_bg_selected));
			}
			else
			{
				view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_widget_listview_item_bg));
			}
		}
		
		return view;
	}


	@Override
	public int getChildrenCount(int groupPosition) 
	{
		if(mProducts!=null) return mProducts.get(groupPosition).size();
		else return 0;
	}
	
	
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return mProducts.get(groupPosition).get(childPosition);
	}


	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}
	
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		// inflate view
		View view = convertView;
		if(view == null) 
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_expandable_listing_group, null);

			// view holder
			ViewHolderGroup holder = new ViewHolderGroup();
			holder.textViewName = (TextView) view.findViewById(R.id.fragment_expandable_listing_group_name);
			view.setTag(holder);
		}

		// entity
		String group = (String) getGroup(groupPosition);

		if(group != null)
		{
			// view holder
			ViewHolderGroup holder = (ViewHolderGroup) view.getTag();

			// content
			holder.textViewName.setText(group);
		}

		return view;
	}
	
	
	@Override
	public int getGroupCount()
	{
		if(mGroups!=null) return mGroups.size();
		else return 0;
	}


	@Override
	public Object getGroup(int groupPosition)
	{
		return mGroups.get(groupPosition);
	}


	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	
	@Override
	public boolean hasStableIds()
	{
		return true;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}
	
	
	public void refill(Context context, ArrayList<String> groups, ArrayList<ArrayList<Product>> products)
	{
		mContext = context;
		mGroups = groups;
		mProducts = products;
		notifyDataSetChanged();
	}
	
	
	public void stop()
	{
		// TODO: stop image loader
	}
	
	
	public void setSelectedPosition(int groupPosition, int childPosition)
	{
		mSelectedGroupPosition = groupPosition;
		mSelectedChildPosition = childPosition;
		notifyDataSetChanged();
	}
	
	
	public int getSelectedGroupPosition()
	{
		return mSelectedGroupPosition;
	}
	
	
	public int getSelectedChildPosition()
	{
		return mSelectedChildPosition;
	}
	
	
	static class ViewHolder
	{
		TextView textViewName;
	}
	
	
	static class ViewHolderGroup
	{
		TextView textViewName;
	}
}
