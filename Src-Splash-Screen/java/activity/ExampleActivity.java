package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;

public class ExampleActivity extends AppCompatActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Example_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
	}
}
