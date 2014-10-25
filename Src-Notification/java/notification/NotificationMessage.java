package com.example.notification;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.R;


// requires android.permission.VIBRATE
public class NotificationMessage
{
	private Context mContext;
	private int mNotificationId;
	private Notification mNotification;
	
	
	private NotificationMessage(Context context, int notificationId, Notification notification)
	{
		mContext = context; // should be an application context
		mNotificationId = notificationId;
		mNotification = notification;
	}
	
	
	public void show()
	{
		// show notification
		NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(mNotificationId, mNotification);
	}
	
	
	public void cancel()
	{
		// cancel notification
		NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(mNotificationId);
	}
	
	
	public static class Builder
	{
		private Context mContext;
		private int mNotificationId;
		
		private PendingIntent mPendingIntent = null;
		private String mTicker = null;
		private String mTitle = null;
		private String mText = null;
		private String mSubText = null;
		private String mContentInfo = null;
		private int mNumber = -1;
		private long mWhen = System.currentTimeMillis();
		private int mSmallIcon = R.drawable.ic_stat_notify;
		private Bitmap mLargeIcon = null;
		private int mProgressMax = -1;
		private int mProgress = -1;
		private boolean mProgressIndeterminate = false;
		private boolean mUsesChronometer = false;
		private boolean mOnlyAlertOnce = false;
		private boolean mOnGoing = false;
		
		private int mAction1Icon = -1;
		private CharSequence mAction1Title = null;
		private PendingIntent mAction1PendingIntent = null;
		private int mAction2Icon = -1;
		private CharSequence mAction2Title = null;
		private PendingIntent mAction2PendingIntent = null;
		
		private CharSequence mBigTextStyleBigContentTitle = null;
		private CharSequence mBigTextStyleSummaryText = null;
		private CharSequence mBigTextStyleBigText = null;
		
		private CharSequence mBigPictureStyleBigContentTitle = null;
		private CharSequence mBigPictureStyleSummaryText = null;
		private Bitmap mBigPictureStyleBigPicture = null;
		
