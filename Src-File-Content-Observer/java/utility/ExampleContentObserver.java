package com.example.utility;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

public class ExampleContentObserver extends ContentObserver {
	public ExampleContentObserver(Handler handler) {
		super(handler);
	}

	@Override
	public void onChange(boolean selfChange) {
		this.onChange(selfChange, null);
	}

	@Override
	public void onChange(boolean selfChange, Uri uri) {
		Logcat.d("" + uri);
	}
}
