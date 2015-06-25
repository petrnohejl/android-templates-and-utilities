package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.activity.ExampleActivity;
import com.example.notification.NotificationMessage;


public class ExampleFragment extends Fragment
{
	private View mRootView;
	private NotificationMessage mNotificationMessage;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}
	
	
	private void showNotification()
	{
		NotificationMessage.Builder builder = new NotificationMessage.Builder(getActivity().getApplicationContext(), NotificationMessage.Type.TYPE1);
		
		builder.setIntent(ExampleActivity.newIntent(getActivity().getApplicationContext())); // TODO: it is recommended to set FLAG_ACTIVITY_NEW_TASK and FLAG_ACTIVITY_CLEAR_TASK flags
		builder.setText("ticker", "title", "text");
		builder.setExtraText("subText", "contentInfo");
		builder.setNumber(24);
		builder.setTime(1356350400000l);
		builder.setIcon(R.drawable.ic_stat_notify, R.mipmap.ic_launcher);
		builder.setProgress(100, 75, false);
		builder.setUsesChronometer(false);
		builder.setOnlyAlertOnce(false);
		builder.setOngoing(false);
		builder.setAction1(R.drawable.ic_stat_notify, "action 1", ExampleActivity.newIntent(getActivity().getApplicationContext())); // TODO: it is recommended to set FLAG_ACTIVITY_NEW_TASK and FLAG_ACTIVITY_CLEAR_TASK flags
		builder.setAction2(R.drawable.ic_stat_notify, "action 2", ExampleActivity.newIntent(getActivity().getApplicationContext())); // TODO: it is recommended to set FLAG_ACTIVITY_NEW_TASK and FLAG_ACTIVITY_CLEAR_TASK flags
		
		//builder.setBigTextStyle("bigContentTitle", "summaryText", "bigText");
		//builder.setBigPictureStyle("bigContentTitle", "summaryText", R.mipmap.ic_launcher);
		
		//List<CharSequence> lines = new ArrayList<>();
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
