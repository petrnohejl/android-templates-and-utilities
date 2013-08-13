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
	private ArrayList<String> mGroupList;
	private ArrayList<ArrayList<Product>> mProductList;
	private int mSelectedGroupPosition = -1;
	private int mSelectedChildPosition = -1;
	
	
	public ExpandableListingAdapter(Context context, ArrayList<String> groupList, ArrayList<ArrayList<Product>> productList)
	{
		mContext = context;
		mGroupList = groupList;
		mProductList = productList;
	}
	
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		// inflate view
		View view = convertView;
		if(view == null) 
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_expandable_listing_item, parent, false);
			
			// view holder
			ViewHolder holder = new ViewHolder();
			holder.nameTextView = (TextView) view.findViewById(R.id.fragment_expandable_listing_item_name);
			view.setTag(holder);
		}
		
		// entity
		Product product = (Product) getChild(groupPosition, childPosition);
		
		if(product != null)
		{
			// view holder
			ViewHolder holder = (ViewHolder) view.getTag();
			
			// content
			holder.nameTextView.setText(product.getName());
			
			// selected item
			if(mSelectedGroupPosition == groupPosition && mSelectedChildPosition == childPosition)
			{
				view.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.view_listview_item_bg_selected));
			}
			else
			{
				view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_view_listview_item_bg));
			}
		}
		
		return view;
	}


	@Override
	public int getChildrenCount(int groupPosition) 
	{
		if(mProductList!=null) return mProductList.get(groupPosition).size();
		else return 0;
	}
	
	
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return mProductList.get(groupPosition).get(childPosition);
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
			view = inflater.inflate(R.layout.fragment_expandable_listing_group, parent, false);

			// view holder
			ViewHolderGroup holder = new ViewHolderGroup();
			holder.nameTextView = (TextView) view.findViewById(R.id.fragment_expandable_listing_group_name);
			view.setTag(holder);
		}

		// entity
		String group = (String) getGroup(groupPosition);

		if(group != null)
		{
			// view holder
			ViewHolderGroup holder = (ViewHolderGroup) view.getTag();

			// content
			holder.nameTextView.setText(group);
		}

		return view;
	}
	
	
	@Override
	public int getGroupCount()
	{
		if(mGroupList!=null) return mGroupList.size();
		else return 0;
	}


	@Override
	public Object getGroup(int groupPosition)
	{
		return mGroupList.get(groupPosition);
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
	
	
	public void refill(Context context, ArrayList<String> groupList, ArrayList<ArrayList<Product>> productList)
	{
		mContext = context;
		mGroupList = groupList;
		mProductList = productList;
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
		TextView nameTextView;
	}
	
	
	static class ViewHolderGroup
	{
		TextView nameTextView;
	}
}
