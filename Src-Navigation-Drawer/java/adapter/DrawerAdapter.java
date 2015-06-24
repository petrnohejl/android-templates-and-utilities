package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;


public class DrawerAdapter extends BaseAdapter
{
	private Context mContext;
	private String[] mTitleList;
	private Integer[] mIconList;
	
	
	public DrawerAdapter(Context context, String[] titleList, Integer[] iconList)
	{
		mContext = context;
		mTitleList = titleList;
		mIconList = iconList;
	}
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// inflate view
		View view = convertView;
		if(view == null) 
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.drawer_item, parent, false);
			
			// view holder
			ViewHolder holder = new ViewHolder();
			holder.titleTextView = (TextView) view.findViewById(R.id.drawer_item_title);
			holder.iconImageView = (ImageView) view.findViewById(R.id.drawer_item_icon);
			view.setTag(holder);
		}
		
		// entity
		String title = (String) mTitleList[position];
		Integer icon = (Integer) mIconList[position];
		if(title!=null && icon!=null )
		{
			// view holder
			ViewHolder holder = (ViewHolder) view.getTag();
			
			// content
			holder.titleTextView.setText(title);
			holder.iconImageView.setImageResource(icon);
		}
		
		return view;
	}
	
	
	@Override
	public int getCount()
	{
		if(mTitleList!=null) return mTitleList.length;
		else return 0;
	}
	
	
	@Override
	public Object getItem(int position)
	{
		if(mTitleList!=null) return mTitleList[position];
		else return null;
	}
	
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	
	public void refill(Context context, String[] titleList, Integer[] iconList)
	{
		mContext = context;
		mTitleList = titleList;
		mIconList = iconList;
		notifyDataSetChanged();
	}
	
	
	static class ViewHolder
	{
		TextView titleTextView;
		ImageView iconImageView;
	}
}
