package com.github.chenglei1986.navigationbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * NavigationBar is a tool bar at the top of a app which copy the style of UINavigationBar of IOS 8.
 * 
 * @author chenglei
 *
 */
public class NavigationBar extends RelativeLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final int DEFAULT_BUTTON_TEXT_COLOR = 0xFF007AFF;
    private static final int MAX_BUTTON_NUM_LEFT = 3;
    private static final int MAX_BUTTON_NUM_RIGHT = 3;

    private Type mNavigationBarType = Type.NORMAL;
    private int mButtonTextColor;
    private float mButtonTextSize;
    private String mTitleText;
    private String mSubtitleText;
    private int mTitleTextColor;
    private int mSubtitleTextColor;
    private float mTitleTextSize;
    private float mSubtitleTextSize;

    private Drawable mTabFirstBackground;
    private Drawable mTabMiddleBackground;
    private Drawable mTabLastBackground;
    private ColorStateList mTabTextColor;

    private LinearLayout mLeftContainer;
    private LinearLayout mRightContainer;
    private LinearLayout mCenterContainer;
    private TitleView mTitleView;
    private RadioGroup mTabView;
    private TextView mBackButton;

    private final SparseArray<NavigationBarButton> mMenus = new SparseArray<>();
    private OnMenuItemClickListener mOnMenuItemClickListener;

    private ViewPager mViewPager;
    private boolean mPagerChangeAnim;

    /**
     * The type specifies what the NavigationBar looks like.
     *
     * @author chenglei
     *
     */
    public enum Type {
        /**
         * A title (and a subtitle) with this type will be displayed.
         */
        NORMAL,

        /**
         * A TabView contains several TabItems with this type will be displayed.
         */
        TAB
    }

    public NavigationBar(Context context) {
        this(context, null);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.navigationBar);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar, defStyleAttr, 0);
        int ordinal = a.getInt(R.styleable.NavigationBar_type, Type.NORMAL.ordinal());
        mNavigationBarType = Type.values()[ordinal];
        mButtonTextColor = a.getColor(R.styleable.NavigationBar_buttonTextColor, ContextCompat.getColor(context, R.color.nv_black));
        mButtonTextSize = a.getDimension(R.styleable.NavigationBar_buttonTextSize, context.getResources().getDimension(R.dimen.buttonTextSize));
        mTitleText = a.getString(R.styleable.NavigationBar_nv_titleText);
        mSubtitleText = a.getString(R.styleable.NavigationBar_nv_subtitleText);
        mTitleTextSize = a.getDimension(R.styleable.NavigationBar_nv_titleSize, context.getResources().getDimension(R.dimen.titleSize));
        mSubtitleTextSize = a.getDimension(R.styleable.NavigationBar_nv_subtitleSize, context.getResources().getDimension(R.dimen.subtitleSize));
        mTitleTextColor = a.getColor(R.styleable.NavigationBar_nv_titleColor, ContextCompat.getColor(context, R.color.titleColor));
        mSubtitleTextColor = a.getColor(R.styleable.NavigationBar_nv_subtitleColor, ContextCompat.getColor(context, R.color.titleColor));

        mTabFirstBackground = a.getDrawable(R.styleable.NavigationBar_nv_tabFirstBackground);
        mTabMiddleBackground = a.getDrawable(R.styleable.NavigationBar_nv_tabMiddleBackground);
        mTabLastBackground = a.getDrawable(R.styleable.NavigationBar_nv_tabLastBackground);
        mTabTextColor = a.getColorStateList(R.styleable.NavigationBar_nv_tabTextColor);
        a.recycle();

        init();
    }

    private void init() {
        if (null == getBackground()) {
            setBackgroundColor(Color.WHITE);
        }
        setGravity(Gravity.CENTER_VERTICAL);
        initCenterContainer();
        initLeftContainer();
        initRightContainer();
    }

    private void initCenterContainer() {
        mCenterContainer = new LinearLayout(getContext());
        mCenterContainer.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mCenterContainer, params);

        switch (mNavigationBarType) {
            case NORMAL:
                initTitleView();
                break;

            case TAB:
                initTabView();
                break;
        }
    }

    private void initLeftContainer() {
        mLeftContainer = new LinearLayout(getContext());
        mLeftContainer.setGravity(Gravity.CENTER_VERTICAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        addView(mLeftContainer, params);

        mBackButton = new TextView(getContext());
        mBackButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mButtonTextSize);
        mBackButton.setTextColor(mButtonTextColor);
        mBackButton.setGravity(Gravity.CENTER);
        int padding = getResources().getDimensionPixelSize(R.dimen.menu_item_padding);
        mBackButton.setPadding(padding, 0, padding, 0);
        mLeftContainer.addView(mBackButton, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mBackButton.setVisibility(GONE);
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

    public NavigationBar setDisplayBackButton(boolean show) {
        mBackButton.setVisibility(show ? VISIBLE : GONE);
        return this;
    }

    public NavigationBar setBackButtonText(int resId) {
        mBackButton.setText(resId);
        return this;
    }

    public NavigationBar setBackButtonText(CharSequence text) {
        mBackButton.setText(text);
        return this;
    }

    public NavigationBar setBackButtonDrawable(Drawable drawable) {
        mBackButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        return this;
    }

    public NavigationBar setBackButtonImageResource(int resId) {
        mBackButton.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        return this;
    }

    public NavigationBar setOnBackButtonClickListener(OnClickListener l) {
        mBackButton.setOnClickListener(l);
        return this;
    }

    private void initTitleView() {
        mTitleView = new TitleView(getContext());
        mCenterContainer.removeAllViews();
        mCenterContainer.addView(mTitleView);
        if (mTitleView != null) {
            mTitleView.setTitleColor(mTitleTextColor);
            mTitleView.setSubtitleColor(mSubtitleTextColor);
            mTitleView.setTitleSize(mTitleTextSize);
            mTitleView.setSubtitleSize(mSubtitleTextSize);
            mTitleView.setTitle(mTitleText);
            mTitleView.setSubTitle(mSubtitleText);
        }
    }

    public NavigationBar setTitle(CharSequence text) {
        if (mTitleView != null) {
            mTitleView.setTitle(text);
        }
        return this;
    }

    public NavigationBar setSubTitle(CharSequence text) {
        if (mTitleView != null) {
            mTitleView.setSubTitle(text);
        }
        return this;
    }

    private void initTabView() {
        mTabView = new RadioGroup(getContext());
        mTabView.setOrientation(LinearLayout.HORIZONTAL);
        mCenterContainer.removeAllViews();
        int paddingVertical = getResources().getDimensionPixelSize(R.dimen.tab_view_vertical_padding);
        mTabView.setPadding(0, paddingVertical, 0, paddingVertical);
        mTabView.setOnCheckedChangeListener(this);
        mCenterContainer.addView(mTabView);
    }

    public NavigationBar setTabs(String[] titles) {
        if (null == mTabView) {
            throw new IllegalStateException("It is not a Tab style NavigationBar!");
        }
        mTabView.removeAllViews();
        for (int i = 0; i < titles.length; i++) {
            RadioButton tab = new RadioButton(getContext());
            tab.setTag(i);
            tab.setText(titles[i]);
            tab.setTextColor(mTabTextColor != null ? mTabTextColor : ContextCompat.getColorStateList(getContext(), R.color.tab_text_color));
            tab.setButtonDrawable(null);
            tab.setGravity(Gravity.CENTER);
            tab.setPadding(0, 0, 0, 0);
            if (0 == i) {
                tab.setBackgroundDrawable(mTabFirstBackground);
            } else if (titles.length - 1 == i) {
                tab.setBackgroundDrawable(mTabLastBackground);
            } else {
                tab.setBackgroundDrawable(mTabMiddleBackground);
            }
            mTabView.addView(tab);
        }
        if (mTabView.getChildCount() > 1) {
            ((RadioButton)mTabView.getChildAt(0)).setChecked(true);
        }
        return this;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mOnTabCheckedListener != null) {
            int position = (Integer) group.findViewById(checkedId).getTag();
            mOnTabCheckedListener.onTabChecked(position);
            if (mViewPager != null) {
                mViewPager.setCurrentItem(position, mPagerChangeAnim);
            }
        }
    }

    private OnTabCheckedListener mOnTabCheckedListener;

    public NavigationBar setOnTabCheckedListener(OnTabCheckedListener l) {
        if (null == mTabView) {
            throw new IllegalStateException("It is not a Tab style NavigationBar!");
        }
        mOnTabCheckedListener = l;
        return this;
    }

    public interface OnTabCheckedListener {
        void onTabChecked(int position);
    }

    public NavigationBar hide() {
        setVisibility(GONE);
        return this;
    }

    public NavigationBar show() {
        setVisibility(VISIBLE);
        return this;
    }

    public boolean isShown() {
        return getVisibility() == VISIBLE;
    }

    public NavigationBar addItem(int id, String title, Drawable drawable) {
        NavigationBarButton button = new NavigationBarButton(getContext());
        button.setText(title);
        button.setImageDrawable(drawable);
        button.setTag(id);
        button.setTextSize(mButtonTextSize);
        button.setTextColor(mButtonTextColor);
        button.setOnClickListener(this);
        mMenus.append(id, button);
        mRightContainer.addView(button, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return this;
    }

    public NavigationBar addItem(int id, int titleResId, int drawableId) {
        NavigationBarButton button = new NavigationBarButton(getContext());
        button.setText(titleResId);
        button.setImageResource(drawableId);
        button.setTag(id);
        button.setTextSize(mButtonTextSize);
        button.setTextColor(mButtonTextColor);
        button.setOnClickListener(this);
        mMenus.append(id, button);
        mRightContainer.addView(button, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return this;
    }

    public NavigationBar setOnMenuItemClickListener(OnMenuItemClickListener l) {
        mOnMenuItemClickListener = l;
        return this;
    }

    public void removeMenu(int id) {
        NavigationBarButton button = mMenus.get(id);
        mRightContainer.removeView(button);
        mMenus.remove(id);
    }

    public void removeAllMenu() {
        mRightContainer.removeAllViews();
        mMenus.clear();
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int id);
    }

    @Override
    public void onClick(View v) {
        if (mOnMenuItemClickListener != null) {
            mOnMenuItemClickListener.onMenuItemClick((Integer) v.getTag());
        }
    }

    public NavigationBar bindViewPager(ViewPager viewPager) {
        bindViewPager(viewPager, false);
        return this;
    }

    public NavigationBar bindViewPager(ViewPager viewPager, boolean anim) {
        mPagerChangeAnim = anim;
        if (null == mTabView) {
            throw new IllegalStateException("It is not a Tab style NavigationBar!");
        }
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabView.check(mTabView.getChildAt(position % mTabView.getChildCount()).getId());
            }
        });
        return this;
    }

}
