package org.chenglei.ios8;

import org.chenglei.ios8.drawable.PressedEffectStateListDrawable;
import org.chenglei.navigationbar.R;
import org.chenglei.utils.ColorUtil;
import org.chenglei.utils.DrawableUtil;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NavigationBar extends RelativeLayout {
	
	private static final int DEFAULT_BUTTON_TEXT_COLOR = 0xFF007AFF;
	
	private RelativeLayout mContainer;
	
	private View mLeftButton;
	
	public NavigationBar(Context context) {
		this(context, null, 0);
	}
	
	public NavigationBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		readAttrs(context, attrs, defStyleAttr);
		init();
	}

	private void readAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar, defStyleAttr, 0);
		a.recycle();
	}
	
	private void init() {
		setBackgroundColor(Color.WHITE);
		mContainer = new RelativeLayout(getContext());
		addView(mContainer, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
	}
	
	public void setLeftButton(CharSequence text, Drawable drawable, View.OnClickListener l) {
		setLeftButton(text, drawable, DEFAULT_BUTTON_TEXT_COLOR, l);
	}
	
	public void setLeftButton(CharSequence text, Drawable drawable, int buttonTextColor, View.OnClickListener l) {
		int buttonTextColorPressed = buttonTextColor - 0x88000000;
		ColorStateList colorStateList = ColorUtil.createColorStateList(buttonTextColor, buttonTextColorPressed);
		Drawable stateListDrawable = new PressedEffectStateListDrawable(drawable, buttonTextColor, buttonTextColorPressed);
		
		TextView leftButton = new TextView(getContext());
		leftButton.setText(text);
		leftButton.setTextColor(colorStateList);
		leftButton.setCompoundDrawablesWithIntrinsicBounds(stateListDrawable, null, null, null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, 
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		addView(leftButton, params);
		leftButton.setOnClickListener(l);
	}
}
