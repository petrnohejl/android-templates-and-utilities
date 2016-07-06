package com.example.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;


public final class BitmapBlur
{
	private static final float BITMAP_SCALE = 0.4f;
	private static final float BLUR_RADIUS = 20f;


	private BitmapBlur() {}


	public static Bitmap getBlurredBitmap(Context context, Bitmap bitmap)
	{
		if(Build.VERSION.SDK_INT < 17) return Bitmap.createBitmap(bitmap);

		int width = Math.round(bitmap.getWidth() * BITMAP_SCALE);
		int height = Math.round(bitmap.getHeight() * BITMAP_SCALE);

		Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

		RenderScript rs = RenderScript.create(context);
		ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
		Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
		scriptIntrinsicBlur.setRadius(BLUR_RADIUS);
		scriptIntrinsicBlur.setInput(tmpIn);
		scriptIntrinsicBlur.forEach(tmpOut);
		tmpOut.copyTo(outputBitmap);
		rs.destroy();

		return outputBitmap;
	}
}
