package com.example.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;


public class RoundedDrawable extends Drawable
{
	private Bitmap mBitmap;
	private float mCornerRadius;
	private int mMargin;
	private Paint mPaint;
	private BitmapShader mBitmapShader;
	private RectF mRect = new RectF();


	public RoundedDrawable(Bitmap bitmap, float cornerRadius)
	{
		this(bitmap, cornerRadius, 0);
	}


	public RoundedDrawable(Bitmap bitmap, float cornerRadius, int margin)
	{
		mBitmap = bitmap;
		mCornerRadius = cornerRadius;
		mMargin = margin;

		mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setShader(mBitmapShader);
	}


	@Override
	protected void onBoundsChange(Rect bounds)
	{
		super.onBoundsChange(bounds);
		mRect.set(mMargin, mMargin, bounds.width() - mMargin, bounds.height() - mMargin);
	}


	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, mPaint);
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
		return mBitmap.getWidth();
	}


	@Override
	public int getIntrinsicHeight()
	{
		return mBitmap.getHeight();
	}
}
