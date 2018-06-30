package com.example.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.R;
import com.example.adapter.DrawerAdapter;
import com.example.fragment.ExampleFragment;


public class ExampleActivity extends AppCompatActivity
{
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerListView;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	private String[] mTitles;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		setupActionBar();
		setupDrawer(savedInstanceState);
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		// action bar menu visibility
		if(menu != null)
		{
			boolean drawerOpened = mDrawerLayout.isDrawerOpen(mDrawerListView);
			MenuItem refresh = menu.findItem(R.id.menu_example_refresh);
			if(refresh != null) refresh.setVisible(!drawerOpened);
		}
		return super.onPrepareOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// open or close the drawer if home button is pressed
		if(mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}

		// action bar menu behavior
		switch(item.getItemId())
		{
			case android.R.id.home:
				// TODO
				return true;

			case R.id.menu_example_refresh:
				// TODO
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}


	@Override
	public void onConfigurationChanged(Configuration newConfiguration)
	{
		super.onConfigurationChanged(newConfiguration);
		mDrawerToggle.onConfigurationChanged(newConfiguration);
	}


	@Override
	public void onBackPressed()
	{
		if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
		{
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		}
		else
		{
			super.onBackPressed();
		}
	}


	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}


	private void setupActionBar()
	{
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar bar = getSupportActionBar();
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setHomeButtonEnabled(true);
	}


	private void setupDrawer(Bundle savedInstanceState)
	{
		mTitle = getTitle();
		mDrawerTitle = getTitle();

		mTitles = new String[4];
		mTitles[0] = getString(R.string.title_example);
		mTitles[1] = getString(R.string.title_example);
		mTitles[2] = getString(R.string.title_example);
		mTitles[3] = getString(R.string.title_example);

		Integer[] icons = new Integer[4];
		icons[0] = R.drawable.ic_drawer_example;
		icons[1] = R.drawable.ic_drawer_example;
		icons[2] = R.drawable.ic_drawer_example;
		icons[3] = R.drawable.ic_drawer_example;

		// reference
		mDrawerLayout = findViewById(R.id.example_drawer_layout);
		mDrawerListView = findViewById(R.id.example_drawer_list);

		// set drawer
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerListView.setAdapter(new DrawerAdapter(this, mTitles, icons));
		mDrawerListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View clickedView, int position, long id)
			{
				selectDrawerItem(position);
			}
		});
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
		{
			@Override
			public void onDrawerClosed(View view)
			{
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();
			}


			@Override
			public void onDrawerOpened(View drawerView)
			{
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.addDrawerListener(mDrawerToggle);

		// show initial fragment
		if(savedInstanceState == null)
		{
			selectDrawerItem(0);
		}
	}


	private void selectDrawerItem(int position)
	{
		Fragment fragment;
		if(position == 0) fragment = ExampleFragment.newInstance();
		else if(position == 1) fragment = ExampleFragment.newInstance();
		else if(position == 2) fragment = ExampleFragment.newInstance();
		else if(position == 3) fragment = ExampleFragment.newInstance();
		else fragment = ExampleFragment.newInstance();

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container_drawer_content, fragment).commitAllowingStateLoss();

		mDrawerListView.setItemChecked(position, true);
		setTitle(mTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerListView);
	}
}
