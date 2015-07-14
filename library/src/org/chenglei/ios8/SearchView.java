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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchView extends LinearLayout {
	
	private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#C9C9CE");
	
	private EditText mInputView;
	private TextView mTextView;
	private ImageView mDrawableLeft;
	private ImageView mDrawableRight;
	private TextView mButton;
	private ViewGroup mInputLayout;
	
	private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
	
    private OnFocusChangeListener mOnFocusChangeListener;
	
	public static class Style {
		
		public static final int RECT = 0;
		
		public static final int ROUND = 1;
		
	}

	public SearchView(Context context) {
		this(context, null);
	}
	
	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.search_view_layout, this);
		
		mInputView = (EditText) findViewById(R.id.input_view);
		mTextView = (TextView) findViewById(R.id.text_view);
		mDrawableLeft = (ImageView) findViewById(R.id.drawable_left);
		mDrawableRight = (ImageView) findViewById(R.id.drawable_right);
		mButton = (TextView) findViewById(R.id.button);
		mInputLayout = (ViewGroup) findViewById(R.id.input_layout);
		
		this.setBackgroundColor(mBackgroundColor);
		mTextView.setSingleLine(true);
		mInputView.setSingleLine(true);
		mInputView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
		mButton.setPadding(0, 0, 0, 0);
		
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
	
	public void setText(CharSequence text) {
		mInputView.setText(text);
	}
	
	public void setHint(CharSequence text) {
		mInputView.setHint(text);
		mTextView.setText(text);
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
    
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
    	mOnFocusChangeListener = l;
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setLayoutTransition(ViewGroup view) {
    	final LayoutTransition transitioner = new LayoutTransition();
		transitioner.setDuration(100);
		transitioner.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);
		transitioner.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);
		transitioner.setStartDelay(LayoutTransition.APPEARING, 0);
		transitioner.setStartDelay(LayoutTransition.DISAPPEARING, 0);
		view.setLayoutTransition(transitioner);
	}
    
    private View.OnClickListener mOnButtonClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			exitSearchMode();
		}
	};
	
	private View.OnClickListener mOnInputLayoutClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			enterSearchMode();
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
	
	public void setImeOption(int imeOptions) {
		mInputView.setImeOptions(imeOptions);
	}
	
	public void setOnEditorActionListener(OnEditorActionListener l) {
		mInputView.setOnEditorActionListener(l);
	}
	
	public void setOnButtonClickListener(View.OnClickListener l) {
		mButton.setOnClickListener(l);
	}
	
	public void enterSearchMode() {
		mInputView.setText("");
		mTextView.setVisibility(View.GONE);
		mInputView.setVisibility(View.VISIBLE);
		mInputView.requestFocus();
		mButton.setVisibility(View.VISIBLE);
		postDelayed(new Runnable() {

			@Override
			public void run() {
				if (mOnFocusChangeListener != null) {
					mOnFocusChangeListener.onFocusChange(mInputView, true);
				}
				showKeyboard(mInputView);
			}
		}, 50);
	}
	
	public void exitSearchMode() {
		mInputView.setText("");
		mInputView.setVisibility(View.GONE);
		mTextView.setVisibility(View.VISIBLE);
		mButton.setVisibility(View.GONE);
		mInputView.clearFocus();
		postDelayed(new Runnable() {

			@Override
			public void run() {
				if (mOnFocusChangeListener != null) {
					mOnFocusChangeListener.onFocusChange(mInputView, false);
				}
				hideKeyboard(mInputView);
			}
		}, 50);
	}
	
	public void setStyle(int style) {
		switch (style) {
		case Style.RECT:
			mInputLayout.setBackgroundResource(R.drawable.search_view_background_rect);
			break;
		case Style.ROUND:
			mInputLayout.setBackgroundResource(R.drawable.search_view_background_round);
			break;
		default:
			throw new RuntimeException("Unknown style " + style);
		}
	}
	
	public void hideKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null && imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}
	
	public void showKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.showSoftInput(v, InputMethodManager.RESULT_UNCHANGED_SHOWN);
		}
	}

}
