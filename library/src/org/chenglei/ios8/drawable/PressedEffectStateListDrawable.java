package org.chenglei.ios8.drawable;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class PressedEffectStateListDrawable extends StateListDrawable {

	private int mColorNormal;
    private int mColorPressed;

    public PressedEffectStateListDrawable(Drawable drawable, int colorNormal, int colorPressed) {
        super();
        this.mColorNormal = colorNormal;
        this.mColorPressed = colorPressed;
        addState(new int[] { android.R.attr.state_pressed }, drawable);
        addState(new int[] {}, drawable);
    }

    @Override
    protected boolean onStateChange(int[] states) {
        boolean isStatePressedInArray = false;
        for (int state : states) {
            if (state == android.R.attr.state_pressed) {
                isStatePressedInArray = true;
            }
        }
        if (isStatePressedInArray) {
        	super.setColorFilter(mColorPressed, PorterDuff.Mode.SRC_IN);
        } else {
        	super.setColorFilter(mColorNormal, PorterDuff.Mode.SRC_IN);
        }
        return super.onStateChange(states);
    }

    @Override
    public boolean isStateful() {
        return true;
    }
}
