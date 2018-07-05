package com.example.utility;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.service.ExampleService;

public final class JobSchedulerUtility {
	public static final int EXAMPLE_JOB = 0;

	private JobSchedulerUtility() {}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static void scheduleExampleJob(Context context) {
		ComponentName componentName = new ComponentName(context, ExampleService.class);

		JobInfo.Builder builder = new JobInfo.Builder(EXAMPLE_JOB, componentName);
		builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);

		JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
		jobScheduler.schedule(builder.build());
	}
}
