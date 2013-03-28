package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.R;
import com.example.activity.ListingActivity;
import com.example.notification.NotificationId;
import com.example.notification.NotificationMessage;


public class NotificationFragment extends SherlockFragment
{
	private View mRootView;
	NotificationMessage mNotificationMessage;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.layout_notification, container, false);
		return mRootView;
	}
	
	
	private void showNotification()
	{
		NotificationMessage.Builder builder = new NotificationMessage.Builder(getActivity().getApplicationContext(), NotificationId.TYPE1);
		
		builder.setIntent(new Intent(getActivity().getApplicationContext(), ListingActivity.class));
		builder.setText("ticker", "title", "text");
		builder.setExtraText("subText", "contentInfo");
		builder.setNumber(24);
		builder.setTime(1356350400000l);
		builder.setIcon(R.drawable.ic_stat_notify, R.drawable.ic_launcher);
		builder.setProgress(100, 75, false);
		builder.setUsesChronometer(false);
		builder.setOnlyAlertOnce(false);
		builder.setOngoing(false);
		builder.setAction1(R.drawable.ic_stat_notify, "action 1", new Intent(getActivity().getApplicationContext(), ListingActivity.class));
		builder.setAction2(R.drawable.ic_stat_notify, "action 2", new Intent(getActivity().getApplicationContext(), ListingActivity.class));
		
		//builder.setBigTextStyle("bigContentTitle", "summaryText", "bigText");
		//builder.setBigPictureStyle("bigContentTitle", "summaryText", R.drawable.ic_launcher);
		
		//ArrayList<CharSequence> lines = new ArrayList<CharSequence>();
		//lines.add("line 1");
		//lines.add("line 2");
		//lines.add("line 3");
		//lines.add("line 4");
		//builder.setInboxStyle("bigContentTitle", "summaryText", lines);
		
		mNotificationMessage = builder.build();
		mNotificationMessage.show();
	}
	
	
	private void cancelNotification()
	{
		if(mNotificationMessage!=null) mNotificationMessage.cancel();
	}
}
