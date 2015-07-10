package org.chenglei.demo;

import org.chenglei.ios8.NavigationBar;
import org.chenglei.ios8.SearchView;
import org.chenglei.ios8.TabView;
import org.chenglei.utils.DrawableUtil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private NavigationBar mNavigationBar;
	
	private static final int TEXT_COLOR = Color.parseColor("#FFFFFF");
	
	private static final int BG_COLOR = Color.parseColor("#25B6ED");
	
	private SearchView mSearchView;
	
	private Button mRightButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mNavigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mNavigationBar.setBackgroundColor(BG_COLOR);
		
		mNavigationBar.addLeftButton("Back", DrawableUtil.getDrawable(this, R.drawable.ic_back), TEXT_COLOR, null);
		mNavigationBar.addLeftButton("", DrawableUtil.getDrawable(this, R.drawable.ic_close), TEXT_COLOR, null);
		
		mRightButton = mNavigationBar.addRightButton("Tab", null, TEXT_COLOR, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				switch (mNavigationBar.getNavigationBarStyle()) {
				case NavigationBar.Style.NORMAL:
					
					mRightButton.setText("Title");
					mNavigationBar.setNavigationBarStyle(NavigationBar.Style.TAB);
					mNavigationBar.setTabs(new String[] {"MSG", "TEL"}, BG_COLOR, TEXT_COLOR, new TabView.OnTabCheckedListener() {
			
						@Override
						public void onChecked(CompoundButton tab, int position) {
							Toast.makeText(MainActivity.this, "tab " + position + " is selected", Toast.LENGTH_SHORT).show();
						}
						
					});
					mNavigationBar.setTabSizeFixed(true);
					break;
				case NavigationBar.Style.TAB:
					
					mRightButton.setText("Tab");
					mNavigationBar.setNavigationBarStyle(NavigationBar.Style.NORMAL);
					mNavigationBar.setTitle("Title Title Title");
					mNavigationBar.setSubTitle("Subtitle Subtitle");
					mNavigationBar.setTitleTextColor(TEXT_COLOR);
					
					break;
				}
				
			}
		});
		//mNavigationBar.addRightButton("", DrawableUtil.getDrawable(this, R.drawable.ic_delete), TEXT_COLOR, null);
		
		mNavigationBar.setTitle("Title Title Title");
		mNavigationBar.setSubTitle("Subtitle Subtitle");
		mNavigationBar.setTitleTextColor(TEXT_COLOR);
		
		mSearchView = (SearchView) findViewById(R.id.search_view);
		mSearchView.setImeOption(EditorInfo.IME_ACTION_SEARCH);
		mSearchView.setOnEditorActionListener(mOnEditorActionListener);
		
		mSearchView.setOnFocusChangeListener(mOnFocusChangeListener);
		mSearchView.setButtonTextColor(BG_COLOR);
		mSearchView.setHint("Search");
		mSearchView.setHintTextColor(0xFF8E8E93);
		mSearchView.setButtonText("Cancel");
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
