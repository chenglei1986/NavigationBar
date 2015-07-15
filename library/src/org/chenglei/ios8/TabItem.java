package org.chenglei.ios8;

import org.chenglei.navigationbar.R;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.RadioButton;

class TabItem extends RadioButton {
	
	public static final int STATE_UNCHECKED = 0;	
	public static final int STATE_CHECKED = 1;
	
	public static final int POSITION_LEFT = 0;
	public static final int POSITION_MIDDLE = 1;
	public static final int POSITION_RIGHT = 2;
	
	private static final int DEFAULT_TEXT_SIZE = 14;
	
	private int mItemColorUnchecked;
	private int mItemColorChecked;
	
	private int mPosition = POSITION_MIDDLE;
	
	private int mHorizontalPadding;
	private int mVerticalPadding;
	
	private Paint mBackgroundPaint;
	
	private Path mLeftItemPath;
	private Path mRightItemPath;
	
	private ValueAnimator mCheckAnim;
	private ValueAnimator mUncheckAnim;
	
	private boolean mPlayAnimation = false;

	public TabItem(Context context) {
		this(context, null);
	}
	
	public TabItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init() {
		setGravity(Gravity.CENTER);
		setClickable(true);
		setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE);
		
		mHorizontalPadding = getContext().getResources().getDimensionPixelSize(R.dimen.tab_item_horizontal_padding);
		mVerticalPadding = getContext().getResources().getDimensionPixelSize(R.dimen.tab_item_vertical_padding);
		setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding, mVerticalPadding);
		
		if (null == mBackgroundPaint) {
			mBackgroundPaint = new Paint();
		}
		mBackgroundPaint.setAntiAlias(true);
	}
	
	public void setItemColors(int uncheckedColor, int checkedColor) {
		mItemColorUnchecked = uncheckedColor;
		mItemColorChecked = checkedColor;
		mBackgroundPaint.setColor(isChecked() ? checkedColor - 0x22000000 : uncheckedColor);
		setTextColor(isChecked() ? uncheckedColor : checkedColor);
		
		mCheckAnim = createColorAnim(mItemColorUnchecked, mItemColorChecked - 0x22000000, 100);
		mUncheckAnim = createColorAnim(mItemColorChecked - 0x22000000, mItemColorUnchecked, 100);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		switch (mPosition) {
		case POSITION_LEFT:
			canvas.drawPath(mLeftItemPath, mBackgroundPaint);
			break;
		case POSITION_MIDDLE:
			canvas.drawColor(mBackgroundPaint.getColor());
			break;
		case POSITION_RIGHT:
			canvas.drawPath(mRightItemPath, mBackgroundPaint);
			break;
		default:
			break;
		}
		super.onDraw(canvas);
	}
	
	public void setPosition(int position) {
		if (position > POSITION_RIGHT) {
			throw new RuntimeException("Unknown position " + position);
		}
		mPosition = position;
		invalidate();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		initLeftItemPath();
		initRightItemPath();
	}
	
	private void initLeftItemPath() {
		mLeftItemPath = new Path();
		mLeftItemPath.moveTo(getHeight() / 2, 0);
		mLeftItemPath.lineTo(getWidth(), 0);
		mLeftItemPath.lineTo(getWidth(), getHeight());
		mLeftItemPath.lineTo(getHeight() / 2, getHeight());
		RectF oval = new RectF(0, 0, getHeight(), getHeight());
		mLeftItemPath.arcTo(oval, 90, 180);
		mLeftItemPath.close();
	}
	
	private void initRightItemPath() {
		mRightItemPath = new Path();
		mRightItemPath.moveTo(0, 0);
		mRightItemPath.lineTo(getWidth() - getHeight() / 2, 0);
		RectF oval = new RectF(getWidth() - getHeight(), 0, getWidth(), getHeight());
		mRightItemPath.arcTo(oval, -90, 180);
		mRightItemPath.lineTo(0, getHeight());
		mRightItemPath.lineTo(0, 0);
		mRightItemPath.close();
	}

	@Override
	public void setChecked(boolean checked) {
		if (null == mBackgroundPaint) {
			mBackgroundPaint = new Paint();
		}
		if (checked) {
			if (mCheckAnim != null && mPlayAnimation) {
				mCheckAnim.start();
			} else {
				mBackgroundPaint.setColor(mItemColorChecked - 0x22000000);
				setTextColor(mItemColorUnchecked);
			}
		} else {
			if (mUncheckAnim != null && mPlayAnimation) {
				mUncheckAnim.start();
			} else {
				mBackgroundPaint.setColor(mItemColorUnchecked);
				setTextColor(mItemColorChecked);
			}
		}
		super.setChecked(checked);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isChecked()) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mBackgroundPaint.setColor(mItemColorChecked - 0xDD000000);
				invalidate();
				break;
				
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				mBackgroundPaint.setColor(mItemColorUnchecked);
				invalidate();
				mPlayAnimation = true;
				break;
			}
		}
		return super.onTouchEvent(event);
	}
	
	private ValueAnimator createColorAnim(final int argbForm, final int argbTo, long duration) {
		ValueAnimator anim = ValueAnimator.ofObject(new ArgbEvaluator(), argbForm, argbTo);
		anim.setDuration(duration);
		anim.setStartDelay(0);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int argb = (int) animation.getAnimatedValue();
				mBackgroundPaint.setColor(argb);
				invalidate();
			}
		});
		anim.addListener(new ValueAnimator.AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {}
			
			@Override
			public void onAnimationRepeat(Animator animation) {}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				if (isChecked()) {
					setTextColor(mItemColorUnchecked);
				} else {
					setTextColor(mItemColorChecked);
				}
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {}
		});
		return anim;
	}
}
