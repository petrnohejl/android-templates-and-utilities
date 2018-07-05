package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.example.R;
import com.example.adapter.TabHostFragmentPagerAdapter;
import com.example.fragment.ExampleFragment;

public class TabHostActivity extends AppCompatActivity {
	private TabHostFragmentPagerAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabhost);
		setupView();
	}

	private void setupView() {
		// reference
		ViewPager viewPager = findViewById(R.id.tabhost_pager);
		TabHost tabHost = findViewById(android.R.id.tabhost);

		// tab host
		tabHost.setup();

		// pager content
		if (mAdapter == null) {
			// create adapter
			mAdapter = new TabHostFragmentPagerAdapter(this, tabHost, viewPager);
			mAdapter.addTab(tabHost.newTabSpec("tag0").setIndicator("Title 0"), ExampleFragment.class, null);
			mAdapter.addTab(tabHost.newTabSpec("tag1").setIndicator("Title 1"), ExampleFragment.class, null);
			mAdapter.addTab(tabHost.newTabSpec("tag2").setIndicator("Title 2"), ExampleFragment.class, null);
		} else {
			// refill adapter
			mAdapter.refill();
		}
	}
}
