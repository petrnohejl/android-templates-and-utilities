package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ExampleFragment extends Fragment {
	private static final String ARGUMENT_PRODUCT_ID = "product_id";
	private static final String SAVED_LIST_POSITION = "list_position";

	public static ExampleFragment newInstance(String productId) {
		ExampleFragment fragment = new ExampleFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putString(ARGUMENT_PRODUCT_ID, productId);
		fragment.setArguments(arguments);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// handle fragment arguments
		Bundle arguments = getArguments();
		if (arguments != null) {
			handleArguments(arguments);
		}

		// restore saved state
		if (savedInstanceState != null) {
			handleSavedInstanceState(savedInstanceState);
		}

		// handle intent extras
		Bundle extras = getActivity().getIntent().getExtras();
		if (extras != null) {
			handleExtras(extras);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// save current instance state
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);

		// TODO
	}

	private void handleArguments(Bundle arguments) {
		// TODO
	}

	private void handleSavedInstanceState(Bundle savedInstanceState) {
		// TODO
	}

	private void handleExtras(Bundle extras) {
		// TODO
	}
}
