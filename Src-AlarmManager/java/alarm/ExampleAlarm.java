package com.example.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.receiver.ExampleAlarmReceiver;


public class ExampleAlarm
{
	private static final int ALARM_ID = 0;
	private static final long ALARM_INTERVAL = AlarmManager.INTERVAL_HOUR; // in milliseconds
	
	private Context mContext;
	
	
	public ExampleAlarm(Context context)
	{
		mContext = context;
	}
	
	
	public void startAlarm()
	{
		AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC, getStartTime(), ALARM_INTERVAL, getPendingIntent()); // TODO: use AlarmManager.RTC or AlarmManager.RTC_WAKEUP
	}
	
	
	public void stopAlarm()
	{
		AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(getPendingIntent());
	}
	
	
	public boolean isRunning()
	{
		Intent alarmIntent = new Intent(mContext, ExampleAlarmReceiver.class);
		return PendingIntent.getBroadcast(mContext, ALARM_ID, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null;
	}
	
	
	private long getStartTime()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MILLISECOND, (int) ALARM_INTERVAL);
		return calendar.getTimeInMillis();
	}
	
	
	private PendingIntent getPendingIntent()
	{
		Intent alarmIntent = new Intent(mContext, ExampleAlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		return pendingIntent;
	}
}
