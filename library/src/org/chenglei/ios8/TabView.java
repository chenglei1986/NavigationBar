package org.chenglei.ios8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

public class TabView extends RadioGroup {
	
	private int mItemColorUnchecked;
	private int mItemColorChecked;
	
	private Paint mBorderPaint = new Paint();
	
	private Path mBorderPath;
	
	private OnTabCheckedListener mOnTabCheckedListener;
	
	public interface OnTabCheckedListener {
		public void onChecked(TabItem tabItem, int position);
	}

	public TabView(Context context) {
		this(context, null);
	}
	
	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		setOrientation(HORIZONTAL);
		setPadding(1, 1, 1, 1);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setStrokeWidth(5);
	}
	
	public void setTabs(String[] titles, int uncheckedColor, int checkedColor) {
		
		int size = titles.length;
		if (size < 2) {
			throw new RuntimeException("There must be at least tow tabs");
		}
		
		mItemColorUnchecked = uncheckedColor;
		mItemColorChecked = checkedColor;
		
		mBorderPaint.setColor(mItemColorUnchecked);
		
		removeAllViews();
		
		for (int i = 0; i < size; i++) {
			String title = titles[i];
			TabItem item = new TabItem(getContext());
			item.setText(title);
			item.setItemColors(mItemColorUnchecked, mItemColorChecked);
			if (0 == i) {
				item.setPosition(TabItem.POSITION_LEFT);
			} else if (i == size - 1) {
				item.setPosition(TabItem.POSITION_RIGHT);
			} else {
				item.setPosition(TabItem.POSITION_MIDDLE);
			}
			addView(item);
			item.setOnClickListener(new OnTabClickListener(i));
		}
	}
	
	public void setOnTabCheckedListener(OnTabCheckedListener l) {
		mOnTabCheckedListener = l;
	}
	
	private class OnTabClickListener implements OnClickListener {
		
		private int mPosition;
		
		public OnTabClickListener(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View v) {
			TabItem tabItem = (TabItem) v;
			if (mOnTabCheckedListener != null) {
				mOnTabCheckedListener.onChecked(tabItem, mPosition);
			}
			
			tabItem.setChecked(true);
			int size = getChildCount();
			for (int i = 0; i < size; i++) {
				if (i != mPosition) {
					((TabItem)getChildAt(i)).setChecked(false);
				}
			}
			invalidate();
		}
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//initPaths();
	}
	
	private void initPaths() {
		if (null == mBorderPath && getHeight() > 0) {
			mBorderPath = new Path();
			mBorderPath.moveTo(getHeight() / 2, 0);
			mBorderPath.lineTo(getWidth() - getHeight() / 2, 0);
			RectF ovalRight = new RectF(getWidth() - getHeight(), 0, getWidth(), getHeight());
			mBorderPath.arcTo(ovalRight, -90, 180);
			mBorderPath.lineTo(getHeight() / 2, getHeight());
			RectF ovalLeft = new RectF(0, 0, getHeight(), getHeight());
			mBorderPath.arcTo(ovalLeft, 90, 180);
			mBorderPath.close();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initPaths();
		canvas.drawPath(mBorderPath, mBorderPaint);
	}

}
