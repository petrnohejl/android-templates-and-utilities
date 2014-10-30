package com.example.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;


public class CircularDrawable extends Drawable
{
	private Bitmap mBitmap;
	private float mDiameter;
	private Paint mPaint;
	private BitmapShader mBitmapShader;


	public CircularDrawable(Bitmap bitmap)
	{
		this(bitmap, bitmap.getWidth()<bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight());
	}


	public CircularDrawable(Bitmap bitmap, float diameter)
	{
		mBitmap = bitmap;
		mDiameter = diameter;

		mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

		mPaint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setShader(mBitmapShader);
	}


	@Override
	protected void onBoundsChange(Rect bounds)
	{
		super.onBoundsChange(bounds);

		float centerX = mBitmap.getWidth()/2f;
		float centerY = mBitmap.getHeight()/2f;
		float diameterMinX = Math.min(mDiameter, mBitmap.getWidth());
		float diameterMinY = Math.min(mDiameter, mBitmap.getHeight());

		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.setTranslate(-centerX + diameterMinX / 2, -centerY + diameterMinY / 2);

		mBitmapShader.setLocalMatrix(matrix);
	}


	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawCircle(getIntrinsicWidth()/2, getIntrinsicHeight()/2, mDiameter / 2, mPaint);
	}


	@Override
	public void setAlpha(int alpha)
	{
		mPaint.setAlpha(alpha);
	}


	@Override
	public void setColorFilter(ColorFilter colorFilter)
	{
		mPaint.setColorFilter(colorFilter);
	}


	@Override
	public int getOpacity()
	{
		return PixelFormat.TRANSLUCENT;
	}


	@Override
	public int getIntrinsicWidth()
	{
		return (int) Math.min(mBitmap.getWidth(), mDiameter);
	}


	@Override
	public int getIntrinsicHeight()
	{
		return (int) Math.min(mBitmap.getHeight(), mDiameter);
	}
}
