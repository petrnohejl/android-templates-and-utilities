package com.example.adapter;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.R;
import com.example.entity.GroupEntity;
import com.example.entity.ProductEntity;


public class StickyListingAdapter extends BaseAdapter implements StickyListHeadersAdapter
{
	private Context mContext;
	private ArrayList<ProductEntity> mProductList;
	private int mSelectedPosition = -1;


	public StickyListingAdapter(Context context, ArrayList<ProductEntity> productList)
	{
		mContext = context;
		mProductList = productList;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// inflate view
		View view = convertView;
		if(view == null) 
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_sticky_listing_item, parent, false);
			
			// view holder
			ViewHolder holder = new ViewHolder();
			holder.nameTextView = (TextView) view.findViewById(R.id.fragment_sticky_listing_item_name);
			view.setTag(holder);
		}
		
		// entity
		ProductEntity product = (ProductEntity) mProductList.get(position);
		
		if(product != null)
		{
			// view holder
			ViewHolder holder = (ViewHolder) view.getTag();
			
			// content
			holder.nameTextView.setText(product.getName());
			
			// selected item
			if(mSelectedPosition == position)
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
	public int getCount()
	{
		if(mProductList!=null) return mProductList.size();
		else return 0;
	}


	@Override
	public Object getItem(int position)
	{
		if(mProductList!=null) return mProductList.get(position);
		else return null;
	}


	@Override
	public long getItemId(int position)
	{
		return position;
	}


	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent)
	{
		// inflate view
		View view = convertView;
		if(view == null) 
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_sticky_listing_group, parent, false);
			
			// view holder
			ViewHolderGroup holder = new ViewHolderGroup();
			holder.nameTextView = (TextView) view.findViewById(R.id.fragment_sticky_listing_group_name);
			view.setTag(holder);
		}
		
		// entity
		GroupEntity group = mProductList.get(position).getGroup();
		
		if(group != null)
		{
			// view holder
			ViewHolderGroup holder = (ViewHolderGroup) view.getTag();
			
			// content
			holder.nameTextView.setText(group.getName());
		}
		
		return view;
	}


	@Override
	public long getHeaderId(int position)
	{
		GroupEntity group = mProductList.get(position).getGroup();
		return group.getId().hashCode();
	}
	
	
	public void refill(Context context, ArrayList<ProductEntity> productList)
	{
		mContext = context;
		mProductList = productList;
		notifyDataSetChanged();
	}
	
	
	public void stop()
	{
		// TODO: stop image loader
	}
	
	
	public void setSelectedPosition(int position)
	{
		mSelectedPosition = position;
		notifyDataSetChanged();
	}
	
	
	public int getSelectedPosition()
	{
		return mSelectedPosition;
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
