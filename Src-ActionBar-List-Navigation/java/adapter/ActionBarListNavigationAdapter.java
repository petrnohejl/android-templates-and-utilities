package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.R;


public class ActionBarListNavigationAdapter<T> extends ArrayAdapter<T>
{
	private Context mContext;
	private String mSubtitle;


	public ActionBarListNavigationAdapter(Context context, int resource, T[] objects, String subtitle)
	{
		super(context, resource, objects);
		mContext = context;
		mSubtitle = subtitle;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// inflate view
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ab_list_navigation, parent, false);

			// view holder
			ViewHolder holder = new ViewHolder();
			holder.titleTextView = (TextView) convertView.findViewById(R.id.ab_list_navigation_title);
			holder.subtitleTextView = (TextView) convertView.findViewById(R.id.ab_list_navigation_subtitle);
			convertView.setTag(holder);
		}

		// entity
		T item = getItem(position);
		if(item != null)
		{
			// view holder
			ViewHolder holder = (ViewHolder) convertView.getTag();

			// content
			holder.titleTextView.setText("Item " + item);
			holder.subtitleTextView.setText(mSubtitle);
		}

		return convertView;
	}


	static class ViewHolder
	{
		TextView titleTextView;
		TextView subtitleTextView;
	}
}
