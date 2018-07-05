package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.R;
import com.example.entity.ProductEntity;

import java.util.List;

public class ExpandableListingAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private List<String> mGroupList;
	private List<List<ProductEntity>> mProductList;
	private int mSelectedGroupPosition = -1;
	private int mSelectedChildPosition = -1;

	public ExpandableListingAdapter(Context context, List<String> groupList, List<List<ProductEntity>> productList) {
		mContext = context;
		mGroupList = groupList;
		mProductList = productList;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// inflate view
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_expandable_listing_item, parent, false);

			// view holder
			ViewHolder holder = new ViewHolder();
			holder.nameTextView = convertView.findViewById(R.id.expandable_listing_item_name);
			convertView.setTag(holder);
		}

		// entity
		ProductEntity product = (ProductEntity) getChild(groupPosition, childPosition);
		if (product != null) {
			// view holder
			ViewHolder holder = (ViewHolder) convertView.getTag();

			// content
			holder.nameTextView.setText(product.getName());

			// selected item
			if (mSelectedGroupPosition == groupPosition && mSelectedChildPosition == childPosition) {
				convertView.setBackgroundResource(R.color.global_color_control_activated);
			} else {
				convertView.setBackgroundResource(R.drawable.selector_selectable_item_bg);
			}
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (mProductList != null) return mProductList.get(groupPosition).size();
		else return 0;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if (mProductList != null) return mProductList.get(groupPosition).get(childPosition);
		else return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// inflate view
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_expandable_listing_group, parent, false);

			// view holder
			ViewHolderGroup holder = new ViewHolderGroup();
			holder.nameTextView = convertView.findViewById(R.id.expandable_listing_group_name);
			convertView.setTag(holder);
		}

		// entity
		String group = (String) getGroup(groupPosition);
		if (group != null) {
			// view holder
			ViewHolderGroup holder = (ViewHolderGroup) convertView.getTag();

			// content
			holder.nameTextView.setText(group);
		}

		return convertView;
	}

	@Override
	public int getGroupCount() {
		if (mGroupList != null) return mGroupList.size();
		else return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		if (mGroupList != null) return mGroupList.get(groupPosition);
		else return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void refill(Context context, List<String> groupList, List<List<ProductEntity>> productList) {
		mContext = context;
		mGroupList = groupList;
		mProductList = productList;
		notifyDataSetChanged();
	}

	public void stop() {
		// TODO: stop image loader
	}

	public void setSelectedPosition(int groupPosition, int childPosition) {
		mSelectedGroupPosition = groupPosition;
		mSelectedChildPosition = childPosition;
		notifyDataSetChanged();
	}

	public int getSelectedGroupPosition() {
		return mSelectedGroupPosition;
	}

	public int getSelectedChildPosition() {
		return mSelectedChildPosition;
	}

	static class ViewHolder {
		TextView nameTextView;
	}

	static class ViewHolderGroup {
		TextView nameTextView;
	}
}
