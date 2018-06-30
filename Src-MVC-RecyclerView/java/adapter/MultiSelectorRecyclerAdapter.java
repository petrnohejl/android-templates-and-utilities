package com.example.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.example.ExampleApplication;
import com.example.R;
import com.example.entity.ProductEntity;


public class MultiSelectorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private MultiSelector mMultiSelector = new MultiSelector(); // TODO: use MultiSelector or SingleSelector


	public static final class ProductViewHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener
	{
		private TextView mNameTextView;
		private OnItemClickListener mListener;
		private MultiSelector mMultiSelector;


		public interface OnItemClickListener
		{
			void onItemClick(View view, int position, long id, int viewType);
			void onItemLongClick(View view, int position, long id, int viewType);
		}


		public ProductViewHolder(View itemView, OnItemClickListener listener, MultiSelector multiSelector)
		{
			super(itemView, multiSelector);
			mListener = listener;
			mMultiSelector = multiSelector;

			// set background
			setSelectionModeBackgroundDrawable(ContextCompat.getDrawable(ExampleApplication.getContext(), R.drawable.selector_selectable_item_bg));

			// set listener
			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);

			// find views
			mNameTextView = itemView.findViewById(R.id.recycler_item_name);
		}


		@Override
		public void onClick(View view)
		{
			if(!mMultiSelector.tapSelection(this))
			{
				int position = getAdapterPosition();
				if(position != RecyclerView.NO_POSITION)
				{
					mListener.onItemClick(view, position, getItemId(), getItemViewType());
				}
			}
		}


		@Override
		public boolean onLongClick(View view)
		{
			if(!mMultiSelector.isSelectable())
			{
				mMultiSelector.setSelectable(true);
				mMultiSelector.setSelected(this, true);
			}
			else
			{
				int position = getAdapterPosition();
				if(position != RecyclerView.NO_POSITION)
				{
					mListener.onItemLongClick(view, position, getItemId(), getItemViewType());
				}
			}
			return true;
		}


		public void bindData(ProductEntity product)
		{
			mNameTextView.setText(product.getName());
		}
	}
}
