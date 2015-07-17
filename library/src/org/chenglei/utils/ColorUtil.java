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
	
	public static ColorStateList createColorStateList(int colorNormal, int colorPressed, int colorFocused, int colorDisable) {  
        int[] colors = new int[] { colorPressed, colorFocused, colorNormal, colorFocused, colorDisable, colorNormal };  
        int[][] states = new int[6][];  
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };  
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };  
        states[2] = new int[] { android.R.attr.state_enabled };  
        states[3] = new int[] { android.R.attr.state_focused };  
        states[4] = new int[] { android.R.attr.state_window_focused };  
        states[5] = new int[] {};  
        ColorStateList colorList = new ColorStateList(states, colors);  
        return colorList;  
    }
	
}
