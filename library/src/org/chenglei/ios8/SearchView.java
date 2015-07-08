package org.chenglei.ios8;

import org.chenglei.drawable.PressedEffectStateListDrawable;
import org.chenglei.navigationbar.R;
import org.chenglei.utils.ColorUtil;
import org.chenglei.utils.DrawableUtil;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchView extends LinearLayout {
	
	private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#C9C9CE");
	
	private EditText mInputView;
	private TextView mTextView;
	private ImageView mDrawableLeft;
	private ImageView mDrawableRight;
	private Button mButton;
	private ViewGroup mInputLayout;
	private ViewGroup mRootView;
	
	private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;

	public SearchView(Context context) {
		this(context, null);
	}
	
	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRootView = (ViewGroup) inflate(context, R.layout.search_view_layout, this);
		
		mInputView = (EditText) findViewById(R.id.input_view);
		mTextView = (TextView) findViewById(R.id.text_view);
		mDrawableLeft = (ImageView) findViewById(R.id.drawable_left);
		mDrawableRight = (ImageView) findViewById(R.id.drawable_right);
		mButton = (Button) findViewById(R.id.button);
		mInputLayout = (ViewGroup) findViewById(R.id.input_layout);
		
		this.setBackgroundColor(mBackgroundColor);
		mInputView.setOnFocusChangeListener(mOnEditTextFocusChangeListener);
		
		setLayoutTransition(mInputLayout);
		
		mInputLayout.setOnClickListener(mOnInputLayoutClickListener);
		
		mInputView.addTextChangedListener(mTextWatcher);
		
		mButton.setOnClickListener(mOnButtonClickListener);
	}
	
	public void setButtonText(CharSequence text) {
		mButton.setText(text);
	}
	
	public void setButtonTextColor(int buttonTextColor) {
		int buttonTextColorPressed = buttonTextColor - 0x88000000;
		ColorStateList colorStateList = ColorUtil.createColorStateList(buttonTextColor, buttonTextColorPressed);
		mButton.setTextColor(colorStateList);
	}
	
	public void setBackgroundColor(int color) {
		super.setBackgroundColor(color);
		mBackgroundColor = color;
	}
	
	public void setHintTextColor(int color) {
		mInputView.setHintTextColor(color);
		mTextView.setTextColor(color);
		Drawable drawableLeft = DrawableUtil.getColoredDrawable(getContext(), R.drawable.ic_search, color);
		if (drawableLeft != null) {
			mDrawableLeft.setImageDrawable(drawableLeft);
		}
		
		Drawable drawableRight = DrawableUtil.getDrawable(getContext(), R.drawable.ic_clear);
		if (drawableRight != null) {
			Drawable stateListDrawable = new PressedEffectStateListDrawable(drawableRight, mBackgroundColor, mButton.getCurrentTextColor());
			mDrawableRight.setImageDrawable(stateListDrawable);
		}
	}
	
	private android.view.View.OnFocusChangeListener mOnEditTextFocusChangeListener = new android.view.View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			mButton.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
			mTextView.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
			mInputView.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
			mDrawableRight.setVisibility((hasFocus && mInputView.getText().toString().length() > 0) ? View.VISIBLE : View.GONE);
			if (mOnFocusChangeListener != null) {
				mOnFocusChangeListener.onFocusChange(v, hasFocus);
			}
		}
	};
	
	/**
     * Interface definition for a callback to be invoked when the focus state of
     * a view changed.
     */
    public interface OnFocusChangeListener {
        /**
         * Called when the focus state of a view has changed.
         *
         * @param v The view whose state has changed.
         * @param hasFocus The new focus state of v.
         */
        void onFocusChange(View v, boolean hasFocus);
    }
    
    private OnFocusChangeListener mOnFocusChangeListener;
    
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
    	mOnFocusChangeListener = l;
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setLayoutTransition(ViewGroup view) {
    	final LayoutTransition transitioner = new LayoutTransition();
		transitioner.setDuration(100);
		view.setLayoutTransition(transitioner);
	}
    
    private View.OnClickListener mOnButtonClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mInputView.setText("");
			mInputView.clearFocus();
		}
	};
	
	private View.OnClickListener mOnInputLayoutClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!mInputView.hasFocus()) {
				mButton.setVisibility(View.VISIBLE);
				mInputView.setVisibility(View.VISIBLE);
				mTextView.setVisibility(View.GONE);
				if (mOnFocusChangeListener != null) {
					mOnFocusChangeListener.onFocusChange(v, true);
				}
				mInputView.requestFocus();
			}
		}
	};
	
	private TextWatcher mTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			mDrawableRight.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
		}
	};

}
