package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import org.alfonz.utility.Logcat;

public class PhoneStateReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Logcat.d("");

		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		String msg = "Phone state changed to " + state;

		if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
			String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			msg += ". Incoming number is " + incomingNumber;
		}

		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

		// TODO: do something
	}
}
