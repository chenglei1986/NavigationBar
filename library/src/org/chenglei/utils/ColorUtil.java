package org.chenglei.utils;

import android.content.res.ColorStateList;

public class ColorUtil {
	
	public static ColorStateList createColorStateList(int colorNormal, int colorPressed) {
		int[] colors = new int[] { colorPressed, colorNormal };
		int[][] states = new int[2][];
		states[0] = new int[] { android.R.attr.state_pressed };
		states[1] = new int[] {};
		ColorStateList colorList = new ColorStateList(states, colors);
		return colorList;
	}
	
}
