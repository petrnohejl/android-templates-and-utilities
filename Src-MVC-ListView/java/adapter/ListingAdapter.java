package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.R;
import com.example.entity.ProductEntity;

import java.util.List;


public class ListingAdapter extends BaseAdapter
{
	private Context mContext;
	private List<ProductEntity> mProductList;


	public ListingAdapter(Context context, List<ProductEntity> productList)
	{
		mContext = context;
		mProductList = productList;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// inflate view
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_listing_item, parent, false);

			// view holder
			ViewHolder holder = new ViewHolder();
			holder.nameTextView = convertView.findViewById(R.id.listing_item_name);
			convertView.setTag(holder);
		}

		// entity
		ProductEntity product = mProductList.get(position);
		if(product != null)
		{
			// view holder
			ViewHolder holder = (ViewHolder) convertView.getTag();

			// content
			holder.nameTextView.setText(product.getName());
		}

		return convertView;
	}


	@Override
	public int getCount()
	{
		if(mProductList != null) return mProductList.size();
		else return 0;
	}


	@Override
	public Object getItem(int position)
	{
		if(mProductList != null) return mProductList.get(position);
		else return null;
	}


	@Override
	public long getItemId(int position)
	{
		return position;
	}


	public void refill(Context context, List<ProductEntity> productList)
	{
		mContext = context;
		mProductList = productList;
		notifyDataSetChanged();
	}


	public void stop()
	{
		// TODO: stop image loader
	}


	static class ViewHolder
	{
		TextView nameTextView;
	}
}
