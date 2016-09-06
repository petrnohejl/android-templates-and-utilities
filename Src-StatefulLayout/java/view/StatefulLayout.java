package com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.R;

import java.util.ArrayList;
import java.util.List;


// inspired by: https://github.com/jakubkinst/Android-StatefulView
public class StatefulLayout extends FrameLayout
{
	private static final String SAVED_STATE = "stateful_layout_state";

	private State mInitialState;
	private int mProgressLayoutId;
	private int mOfflineLayoutId;
	private int mEmptyLayoutId;
	private List<View> mContentLayoutList;
	private View mProgressLayout;
	private View mOfflineLayout;
	private View mEmptyLayout;
	private State mState;
	private OnStateChangeListener mOnStateChangeListener;


	public enum State
	{
		CONTENT(0), PROGRESS(1), OFFLINE(2), EMPTY(3);

		private final int mValue;


		private State(int value)
		{
			mValue = value;
		}


		public static State valueToState(int value)
		{
			State[] values = State.values();
			return values[value];
		}


		public int getValue()
		{
			return mValue;
		}
	}


	public interface OnStateChangeListener
	{
		void onStateChange(View v, State state);
	}


	public StatefulLayout(Context context)
	{
		this(context, null);
	}


	public StatefulLayout(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}


	public StatefulLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulLayout);
		if(typedArray.hasValue(R.styleable.StatefulLayout_state))
		{
			int initialStateValue = typedArray.getInt(R.styleable.StatefulLayout_state, State.CONTENT.getValue());
			mInitialState = State.valueToState(initialStateValue);
		}
		if(typedArray.hasValue(R.styleable.StatefulLayout_progressLayout) &&
				typedArray.hasValue(R.styleable.StatefulLayout_offlineLayout) &&
				typedArray.hasValue(R.styleable.StatefulLayout_emptyLayout))
		{
			mProgressLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_progressLayout, 0);
			mOfflineLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_offlineLayout, 0);
			mEmptyLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_emptyLayout, 0);
		}
		else
		{
			throw new IllegalArgumentException("Attributes progressLayout, offlineLayout and emptyLayout are mandatory");
		}
		typedArray.recycle();
	}


	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		setupView();
	}


	public void showContent()
	{
		setState(State.CONTENT);
	}


	public void showProgress()
	{
		setState(State.PROGRESS);
	}


	public void showOffline()
	{
		setState(State.OFFLINE);
	}


	public void showEmpty()
	{
		setState(State.EMPTY);
	}


	public State getState()
	{
		return mState;
	}


	public void setState(State state)
	{
		mState = state;

		for(int i = 0; i < mContentLayoutList.size(); i++)
		{
			mContentLayoutList.get(i).setVisibility(state == State.CONTENT ? View.VISIBLE : View.GONE);
		}

		mProgressLayout.setVisibility(state == State.PROGRESS ? View.VISIBLE : View.GONE);
		mOfflineLayout.setVisibility(state == State.OFFLINE ? View.VISIBLE : View.GONE);
		mEmptyLayout.setVisibility(state == State.EMPTY ? View.VISIBLE : View.GONE);

		if(mOnStateChangeListener != null) mOnStateChangeListener.onStateChange(this, state);
	}


	public void setOnStateChangeListener(OnStateChangeListener l)
	{
		mOnStateChangeListener = l;
	}


	public void saveInstanceState(Bundle outState)
	{
		if(mState != null)
		{
			outState.putInt(SAVED_STATE, mState.getValue());
		}
	}


	public State restoreInstanceState(Bundle savedInstanceState)
	{
		State state = null;
		if(savedInstanceState != null && savedInstanceState.containsKey(SAVED_STATE))
		{
			int value = savedInstanceState.getInt(SAVED_STATE);
			state = StatefulLayout.State.valueToState(value);
			setState(state);
		}
		return state;
	}


	private void setupView()
	{
		if(mContentLayoutList == null && !isInEditMode())
		{
			mContentLayoutList = new ArrayList<>();
			for(int i = 0; i < getChildCount(); i++)
			{
				mContentLayoutList.add(getChildAt(i));
			}

			mProgressLayout = LayoutInflater.from(getContext()).inflate(mProgressLayoutId, this, false);
			mOfflineLayout = LayoutInflater.from(getContext()).inflate(mOfflineLayoutId, this, false);
			mEmptyLayout = LayoutInflater.from(getContext()).inflate(mEmptyLayoutId, this, false);

			addView(mProgressLayout);
			addView(mOfflineLayout);
			addView(mEmptyLayout);

			setState(mInitialState);
		}
	}
}
