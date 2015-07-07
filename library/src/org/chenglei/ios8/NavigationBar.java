package org.chenglei.ios8;

import org.chenglei.ios8.drawable.PressedEffectStateListDrawable;
import org.chenglei.navigationbar.R;
import org.chenglei.utils.ColorUtil;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class NavigationBar extends RelativeLayout {
	
	private static final int DEFAULT_BUTTON_TEXT_COLOR = 0xFF007AFF;
	private static final int MAX_BUTTON_NUM_LEFT = 3;
	private static final int MAX_BUTTON_NUM_RIGHT = 3;
	private static final int BUTTON_TEXT_SIZE = 15;
	
	private int mMinHeight;
	private int mHorizontalPadding;
	
	private int mNavigationBarStyle = Style.NORMAL;
	
	private LinearLayout mLeftContainer;
	private LinearLayout mRightContainer;
	private TitleView mTitleView;
	private TabView mTabView;
	
	private int mMaxButtonNumLeft = MAX_BUTTON_NUM_LEFT;
	private int mMaxButtonNumRight = MAX_BUTTON_NUM_RIGHT;
	
	public static class Style {
		public static final int NORMAL = 0;
		public static final int TAB = 1;
		public static final int SEARCH = 2;
	}
	
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
		mMinHeight = getContext().getResources().getDimensionPixelSize(R.dimen.min_navigation_bar_height);
		mHorizontalPadding = getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar_horizontal_padding);
		setMinimumHeight(mMinHeight);
		initTitleView();
		initLeftContainer();
		initRightContainer();
	}
	
	private void initLeftContainer() {
		mLeftContainer = new LinearLayout(getContext());
		mLeftContainer.setGravity(Gravity.CENTER_VERTICAL);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, 
				RelativeLayout.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		addView(mLeftContainer, params);
	}
	
	private void initRightContainer() {
		mRightContainer = new LinearLayout(getContext());
		mRightContainer.setGravity(Gravity.CENTER_VERTICAL);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, 
				RelativeLayout.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(mRightContainer, params);
	}
	
	public void setNavigationBarStyle(int style) {
		if (mNavigationBarStyle != style) {
			mNavigationBarStyle = style;
			removeView(mTitleView);
			switch (style) {
			case Style.NORMAL:
				initTitleView();
				break;
			case Style.SEARCH:
				initSearchView();
				break;
			case Style.TAB:
				initTabView();
				break;
			}
		}
	}
	
	public Button addLeftButton(CharSequence text, Drawable drawable, View.OnClickListener l) {
		return addLeftButton(text, drawable, DEFAULT_BUTTON_TEXT_COLOR, l);
	}
	
	public Button addRightButton(CharSequence text, Drawable drawable, View.OnClickListener l) {
		return addRightButton(text, drawable, DEFAULT_BUTTON_TEXT_COLOR, l);
	}
	
	public void setMaxButtonNumLeft(int num) {
		mMaxButtonNumLeft = num;
	}
	
	public void setMaxButtonNumRight(int num) {
		mMaxButtonNumRight = num;
	}
	
	public Button addLeftButton(CharSequence text, Drawable drawable, int buttonTextColor, View.OnClickListener l) {
		int num = mLeftContainer.getChildCount();
		if (num >= mMaxButtonNumLeft) {
			throw new RuntimeException("The number of left navigation buttons can not be more than " + mMaxButtonNumLeft);
		}
		
		Button leftButton = createNavigationButton(text, drawable, buttonTextColor, l);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(mHorizontalPadding, 0, mHorizontalPadding, 0);
		mLeftContainer.addView(leftButton, params);
		return leftButton;
	}
	
	public Button addRightButton(CharSequence text, Drawable drawable, int buttonTextColor, View.OnClickListener l) {
		int num = mRightContainer.getChildCount();
		if (num >= mMaxButtonNumRight) {
			throw new RuntimeException("The number of right navigation buttons can not be more than " + mMaxButtonNumRight);
		}
		
		Button rightButton = createNavigationButton(text, drawable, buttonTextColor, l);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(mHorizontalPadding, 0, mHorizontalPadding, 0);
		mRightContainer.addView(rightButton, 0, params);
		return rightButton;
	}
	
	private Button createNavigationButton(CharSequence text, Drawable drawable, int buttonTextColor, View.OnClickListener l) {
		int buttonTextColorPressed = buttonTextColor - 0x88000000;
		ColorStateList colorStateList = ColorUtil.createColorStateList(buttonTextColor, buttonTextColorPressed);
		
		Button button = new Button(getContext());
		button.setBackgroundColor(Color.TRANSPARENT);
		button.setPadding(0, 0, 0, 0);
		button.setText(text);
		button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, BUTTON_TEXT_SIZE);
		button.setTextColor(colorStateList);
		button.setGravity(Gravity.CENTER);
		
		if (drawable != null) {
			Drawable stateListDrawable = new PressedEffectStateListDrawable(drawable, buttonTextColor, buttonTextColorPressed);
			stateListDrawable.setBounds(0, 0, mMinHeight / 2, mMinHeight / 2);
			button.setCompoundDrawables(stateListDrawable, null, null, null);
		}
		button.setOnClickListener(l);
		return button;
	}
	
	private void initTitleView() {
		mTitleView = new TitleView(getContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, 
				RelativeLayout.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(mTitleView, params);
		setTitleTextColor(DEFAULT_BUTTON_TEXT_COLOR);
	}
	
	public void setTitle(CharSequence text) {
		if (mTitleView != null) {
			mTitleView.setTitle(text);
		}
	}
	
	public void setSubTitle(CharSequence text) {
		if (mTitleView != null) {
			mTitleView.setSubTitle(text);
		}
	}
	
	public void setTitleTextColor(int color) {
		if (mTitleView != null) {
			mTitleView.setTextColor(color);
		}
	}
	
	private void initSearchView() {
		
	}
	
	private void initTabView() {
		mTabView = new TabView(getContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, 
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(mTabView, params);
	}
	
	public void setTabs(String[] titles, TabView.OnTabCheckedListener l) {
		setTabs(titles, Color.WHITE, DEFAULT_BUTTON_TEXT_COLOR, l);
	}
	
	public void setTabs(String[] titles, int uncheckedColor, int checkedColor, TabView.OnTabCheckedListener l) {
		mTabView.setTabs(titles, uncheckedColor, checkedColor, l);
	}
	
}
