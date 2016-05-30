package com.github.chenglei1986.navigationbar.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chenglei1986.navigationbar.NavigationBar;
import com.github.chenglei1986.navigationbar.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomTab extends FragmentActivity {

    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();

    private static final int BG_COLOR = Color.parseColor("#25B6ED");
    private static final String[] TAB_NANES = {"music", "video", "games"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_tab);
        ButterKnife.bind(this);

        mNavigationBar.setDisplayBackButton(true)
                .setBackButtonImageResource(R.drawable.ic_chevron_left_white_24dp)
                .setBackButtonText("Back")
                .setOnBackButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                })
                .addItem(0, "Settings", ActivityCompat.getDrawable(this, R.drawable.ic_settings_white_24dp))
                .addItem(1, "More", ActivityCompat.getDrawable(this, R.drawable.ic_more_vert_white_24dp))
                .setOnMenuItemClickListener(new NavigationBar.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int id) {
                        Toast.makeText(getApplicationContext(), "" + id, Toast.LENGTH_SHORT).show();
                    }
                })
                .setTabs(new String[] {"music", "video", "games"}).setOnTabCheckedListener(new NavigationBar.OnTabCheckedListener() {
                    @Override
                    public void onTabChecked(int position) {
                        Toast.makeText(getApplicationContext(), "Tab " + position + " is checked", Toast.LENGTH_SHORT).show();
                    }
                })
                .bindViewPager(mViewPager);

        mFragmentList.add(TabFragment.newInstance(TAB_NANES[0]));
        mFragmentList.add(TabFragment.newInstance(TAB_NANES[1]));
        mFragmentList.add(TabFragment.newInstance(TAB_NANES[2]));

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mSearchView.setImeOption(EditorInfo.IME_ACTION_SEARCH);
        mSearchView.setOnEditorActionListener(mOnEditorActionListener);

        mSearchView.setOnFocusChangeListener(mOnFocusChangeListener);
        mSearchView.setButtonTextColor(BG_COLOR);
        mSearchView.setHint("Search");
        mSearchView.setHintTextColor(0xFF8E8E93);
        mSearchView.setButtonText("Cancel");
        mSearchView.setStyle(SearchView.Style.ROUND);

    }

    private View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && mNavigationBar.isShown()) {
                mNavigationBar.hide();
            } else if (!hasFocus && !mNavigationBar.isShown()) {
                mNavigationBar.show();
            }
        }
    };

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                Toast.makeText(CustomTab.this, "Search", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    };

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position % getCount());
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
