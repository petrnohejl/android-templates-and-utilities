package com.example.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.example.R;
import com.example.graphics.BitmapScaler;


public class ImagePicker
{
	public static final int ACTION_PICK_IMAGE_FROM_CAMERA = 0;
	public static final int ACTION_PICK_IMAGE_FROM_GALLERY = 1;
	
	private Context mContext;
	private int mImageSize = 640;
	private String mImageFromCameraPath = null;
	
	
	public ImagePicker(Context context)
	{
		mContext = context;
	}
	
	
	public ImagePicker(Context context, int imageSize)
	{
		mContext = context;
		mImageSize = imageSize;
	}
	
	
	public void pickImageFromCamera(Fragment fragment)
	{
		File file;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		try
		{
			file = createImageFile(fragment.getString(R.string.app_name));
			mImageFromCameraPath = file.getAbsolutePath();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			mImageFromCameraPath = null;
		}
		
		fragment.startActivityForResult(intent, ACTION_PICK_IMAGE_FROM_CAMERA);
	}
	
	
	public void pickImageFromGallery(Fragment fragment)
	{
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/jpeg");
		fragment.startActivityForResult(intent, ACTION_PICK_IMAGE_FROM_GALLERY);
	}
	
	
	public Bitmap handleImageFromCamera()
	{
		Bitmap bitmap = null;
		
		if(mImageFromCameraPath!=null)
		{
			addImageToGallery(mImageFromCameraPath);
			bitmap = handleImage(mImageFromCameraPath);
			mImageFromCameraPath = null;
		}
		
		return bitmap;
	}
	
	
	public Bitmap handleImageFromGallery(Intent data)
	{
		Bitmap bitmap = null;
		
		Uri imageFromGalleryUri = data.getData();
		String imageFromGalleryPath = getPathFromUri(imageFromGalleryUri);
		
		if(imageFromGalleryPath!=null)
		{
			bitmap = handleImage(imageFromGalleryPath);
		}
		
		return bitmap;
	}
	
	
	private File createImageFile(String albumDirectoryName) throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "IMG_" + timeStamp + "_";
		File albumFile = getAlbumDirectory(albumDirectoryName);
		File imageFile = File.createTempFile(imageFileName, ".jpg", albumFile);
		return imageFile;
	}
	
	
	private File getAlbumDirectory(String albumDirectoryName)
	{
		File storageDirectory = null;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			storageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumDirectoryName);
			if(storageDirectory != null)
			{
				if(!storageDirectory.mkdirs())
				{
					if(!storageDirectory.exists())
					{
						Logcat.d("ImagePicker.getAlbumDirectory(): failed to create album directory");
						return null;
					}
				}
			}
		}
		else
		{
			Logcat.d("ImagePicker.getAlbumDirectory(): external storage is not mounted");
		}
		return storageDirectory;
	}
	
	
	private String getPathFromUri(Uri uri)
	{
		String path = null;
		if(mContext!=null)
		{
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
			if(cursor.moveToFirst())
			{
				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				path = cursor.getString(columnIndex);
			}
			cursor.close();
		}
		return path;
	}
	
	
	private void addImageToGallery(String imageFromCameraPath)
	{
		Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File file = new File(imageFromCameraPath);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		if(mContext!=null) mContext.sendBroadcast(intent);
	}
	
	
	private Bitmap handleImage(String imagePath)
	{
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, bitmapOptions);
		
		// set bitmap options to scale the image decode target
		bitmapOptions.inJustDecodeBounds = false;
		bitmapOptions.inSampleSize = 4;
		bitmapOptions.inPurgeable = true;
		
		// decode the JPEG file into a bitmap
		Bitmap originalBitmap = null;
		Bitmap bitmap = null;
		try
		{
			originalBitmap = BitmapFactory.decodeFile(imagePath, bitmapOptions);
			bitmap = BitmapScaler.scaleToFill(originalBitmap, mImageSize, mImageSize);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(originalBitmap!=null) originalBitmap.recycle();
		}
		
		// handle bitmap rotation
		int rotation = getBitmapRotation(imagePath);
		if(rotation>0)
		{
			Matrix matrix = new Matrix();
			matrix.setRotate(rotation, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}
		
		return bitmap;
	}
	
	
	private int getBitmapRotation(String imagePath)
	{
		int rotation = 0;
		switch(getExifOrientation(imagePath))
		{
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotation = 90;
				break;
				
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotation = 180;
				break;
				
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotation = 270;
				break;
				
			default:
				rotation = 0;
				break;
		}
		return rotation;
	}
	
	
	private int getExifOrientation(String imagePath)
	{
		int orientation = 0;
		try
		{
			ExifInterface exif = new ExifInterface(imagePath);
			orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return orientation;
	}
}
