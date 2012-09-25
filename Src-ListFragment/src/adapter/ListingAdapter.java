package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.R;
import com.example.client.entity.Message;


public class ListingAdapter extends BaseAdapter 
{
	private Context mContext;
	private ArrayList<Message> mMessages;
	private int mSelectedPosition = -1;
	
	
	public ListingAdapter(Context context, ArrayList<Message> messages)
	{
		mContext = context;
		mMessages = messages;
	}
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// inflate view
		View view = convertView;
		if(view == null) 
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.layout_listing_item, null);
			
			// view holder
			ViewHolder holder = new ViewHolder();
			holder.textViewName = (TextView) view.findViewById(R.id.layout_listing_item_name);
			view.setTag(holder);
		}
		
		// entity
		Message message = (Message) mMessages.get(position);
		
		if(message != null)
		{
			// view holder
			ViewHolder holder = (ViewHolder) view.getTag();
			
			// content
			holder.textViewName.setText(message.getName());
			
			// selected item
			if(mSelectedPosition == position)
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
	public int getCount()
	{
		return mMessages.size();
	}
	
	
	@Override
	public Object getItem(int position)
	{
		if(mMessages!=null) return mMessages.size();
		else return 0;
	}
	
	
	@Override
	public long getItemId(int position)
	{
		return 0;
	}
	
	
	public void refill(Context context, ArrayList<Message> messages)
	{
		mContext = context;
		mMessages = messages;
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
		TextView textViewName;
	}
}
