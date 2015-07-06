package org.chenglei.ios8;

import org.chenglei.navigationbar.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;

class TabItem extends CompoundButton implements android.widget.CompoundButton.OnCheckedChangeListener {
	
	public static final int STATE_UNCHECKED = 0;	
	public static final int STATE_CHECKED = 1;
	
	public static final int POSITION_LEFT = 0;
	public static final int POSITION_MIDDLE = 1;
	public static final int POSITION_RIGHT = 2;
	
	private static final int DEFAULT_TEXT_SIZE = 16;
	
	private int mItemColorUnchecked;
	private int mItemColorChecked;
	
	private int mState = STATE_UNCHECKED;
	
	private int mPosition = POSITION_MIDDLE;
	
	private int mHorizontalPadding;
	private int mVerticalPadding;
	
	private Paint mBackgroundPaint = new Paint();
	
	private Path mLeftItemPath;
	private Path mRightItemPath;

	public TabItem(Context context) {
		this(context, null, 0);
	}
	
	public TabItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init() {
		setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE);
		
		mHorizontalPadding = getContext().getResources().getDimensionPixelSize(R.dimen.tab_item_horizontal_padding);
		mVerticalPadding = getContext().getResources().getDimensionPixelSize(R.dimen.tab_item_vertical_padding);
		setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding, mVerticalPadding);
		
		mBackgroundPaint.setAntiAlias(true);
		setOnCheckedChangeListener(this);
	}
	
	public void setItemColors(int uncheckedColor, int checkedColor) {
		mItemColorUnchecked = uncheckedColor;
		mItemColorChecked = checkedColor;
		mBackgroundPaint.setColor(isChecked() ? checkedColor : uncheckedColor);
		setTextColor(isChecked() ? uncheckedColor : checkedColor);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		switch (mPosition) {
		case POSITION_LEFT:
			canvas.drawPath(mLeftItemPath, mBackgroundPaint);
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
		if (POSITION_MIDDLE == position) {
			setBackgroundColor(isChecked() ? mItemColorChecked : mItemColorUnchecked);
		}
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			mBackgroundPaint.setColor(mItemColorChecked);
			setTextColor(mItemColorUnchecked);
		} else {
			mBackgroundPaint.setColor(mItemColorUnchecked);
			setTextColor(mItemColorChecked);
		}
		if (POSITION_MIDDLE == mPosition) {
			setBackgroundColor(mBackgroundPaint.getColor());
		} else {
			invalidate();
		}
	}
	
	@Override
    public void toggle() {
        // we override to prevent toggle when the radio is already
        // checked (as opposed to check boxes widgets)
        if (!isChecked()) {
            super.toggle();
        }
    }

}
