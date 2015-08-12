package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.R;


public class ExampleFragment extends Fragment
{
	private View mRootView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	private void bindData()
	{
		// reference
		Button exampleButton = (Button) mRootView.findViewById(R.id.fragment_example_button);

		// popup menu
		exampleButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				PopupMenu popup = new PopupMenu(getActivity(), view);
				popup.getMenuInflater().inflate(R.menu.menu_example, popup.getMenu());
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
				{
					public boolean onMenuItemClick(MenuItem item)
					{
						// TODO

						return true;
					}
				});
				popup.show();
			}
		});
	}
}
