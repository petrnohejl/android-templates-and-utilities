package com.example.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.entity.ProductEntity;

import java.util.List;
import java.util.Arrays;
import java.util.Set;

import pl.polidea.treeview.AbstractTreeViewAdapter;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;


public class TreeListingAdapter extends AbstractTreeViewAdapter<Long>
{
	private List<ProductEntity> mProductList;
	private Set<Long> mSelectedSet;


	public TreeListingAdapter(Activity activity, List<ProductEntity> productList, Set<Long> selectedSet, TreeStateManager<Long> treeStateManager, int treeviewDepth)
	{
		super(activity, treeStateManager, treeviewDepth);
		mProductList = productList;
		mSelectedSet = selectedSet;
	}


	@Override
	public LinearLayout updateView(View view, TreeNodeInfo<Long> treeNodeInfo)
	{
		// reference
		TextView nameTextView = (TextView) view.findViewById(R.id.fragment_tree_listing_item_name);
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.fragment_tree_listing_item_checkbox);

		// entity
		ProductEntity product = mProductList.get(treeNodeInfo.getId().intValue());

		// content
		String nodeLog = "" + Arrays.asList(getManager().getHierarchyDescription(treeNodeInfo.getId()));
		nameTextView.setText(product.getName() + " " + nodeLog);

		// checkbox listener
		checkBox.setTag(treeNodeInfo.getId());
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				Long id = (Long) buttonView.getTag();
				if(isChecked) mSelectedSet.add(id);
				else mSelectedSet.remove(id);
			}
		});

		// checkbox visibility
		if(treeNodeInfo.isWithChildren())
		{
			checkBox.setVisibility(View.GONE);
		}
		else
		{
			checkBox.setVisibility(View.VISIBLE);
			checkBox.setChecked(mSelectedSet.contains(treeNodeInfo.getId()));
		}

		return (LinearLayout) view;
	}


	@Override
	public View getNewChildView(TreeNodeInfo<Long> treeNodeInfo)
	{
		LinearLayout viewLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.fragment_tree_listing_item, null);
		return updateView(viewLayout, treeNodeInfo);
	}


	@Override
	public Drawable getBackgroundDrawable(TreeNodeInfo<Long> treeNodeInfo)
	{
		return ContextCompat.getDrawable(getActivity(), R.drawable.selector_selectable_item_bg);
	}


	@Override
	public void handleItemClick(View view, Object id)
	{
		TreeNodeInfo<Long> treeNodeInfo = getManager().getNodeInfo((Long) id);
		if(treeNodeInfo.isWithChildren())
		{
			super.handleItemClick(view, id);
		}
		else
		{
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.fragment_tree_listing_item_checkbox);
			checkBox.performClick();
		}
	}


	@Override
	public long getItemId(int position)
	{
		return getTreeId(position);
	}


	public void stop()
	{
		// TODO: stop image loader
	}
}
