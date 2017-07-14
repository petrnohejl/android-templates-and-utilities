package com.example.viewmodel;

import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ui.ExampleView;
import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import org.alfonz.rx.AlfonzDisposableMaybeObserver;
import org.alfonz.rx.AlfonzDisposableObserver;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.rx.RxManager;
import org.alfonz.utility.Logcat;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;


public class ExampleViewModel extends BaseViewModel<ExampleView>
{
	private static final String LOCATION_SETTINGS_CALL_TYPE = "location_settings";
	private static final String LAST_LOCATION_CALL_TYPE = "last_location";
	private static final String SINGLE_LOCATION_CALL_TYPE = "single_location";
	private static final String SINGLE_ADDRESS_CALL_TYPE = "single_address";
	private static final String CONTINUOUS_LOCATION_CALL_TYPE = "continuous_location";
	private static final String CONTINUOUS_ADDRESS_CALL_TYPE = "continuous_address";

	private final RxManager mRxManager = new RxManager();
	private final RxLocation mRxLocation = new RxLocation(getApplicationContext());
	private Disposable mSingleLocationDisposable;
	private Disposable mSingleAddressDisposable;


	@Override
	public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState)
	{
		super.onCreate(arguments, savedInstanceState);
		mRxLocation.setDefaultTimeout(30, TimeUnit.SECONDS);
	}


	@Override
	public void onStart()
	{
		super.onStart();
		runContinuousLocationCall();
	}


	@Override
	public void onStop()
	{
		super.onStop();
		mRxManager.disposeAll();
	}


	private LocationRequest createSingleLocationRequest()
	{
		return LocationRequest.create()
				.setNumUpdates(1)
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}


	private LocationRequest createContinuousLocationRequest()
	{
		return LocationRequest.create()
				.setInterval(10000)
				.setFastestInterval(5000)
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}


	private void runLocationSettingsCall()
	{
		if(!mRxManager.isRunning(LOCATION_SETTINGS_CALL_TYPE))
		{
			Single<Boolean> rawSingle = mRxLocation.settings().checkAndHandleResolution(createSingleLocationRequest());
			Single<Boolean> single = mRxManager.setupSingleWithSchedulers(rawSingle, LOCATION_SETTINGS_CALL_TYPE);
			single.subscribeWith(createLocationSettingsSingleObserver());
		}
	}


	@SuppressWarnings("MissingPermission")
	private void runLastLocationCall()
	{
		if(!mRxManager.isRunning(LAST_LOCATION_CALL_TYPE))
		{
			Maybe<Location> rawMaybe = mRxLocation.location().lastLocation();
			Maybe<Location> maybe = mRxManager.setupMaybeWithSchedulers(rawMaybe, LAST_LOCATION_CALL_TYPE);
			maybe.subscribeWith(createLastLocationMaybeObserver());
		}
	}


	@SuppressWarnings("MissingPermission")
	private void runSingleLocationCall()
	{
		if(!mRxManager.isRunning(SINGLE_LOCATION_CALL_TYPE))
		{
			Observable<Location> rawObservable = mRxLocation.location().updates(createSingleLocationRequest());
			Observable<Location> observable = mRxManager.setupObservableWithSchedulers(rawObservable, SINGLE_LOCATION_CALL_TYPE);
			mSingleLocationDisposable = observable.subscribeWith(createSingleLocationObserver());
		}
	}


	@SuppressWarnings("MissingPermission")
	private void runSingleAddressCall()
	{
		if(!mRxManager.isRunning(SINGLE_ADDRESS_CALL_TYPE))
		{
			Observable<Address> rawObservable = mRxLocation.location()
					.updates(createSingleLocationRequest())
					.flatMap(location -> mRxLocation.geocoding().fromLocation(location).toObservable());
			Observable<Address> observable = mRxManager.setupObservableWithSchedulers(rawObservable, SINGLE_ADDRESS_CALL_TYPE);
			mSingleAddressDisposable = observable.subscribeWith(createSingleAddressObserver());
		}
	}


	@SuppressWarnings("MissingPermission")
	private void runContinuousLocationCall()
	{
		if(!mRxManager.isRunning(CONTINUOUS_LOCATION_CALL_TYPE))
		{
			Observable<Location> rawObservable = mRxLocation.location().updates(createContinuousLocationRequest());
			Observable<Location> observable = mRxManager.setupObservableWithSchedulers(rawObservable, CONTINUOUS_LOCATION_CALL_TYPE);
			observable.subscribeWith(createContinuousLocationObserver());
		}
	}


	@SuppressWarnings("MissingPermission")
	private void runContinuousAddressCall()
	{
		if(!mRxManager.isRunning(CONTINUOUS_ADDRESS_CALL_TYPE))
		{
			Observable<Address> rawObservable = mRxLocation.location()
					.updates(createContinuousLocationRequest())
					.flatMap(location -> mRxLocation.geocoding().fromLocation(location).toObservable());
			Observable<Address> observable = mRxManager.setupObservableWithSchedulers(rawObservable, CONTINUOUS_ADDRESS_CALL_TYPE);
			observable.subscribeWith(createContinuousAddressObserver());
		}
	}


	private DisposableSingleObserver<Boolean> createLocationSettingsSingleObserver()
	{
		return AlfonzDisposableSingleObserver.newInstance(
				data ->
				{
					Logcat.d(data.toString());
				},
				throwable ->
				{
					Logcat.e("onError", throwable);
				}
		);
	}


	private DisposableMaybeObserver<Location> createLastLocationMaybeObserver()
	{
		return AlfonzDisposableMaybeObserver.newInstance(
				data ->
				{
					Logcat.d(data.toString());
				},
				throwable ->
				{
					Logcat.e("onError", throwable);
				},
				() ->
				{
					Logcat.d("onComplete");
				}
		);
	}


	private DisposableObserver<Location> createSingleLocationObserver()
	{
		return AlfonzDisposableObserver.newInstance(
				data ->
				{
					Logcat.d(data.toString());
					mSingleLocationDisposable.dispose();
				},
				throwable ->
				{
					Logcat.e("onError", throwable);
				},
				() ->
				{
					Logcat.d("onComplete");
				}
		);
	}


	private DisposableObserver<Address> createSingleAddressObserver()
	{
		return AlfonzDisposableObserver.newInstance(
				data ->
				{
					Logcat.d(data.toString());
					mSingleAddressDisposable.dispose();
				},
				throwable ->
				{
					Logcat.e("onError", throwable);
				},
				() ->
				{
					Logcat.d("onComplete");
				}
		);
	}


	private DisposableObserver<Location> createContinuousLocationObserver()
	{
		return AlfonzDisposableObserver.newInstance(
				data ->
				{
					Logcat.d(data.toString());
				},
				throwable ->
				{
					Logcat.e("onError", throwable);
				},
				() ->
				{
					Logcat.d("onComplete");
				}
		);
	}


	private DisposableObserver<Address> createContinuousAddressObserver()
	{
		return AlfonzDisposableObserver.newInstance(
				data ->
				{
					Logcat.d(data.toString());
				},
				throwable ->
				{
					Logcat.e("onError", throwable);
				},
				() ->
				{
					Logcat.d("onComplete");
				}
		);
	}
}
