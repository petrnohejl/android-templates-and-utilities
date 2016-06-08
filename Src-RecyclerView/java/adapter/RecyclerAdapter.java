package com.example.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;
import com.example.entity.ProductEntity;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private static final int VIEW_TYPE_HEADER = 0;
	private static final int VIEW_TYPE_PRODUCT = 1;
	private static final int VIEW_TYPE_FOOTER = 2;

	private List<String> mHeaderList;
	private List<ProductEntity> mProductList;
	private List<Object> mFooterList;
	private ProductViewHolder.OnItemClickListener mListener;


	public RecyclerAdapter(List<String> headerList, List<ProductEntity> productList, List<Object> footerList, ProductViewHolder.OnItemClickListener listener)
	{
		mHeaderList = headerList;
		mProductList = productList;
		mFooterList = footerList;
		mListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		// inflate view and create view holder
		if(viewType == VIEW_TYPE_HEADER)
		{
			View view = inflater.inflate(R.layout.fragment_recycler_header, parent, false);
			return new HeaderViewHolder(view);
		}
		else if(viewType == VIEW_TYPE_PRODUCT)
		{
			View view = inflater.inflate(R.layout.fragment_recycler_item, parent, false);
			return new ProductViewHolder(view, mListener);
		}
		else if(viewType == VIEW_TYPE_FOOTER)
		{
			View view = inflater.inflate(R.layout.fragment_recycler_footer, parent, false);
			return new FooterViewHolder(view);
		}
		else
		{
			throw new RuntimeException("There is no view type that matches the type " + viewType);
		}
	}


	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
	{
		if(viewHolder instanceof HeaderViewHolder)
		{
			// entity
			String string = mHeaderList.get(getHeaderPosition(position));

			// bind data
			if(string != null)
			{
				((HeaderViewHolder) viewHolder).bindData(string);
			}
		}
		else if(viewHolder instanceof ProductViewHolder)
		{
			// entity
			ProductEntity product = mProductList.get(getProductPosition(position));

			// bind data
			if(product != null)
			{
				((ProductViewHolder) viewHolder).bindData(product);
			}
		}
		else if(viewHolder instanceof FooterViewHolder)
		{
			// entity
			Object object = mFooterList.get(getFooterPosition(position));

			// bind data
			if(object != null)
			{
				((FooterViewHolder) viewHolder).bindData(object);
			}
		}
	}


	@Override
	public int getItemCount()
	{
		int size = 0;
		if(mHeaderList != null) size += mHeaderList.size();
		if(mProductList != null) size += mProductList.size();
		if(mFooterList != null) size += mFooterList.size();
		return size;
	}


	@Override
	public int getItemViewType(int position)
	{
		int headers = mHeaderList.size();
		int products = mProductList.size();
		int footers = mFooterList.size();

		if(position < headers) return VIEW_TYPE_HEADER;
		else if(position < headers + products) return VIEW_TYPE_PRODUCT;
		else if(position < headers + products + footers) return VIEW_TYPE_FOOTER;
		else return -1;
	}


	public int getHeaderCount()
	{
		if(mHeaderList != null) return mHeaderList.size();
		return 0;
	}


	public int getProductCount()
	{
		if(mProductList != null) return mProductList.size();
		return 0;
	}


	public int getFooterCount()
	{
		if(mFooterList != null) return mFooterList.size();
		return 0;
	}


	public int getHeaderPosition(int recyclerPosition)
	{
		return recyclerPosition;
	}


	public int getProductPosition(int recyclerPosition)
	{
		return recyclerPosition - getHeaderCount();
	}


	public int getFooterPosition(int recyclerPosition)
	{
		return recyclerPosition - getHeaderCount() - getProductCount();
	}


	public int getRecyclerPositionByHeader(int headerPosition)
	{
		return headerPosition;
	}


	public int getRecyclerPositionByProduct(int productPosition)
	{
		return productPosition + getHeaderCount();
	}


	public int getRecyclerPositionByFooter(int footerPosition)
	{
		return footerPosition + getProductCount() + getHeaderCount();
	}


	public void refill(List<String> headerList, List<ProductEntity> productList, List<Object> footerList, ProductViewHolder.OnItemClickListener listener)
	{
		mHeaderList = headerList;
		mProductList = productList;
		mFooterList = footerList;
		mListener = listener;
		notifyDataSetChanged();
	}


	public void stop()
	{
		// TODO: stop image loader
	}


	public static final class HeaderViewHolder extends RecyclerView.ViewHolder
	{
		private TextView mNameTextView;


		public HeaderViewHolder(View itemView)
		{
			super(itemView);

			// find views
			mNameTextView = (TextView) itemView.findViewById(R.id.fragment_recycler_header_name);
		}


		public void bindData(String string)
		{
			mNameTextView.setText(string);
		}
	}


	public static final class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
	{
		private TextView mNameTextView;
		private OnItemClickListener mListener;


		public interface OnItemClickListener
		{
			void onItemClick(View view, int position, long id, int viewType);
			void onItemLongClick(View view, int position, long id, int viewType);
		}


		public ProductViewHolder(View itemView, OnItemClickListener listener)
		{
			super(itemView);
			mListener = listener;

			// set listener
			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);

			// find views
			mNameTextView = (TextView) itemView.findViewById(R.id.fragment_recycler_item_name);
		}


		@Override
		public void onClick(View view)
		{
			int position = getAdapterPosition();
			if(position != RecyclerView.NO_POSITION)
			{
				mListener.onItemClick(view, position, getItemId(), getItemViewType());
			}
		}


		@Override
		public boolean onLongClick(View view)
		{
			int position = getAdapterPosition();
			if(position != RecyclerView.NO_POSITION)
			{
				mListener.onItemLongClick(view, position, getItemId(), getItemViewType());
			}
			return true;
		}


		public void bindData(ProductEntity product)
		{
			mNameTextView.setText(product.getName());
		}
	}


	public static final class FooterViewHolder extends RecyclerView.ViewHolder
	{
		public FooterViewHolder(View itemView)
		{
			super(itemView);
		}


		public void bindData(Object object)
		{
			// do nothing
		}
	}
}
