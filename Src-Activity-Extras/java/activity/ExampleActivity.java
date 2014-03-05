package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.example.R;


public class ExampleActivity extends ActionBarActivity
{
	public static final String EXTRA_PRODUCT_ID = "product_id";
	public static final String EXTRA_PRODUCT_TITLE = "product_title";
	
	public static final String SAVE_LIST_POSITION = "list_position";
	public static final String SAVE_FORM_LOGIN = "form_login";
	public static final String SAVE_FORM_PASSWORD = "form_password";
	
	public static final int ACTION_PICK_IMAGE_FROM_CAMERA = 1;
	public static final int ACTION_PICK_IMAGE_FROM_GALLERY = 2;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_example);
	}
}
