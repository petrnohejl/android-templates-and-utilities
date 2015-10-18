package com.example.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;


public final class BitmapReflection
{
	private BitmapReflection() {}


	public static Bitmap getReflectedBitmap(Bitmap bitmap, int gap)
	{
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionBitmap = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);
		Bitmap finalBitmap = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, finalBitmap.getHeight() + gap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);

		Paint paint = new Paint();
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

		Canvas canvas = new Canvas(finalBitmap);
		canvas.drawBitmap(bitmap, 0, 0, null);
		canvas.drawRect(0, height, width, height + gap, new Paint());
		canvas.drawBitmap(reflectionBitmap, 0, height + gap, null);
		canvas.drawRect(0, height, width, finalBitmap.getHeight() + gap, paint);

		reflectionBitmap.recycle();

		return finalBitmap;
	}


	public static Bitmap getReflection(Bitmap bitmap)
	{
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionBitmap = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);
		Bitmap finalBitmap = Bitmap.createBitmap(width, (height / 2), Bitmap.Config.ARGB_8888);

		LinearGradient shader = new LinearGradient(0, 0, 0, finalBitmap.getHeight(), 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);

		Paint paint = new Paint();
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

		Canvas canvas = new Canvas(finalBitmap);
		canvas.drawBitmap(bitmap, 0, 0, null);
		canvas.drawBitmap(reflectionBitmap, 0, 0, null);
		canvas.drawRect(0, 0, width, finalBitmap.getHeight(), paint);

		reflectionBitmap.recycle();

		return finalBitmap;
	}
}
