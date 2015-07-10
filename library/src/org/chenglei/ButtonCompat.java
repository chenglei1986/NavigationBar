package org.chenglei;

import android.os.Build;
import android.widget.Button;

public class ButtonCompat {
	
	public static void setAllCaps(Button button, boolean allCaps) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
			button.setAllCaps(allCaps);
		}
	}

}
