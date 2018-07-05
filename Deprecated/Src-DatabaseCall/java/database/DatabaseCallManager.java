package com.example.database;

import android.os.AsyncTask;
import android.os.Build;

import com.example.database.query.Query;

import org.alfonz.utility.Logcat;

import java.util.LinkedList;
import java.util.List;

public class DatabaseCallManager {
	private List<DatabaseCallTask> mTaskList = new LinkedList<>();

	public DatabaseCallManager() {
	}

	public void executeTask(Query query, DatabaseCallListener listener) {
		DatabaseCallTask task = new DatabaseCallTask(query, listener);
		mTaskList.add(task);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// use AsyncTask.THREAD_POOL_EXECUTOR or AsyncTask.SERIAL_EXECUTOR
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			task.execute();
		}
	}

	public boolean finishTask(DatabaseCallTask task) {
		return mTaskList.remove(task);
	}

	public int getTasksCount() {
		return mTaskList.size();
	}

	public boolean hasRunningTask(Class<?> queryClass) {
		String className = queryClass.getSimpleName();

		for (DatabaseCallTask task : mTaskList) {
			String taskName = task.getQuery().getClass().getSimpleName();
			if (className.equals(taskName)) return true;
		}

		return false;
	}

	public void cancelAllTasks() {
		for (int i = mTaskList.size() - 1; i >= 0; i--) {
			DatabaseCallTask task = mTaskList.get(i);
			if (task != null) {
				task.cancel(true);
				mTaskList.remove(task);
			}
		}
	}

	public void printRunningTasks() {
		for (DatabaseCallTask task : mTaskList) {
			Logcat.d(task == null ? "null" : (task.getQuery().getClass().getSimpleName() + " " + task.getStatus().toString()));
		}

		if (mTaskList.isEmpty()) Logcat.d("empty");
	}
}
