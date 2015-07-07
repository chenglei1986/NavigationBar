package org.chenglei.ios8;

import org.chenglei.utils.ViewUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

public class TabView extends RadioGroup {
	
	private int mItemColorUnchecked;
	private int mItemColorChecked;
	
	private Paint mBorderPaint = new Paint();
	private Paint mDividerPaint = new Paint();
	
	private Rect mBorderRect = new Rect();
	
	private Path mBorderPath;
	
	private OnTabCheckedListener mOnTabCheckedListener;
	private OnTabItemCheckedChangeListener mOnTabItemCheckedChangeListener;
	
	public interface OnTabCheckedListener {
		public void onChecked(CompoundButton tab, int position);
	}

	public TabView(Context context) {
		this(context, null);
	}
	
	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		init();
	}
	
	private void init() {
		setOrientation(HORIZONTAL);
		initPaints();
	}
	
	private void initPaints() {
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setStrokeWidth(1);
		mBorderPaint.setStrokeJoin(Paint.Join.ROUND);
		
		mDividerPaint.setAntiAlias(true);
		mDividerPaint.setStyle(Paint.Style.FILL);
		mDividerPaint.setStrokeWidth(1);
	}
	
	public void setTabs(String[] titles, int uncheckedColor, int checkedColor, OnTabCheckedListener l) {
		
		int size = titles.length;
		if (size < 2) {
			throw new RuntimeException("There must be at least tow tabs");
		}
		
		mOnTabCheckedListener = l;
		
		mItemColorUnchecked = uncheckedColor;
		mItemColorChecked = checkedColor;
		
		mBorderPaint.setColor(mItemColorChecked - 0x22000000);
		mDividerPaint.setColor(mItemColorChecked - 0x22000000);
		
		removeAllViews();
		
		for (int i = 0; i < size; i++) {
			String title = titles[i];
			TabItem item = new TabItem(getContext());
			item.setId(ViewUtil.generateViewId());
			item.setText(title);
			item.setItemColors(mItemColorUnchecked, mItemColorChecked);
			if (0 == i) {
				item.setPosition(TabItem.POSITION_LEFT);
				item.setChecked(true);
			} else if (i == size - 1) {
				item.setPosition(TabItem.POSITION_RIGHT);
			} else {
				item.setPosition(TabItem.POSITION_MIDDLE);
			}
			mOnTabItemCheckedChangeListener = new OnTabItemCheckedChangeListener(i);
			item.setOnCheckedChangeListener(mOnTabItemCheckedChangeListener);
			addView(item);
		}
	}
	
	public void setOnTabCheckedListener(OnTabCheckedListener l) {
		mOnTabCheckedListener = l;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mBorderRect.set(1, 1, getWidth() - 1, getHeight() - 1);
		initPaths();
	}
	
	private void initPaths() {
		if (null == mBorderPath && getHeight() > 0) {
			mBorderPath = new Path();
			mBorderPath.moveTo(mBorderRect.left + mBorderRect.height() / 2, mBorderRect.top);
			mBorderPath.lineTo(mBorderRect.right - mBorderRect.height() / 2, mBorderRect.top);
			RectF ovalRight = new RectF(mBorderRect.right - mBorderRect.height(), mBorderRect.top, mBorderRect.right, mBorderRect.bottom);
			mBorderPath.arcTo(ovalRight, -90, 180);
			mBorderPath.lineTo(mBorderRect.left + mBorderRect.height() / 2, mBorderRect.bottom);
			RectF ovalLeft = new RectF(mBorderRect.left, mBorderRect.top, mBorderRect.left + mBorderRect.height(), mBorderRect.bottom);
			mBorderPath.arcTo(ovalLeft, 90, 180);
			mBorderPath.close();
		}
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		initPaths();
		canvas.drawPath(mBorderPath, mBorderPaint);
		drawDivider(canvas);
	}
	
	private void drawDivider(Canvas canvas) {
		int childCount = getChildCount();
		if (childCount < 2) {
			return;
		}
		int width = getWidth();
		int childWidth = width / childCount;
		for (int i = 0; i < childCount - 1; i++) {
			canvas.drawLine(childWidth * (i + 1), 0, childWidth * (i + 1), getHeight(), mDividerPaint);
		}
		
	}
	
	private class OnTabItemCheckedChangeListener implements android.widget.CompoundButton.OnCheckedChangeListener {
		
		private int mPosition;
		
		public OnTabItemCheckedChangeListener(int position) {
			mPosition = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked && mOnTabCheckedListener != null) {
				mOnTabCheckedListener.onChecked(buttonView, mPosition);
			}
		}
		
	}

}
