package org.chenglei.ios8;

import android.content.Context;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleView extends LinearLayout {
	
	private static final int TITLE_TEXT_SIZE = 16;
	private static final int SUBTITLE_TEXT_SIZE = 10;
	
	private TextView mTitle;
	private TextView mSubTitle;

	public TitleView(Context context) {
		this(context, null);
	}
	
	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER);
		initTitle();
		initSubTitle();
		addView(mTitle);
		addView(mSubTitle);
	}
	
	private void initTitle() {
		mTitle = new TextView(getContext());
		mTitle.setSingleLine(true);
		mTitle.setEms(6);
		mTitle.setEllipsize(TruncateAt.END);
		mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TITLE_TEXT_SIZE);
		mTitle.setGravity(Gravity.CENTER);
		mTitle.setVisibility(View.GONE);
	}
	
	private void initSubTitle() {
		mSubTitle = new TextView(getContext());
		mSubTitle.setSingleLine(true);
		mSubTitle.setEms(12);
		mSubTitle.setEllipsize(TruncateAt.END);
		mSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, SUBTITLE_TEXT_SIZE);
		mSubTitle.setGravity(Gravity.CENTER);
		mSubTitle.setVisibility(View.GONE);
	}
	
	public void setTitle(CharSequence text) {
		mTitle.setText(text);
		mTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
	}
	
	public void setSubTitle(CharSequence text) {
		mSubTitle.setText(text);
		mSubTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
	}
	
	public void setTextColor(int color) {
		mTitle.setTextColor(color);
		mSubTitle.setTextColor(color);
	}

}