		private CharSequence mInboxStyleBigContentTitle = null;
		private CharSequence mInboxStyleSummaryText = null;
		private ArrayList<CharSequence> mInboxStyleLines = null;
		
		
		public Builder(Context context, int notificationId)
		{
			mContext = context; // should be an application context
			mNotificationId = notificationId;
		}
		
		
		public void setIntent(Intent notificationIntent)
		{
			// pending intent
			mPendingIntent = PendingIntent.getActivity(mContext, mNotificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		
		
		public void setText(String ticker, String title, String text)
		{
			mTicker = ticker;
			mTitle = title;
			mText = text;
		}
		
		
		public void setExtraText(String subText, String contentInfo)
		{
			mSubText = subText;
			mContentInfo = contentInfo;
		}
		
		
		public void setNumber(int number)
		{
			mNumber = number;
		}
		
		
		public void setTime(long when)
		{
			mWhen = when;
		}
		
		
		public void setIcon(int smallIcon, Bitmap largeIcon)
		{
			mSmallIcon = smallIcon;
			mLargeIcon = largeIcon;
		}
		
		
		public void setIcon(int smallIcon, int largeIcon)
		{
			mSmallIcon = smallIcon;
			mLargeIcon = BitmapFactory.decodeResource(mContext.getResources(), largeIcon);
		}
		
		
		public void setProgress(int max, int progress, boolean indeterminate)
		{
			// setProgress() does not work with setExtraText() method
			if(indeterminate)
			{
				mProgressMax = 0;
				mProgress = 0;
			}
			else
			{
				mProgressMax = max;
				mProgress = progress;
			}
			mProgressIndeterminate = indeterminate;
		}
		
		
		public void setUsesChronometer(boolean usesChronometer)
		{
			mUsesChronometer = usesChronometer;
		}
		
		
		public void setOnlyAlertOnce(boolean onlyAlertOnce)
		{
			mOnlyAlertOnce = onlyAlertOnce;
		}
		
		
		public void setOngoing(boolean ongoing)
		{
			mOnGoing = ongoing;
		}
		
		
		public void setAction1(int icon, CharSequence title, Intent actionIntent)
		{
			mAction1Icon = icon;
			mAction1Title = title;

			// pending intent
			mAction1PendingIntent = PendingIntent.getActivity(mContext, mNotificationId, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		
		
		public void setAction2(int icon, CharSequence title, Intent actionIntent)
		{
			mAction2Icon = icon;
			mAction2Title = title;

			// pending intent
			mAction2PendingIntent = PendingIntent.getActivity(mContext, mNotificationId, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		
		
		public void setBigTextStyle(CharSequence bigContentTitle, CharSequence summaryText, CharSequence bigText)
		{
			mBigTextStyleBigContentTitle = bigContentTitle;
			mBigTextStyleSummaryText = summaryText;
			mBigTextStyleBigText = bigText;
		}
		
		
		public void setBigPictureStyle(CharSequence bigContentTitle, CharSequence summaryText, Bitmap bigPicture)
		{
			mBigPictureStyleBigContentTitle = bigContentTitle;
			mBigPictureStyleSummaryText = summaryText;
			mBigPictureStyleBigPicture = bigPicture;
		}
		
		
		public void setBigPictureStyle(CharSequence bigContentTitle, CharSequence summaryText, int bigPicture)
		{
			mBigPictureStyleBigContentTitle = bigContentTitle;
			mBigPictureStyleSummaryText = summaryText;
			mBigPictureStyleBigPicture = BitmapFactory.decodeResource(mContext.getResources(), bigPicture);;
		}
		
		
		public void setInboxStyle(CharSequence bigContentTitle, CharSequence summaryText, ArrayList<CharSequence> lines)
		{
			mInboxStyleBigContentTitle = bigContentTitle;
			mInboxStyleSummaryText = summaryText;
			mInboxStyleLines = lines;
		}
		
		
		public NotificationMessage build()
		{
			// notification builder
			NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
			if(mPendingIntent!=null) builder.setContentIntent(mPendingIntent);
			if(mTicker!=null) builder.setTicker(mTicker);
			if(mTitle!=null) builder.setContentTitle(mTitle);
			if(mText!=null) builder.setContentText(mText);
			if(mSubText!=null) builder.setSubText(mSubText);
			if(mContentInfo!=null) builder.setContentInfo(mContentInfo);
			if(mNumber>0) builder.setNumber(mNumber);
			if(mWhen>0l) builder.setWhen(mWhen);
			if(mSmallIcon>0) builder.setSmallIcon(mSmallIcon);
			if(mLargeIcon!=null) builder.setLargeIcon(mLargeIcon);
			if(mProgressMax>=0 && mProgress>=0) builder.setProgress(mProgressMax, mProgress, mProgressIndeterminate);
			builder.setUsesChronometer(mUsesChronometer);
			builder.setOnlyAlertOnce(mOnlyAlertOnce);
			builder.setOngoing(mOnGoing);
			builder.setAutoCancel(!mOnGoing);
			builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
			builder.setDefaults(Notification.DEFAULT_ALL);
			
			// notification actions
			if(mAction1Icon>0 || mAction1Title!=null || mAction1PendingIntent!=null)
			{
				builder.addAction(mAction1Icon, mAction1Title, mAction1PendingIntent);
			}
			if(mAction2Icon>0 || mAction2Title!=null || mAction2PendingIntent!=null)
			{
				builder.addAction(mAction2Icon, mAction2Title, mAction2PendingIntent);
			}
			
			// notification style
			Notification notification;
			if(mBigTextStyleBigContentTitle!=null || mBigTextStyleSummaryText!=null || mBigTextStyleBigText!=null)
			{
				NotificationCompat.BigTextStyle notificationStyle = new NotificationCompat.BigTextStyle();
				notificationStyle.setBuilder(builder);
				if(mBigTextStyleBigContentTitle!=null) notificationStyle.setBigContentTitle(mBigTextStyleBigContentTitle);
				if(mBigTextStyleSummaryText!=null) notificationStyle.setSummaryText(mBigTextStyleSummaryText);
				if(mBigTextStyleBigText!=null) notificationStyle.bigText(mBigTextStyleBigText);
				notification = notificationStyle.build();
			}
			else if(mBigPictureStyleBigContentTitle!=null || mBigPictureStyleSummaryText!=null || mBigPictureStyleBigPicture!=null)
			{
				NotificationCompat.BigPictureStyle notificationStyle = new NotificationCompat.BigPictureStyle();
				notificationStyle.setBuilder(builder);
				if(mBigPictureStyleBigContentTitle!=null) notificationStyle.setBigContentTitle(mBigPictureStyleBigContentTitle);
				if(mBigPictureStyleSummaryText!=null) notificationStyle.setSummaryText(mBigPictureStyleSummaryText);
				if(mBigPictureStyleBigPicture!=null) notificationStyle.bigPicture(mBigPictureStyleBigPicture);
				notification = notificationStyle.build();
			}
			else if(mInboxStyleBigContentTitle!=null || mInboxStyleSummaryText!=null || mInboxStyleLines!=null)
			{
				NotificationCompat.InboxStyle notificationStyle = new NotificationCompat.InboxStyle();
				notificationStyle.setBuilder(builder);
				if(mInboxStyleBigContentTitle!=null) notificationStyle.setBigContentTitle(mInboxStyleBigContentTitle);
				if(mInboxStyleSummaryText!=null) notificationStyle.setSummaryText(mInboxStyleSummaryText);
				if(mInboxStyleLines!=null)
				{
					for(int i=0; i<mInboxStyleLines.size(); i++)
					{
						notificationStyle.addLine(mInboxStyleLines.get(i));
					}
				}
				notification = notificationStyle.build();
			}
			else
			{
				notification = builder.build();
			}
			
			return new NotificationMessage(mContext, mNotificationId, notification);
		}
	}
}
