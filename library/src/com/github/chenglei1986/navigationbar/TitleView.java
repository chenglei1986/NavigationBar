package com.github.chenglei1986.navigationbar;

import android.content.Context;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

class TitleView extends LinearLayout {

    private static final int TITLE_TEXT_SIZE = 16;
    private static final int SUBTITLE_TEXT_SIZE = 10;

    private TextView mTitle;
    private TextView mSubtitle;

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
        addView(mSubtitle);
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
        mSubtitle = new TextView(getContext());
        mSubtitle.setSingleLine(true);
        mSubtitle.setEms(12);
        mSubtitle.setEllipsize(TruncateAt.END);
        mSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, SUBTITLE_TEXT_SIZE);
        mSubtitle.setGravity(Gravity.CENTER);
        mSubtitle.setVisibility(View.GONE);
    }

    public void setTitle(CharSequence text) {
        mTitle.setText(text);
        mTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    public void setSubTitle(CharSequence text) {
        mSubtitle.setText(text);
        mSubtitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setSubtitleColor(int color) {
        mSubtitle.setTextColor(color);
    }

    public void setTitleSize(float size) {
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setSubtitleSize(float size) {
        mSubtitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

}
