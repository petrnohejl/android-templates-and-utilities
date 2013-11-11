package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.R;


public class ExampleActivity extends ActionBarActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_example);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		MenuInflater menuInflater = new MenuInflater(this);
		menuInflater.inflate(R.menu.menu_example, menu);
		
		// search view
		SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
		setSearchView(searchView);
		
		// search menu item
		MenuItem searchMenuItem = menu.add(Menu.NONE, Menu.NONE, 1, R.string.ab_button_search);
		searchMenuItem.setIcon(R.drawable.ic_menu_search);
		MenuItemCompat.setActionView(searchMenuItem, searchView);
		MenuItemCompat.setShowAsAction(searchMenuItem, MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	private void setSearchView(SearchView searchView)
	{
		// search hint
		searchView.setQueryHint(getString(R.string.ab_button_search_hint));
		
		// background
		View searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
		searchPlate.setBackgroundResource(R.drawable.ab_searchview_bg);
		
		// clear button
		ImageView searchClose = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
		searchClose.setImageResource(R.drawable.ab_ic_clear_search);
		
		// text color
		AutoCompleteTextView searchText = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
		searchText.setTextColor(getResources().getColor(R.color.global_text_primary));
		searchText.setHintTextColor(getResources().getColor(R.color.global_text_secondary));
	}
}
