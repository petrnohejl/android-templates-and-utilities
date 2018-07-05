package com.example.task;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class LoadDataTask extends AsyncTask<Void, Void, String> {
	private WeakReference<OnLoadDataListener> mOnLoadDataListener;

	public interface OnLoadDataListener {
		void onLoadData();
	}

	public LoadDataTask(OnLoadDataListener onLoadDataListener) {
		setListener(onLoadDataListener);
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			// TODO: do something
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if (isCancelled()) return;

		OnLoadDataListener listener = mOnLoadDataListener.get();
		if (listener != null) {
			listener.onLoadData();
		}
	}

	public void setListener(OnLoadDataListener onLoadDataListener) {
		mOnLoadDataListener = new WeakReference<>(onLoadDataListener);
	}
}
