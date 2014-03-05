package com.example.utility;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.ExampleApplication;


public class KeyboardUtility
{
	public static void showKeyboard(EditText editText)
	{
		if(editText != null)
		{
			editText.requestFocus();
			InputMethodManager inputMethodManager = (InputMethodManager) ExampleApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.showSoftInput(editText, 0);
		}
	}
	
	
	public static void hideKeyboard(EditText editText)
	{
		if(editText != null)
		{
			InputMethodManager inputMethodManager = (InputMethodManager) ExampleApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}
}
