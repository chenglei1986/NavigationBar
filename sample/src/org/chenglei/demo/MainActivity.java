package org.chenglei.demo;

import org.chenglei.ios8.NavigationBar;
import org.chenglei.ios8.SearchView;
import org.chenglei.utils.DrawableUtil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private NavigationBar mNavigationBar;
	
	private static final int TEXT_COLOR = Color.parseColor("#FFFFFF");
	
	private static final int BG_COLOR = Color.parseColor("#25B6ED");
	
	private SearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mNavigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mNavigationBar.setBackgroundColor(BG_COLOR);
		
		mNavigationBar.addLeftButton("返回", DrawableUtil.getDrawable(this, R.drawable.ic_back), TEXT_COLOR, null);
		mNavigationBar.addLeftButton("", DrawableUtil.getDrawable(this, R.drawable.ic_close), TEXT_COLOR, null);
		
		mNavigationBar.addRightButton("", DrawableUtil.getDrawable(this, R.drawable.ic_delete), TEXT_COLOR, null);
		mNavigationBar.addRightButton("取消", null, TEXT_COLOR, null);
		
		mNavigationBar.setTitle("标题标题标题标题标题标题标题标题标题标题");
		mNavigationBar.setSubTitle("标题标题标题标题标题标题标题标题标题标题");
		mNavigationBar.setTitleTextColor(TEXT_COLOR);
		
//		mNavigationBar.setNavigationBarStyle(NavigationBar.Style.TAB);
//		mNavigationBar.setTabs(new String[] {"消息", "电话"}, BG_COLOR, TEXT_COLOR, new TabView.OnTabCheckedListener() {
//
//			@Override
//			public void onChecked(CompoundButton tab, int position) {
//				Toast.makeText(MainActivity.this, "tab " + position + " is selected", Toast.LENGTH_SHORT).show();
//			}
//			
//		});
		
		mSearchView = (SearchView) findViewById(R.id.search_view);
		mSearchView.setImeOption(EditorInfo.IME_ACTION_SEARCH);
		mSearchView.setOnEditorActionListener(mOnEditorActionListener);
		
		mSearchView.setOnFocusChangeListener(mOnFocusChangeListener);
		mSearchView.setButtonTextColor(BG_COLOR);
		mSearchView.setHintTextColor(0xFF8E8E93);
		mSearchView.setButtonText("取消");
		mSearchView.setStyle(SearchView.Style.ROUND);
		
	}
	
	private OnFocusChangeListener mOnFocusChangeListener = new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus && mNavigationBar.isShown()) {
				mNavigationBar.hide();
			} else if (!hasFocus && !mNavigationBar.isShown()) {
				mNavigationBar.show();
			}
		}
	};
	
	private OnEditorActionListener mOnEditorActionListener = new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (EditorInfo.IME_ACTION_SEARCH == actionId) {
				Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
			}
			return false;
		}
	};

}
