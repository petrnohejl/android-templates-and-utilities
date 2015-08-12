package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
		TextView descriptionTextView = (TextView) mRootView.findViewById(R.id.fragment_example_description);
		
		// color highlight
		String colorHighlight = toHtmlColor(R.color.global_text_secondary);
		
		// remove html tags
		String nameWithoutHtml = removeHtmlTags("<u>Petr</u>", true);

		// text
		StringBuilder builder = new StringBuilder();
		builder.append("I am ");
		builder.append("<b><font color='" + colorHighlight + "'>");
		builder.append(nameWithoutHtml);
		builder.append("</font></b>");
		builder.append(" and I love <a href='http://android.com'>Android</a>!");

		// set html text
		// beware of this bug: https://code.google.com/p/android/issues/detail?id=35412, https://code.google.com/p/android/issues/detail?id=34913
		descriptionTextView.setText(Html.fromHtml(builder.toString()), TextView.BufferType.SPANNABLE);
		descriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	
	private String toHtmlColor(int colorResource)
	{
		return "#" + Integer.toHexString(getResources().getColor(colorResource)).substring(2);
	}
	
	
	private String removeHtmlTags(String htmlText, boolean removeBreak)
	{
		String text = Html.fromHtml(htmlText).toString();
		if(removeBreak) text = text.replace("\n", " ").replace("\r", "");
		return text;
	}
}
