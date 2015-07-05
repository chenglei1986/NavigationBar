package org.chenglei.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableUtil {
	
	public static Drawable getColoredDrawable(Context context, int id, int color) {
		return getColoredDrawable(context, getDrawable(context, id), color);
	}
	
	public static Drawable getColoredDrawable(Context context, Drawable raw, int color) {
		if (raw != null) {
			DrawableCompat.setTint(DrawableCompat.wrap(raw), color);
		}
		return raw;
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static Drawable getDrawable(Context context, int id) {
		Drawable drawable = null;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
			drawable = context.getResources().getDrawable(id, null);
		} else {
			drawable = context.getResources().getDrawable(id);
		}
		return drawable;
	}
	
}
