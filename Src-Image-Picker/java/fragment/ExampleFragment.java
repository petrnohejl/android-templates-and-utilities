package com.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.R;
import com.example.utility.ImagePicker;


public class ExampleFragment extends TaskFragment
{
	private View mRootView;
	private Bitmap mBitmap;
	private ImagePicker mImagePicker;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mImagePicker = new ImagePicker(getActivity());
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	@Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent data)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				switch(requestCode)
				{
					case ImagePicker.ACTION_PICK_IMAGE_FROM_CAMERA:
					{
						if(resultCode == Activity.RESULT_OK)
						{
							Bitmap bitmap = mImagePicker.handleImageFromCamera();
							if(bitmap != null)
							{
								mBitmap = bitmap;
								bindData();
							}
							else
							{
								// TODO
							}
						}
						else if(resultCode == Activity.RESULT_CANCELED)
						{
							// TODO
						}
						break;
					}

					case ImagePicker.ACTION_PICK_IMAGE_FROM_GALLERY:
					{
						if(resultCode == Activity.RESULT_OK)
						{
							Bitmap bitmap = mImagePicker.handleImageFromGallery(data);
							if(bitmap != null)
							{
								mBitmap = bitmap;
								bindData();
							}
							else
							{
								// TODO
							}
						}
						else if(resultCode == Activity.RESULT_CANCELED)
						{
							// TODO
						}
						break;
					}
				}
			}
		});
	}


	private void bindData()
	{
		// reference
		ImageView previewImageView = (ImageView) mRootView.findViewById(R.id.fragment_example_preview);
		Button cameraButton = (Button) mRootView.findViewById(R.id.fragment_example_camera);
		Button galleryButton = (Button) mRootView.findViewById(R.id.fragment_example_gallery);

		// content
		if(mBitmap != null) previewImageView.setImageBitmap(mBitmap);
		cameraButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mImagePicker.pickImageFromCamera(ExampleFragment.this);
			}
		});
		galleryButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mImagePicker.pickImageFromGallery(ExampleFragment.this);
			}
		});
	}
}
