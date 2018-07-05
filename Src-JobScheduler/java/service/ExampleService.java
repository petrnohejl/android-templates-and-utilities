package com.example.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.alfonz.utility.Logcat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExampleService extends JobService {
	@Override
	public boolean onStartJob(JobParameters params) {
		doJob(params);
		return true;
	}

	@Override
	public boolean onStopJob(JobParameters params) {
		return true;
	}

	private void doJob(final JobParameters params) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Logcat.d("doing job");
					Thread.sleep(3000L);
					jobFinished(params, false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
