package com.github.chenglei1986.navigationbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

class NavigationBarButton extends FrameLayout {

    private TextView mTextView;
    private ImageView mImageView;

    public NavigationBarButton(Context context) {
        this(context, null);
    }

    public NavigationBarButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBarButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int padding = getResources().getDimensionPixelSize(R.dimen.menu_item_padding);
        setPadding(padding, 0, padding, 0);
        mTextView = new TextView(getContext());
        mImageView = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(mTextView, layoutParams);
        addView(mImageView, layoutParams);
    }

    public void setImageResource(int src) {
        mImageView.setImageResource(src);
        if (src > 0) {
            mTextView.setText("");
        }
    }

    public void setImageDrawable(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
        if (drawable != null) {
            mTextView.setText("");
        }
    }

    public void setText(int src) {
        mTextView.setText(src);
        mImageView.setImageDrawable(null);
    }

    public void setText(String text) {
        mTextView.setText(text);
        mImageView.setImageDrawable(null);
    }

    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }

    public void setTextSize(float size) {
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

}
