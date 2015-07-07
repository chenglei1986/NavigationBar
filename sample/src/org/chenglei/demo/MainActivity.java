package org.chenglei.demo;

import org.chenglei.ios8.NavigationBar;
import org.chenglei.ios8.TabView;
import org.chenglei.utils.DrawableUtil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;

public class MainActivity extends Activity {
	
	private NavigationBar mNavigationBar;
	
	private static final int TEXT_COLOR = Color.parseColor("#FFFFFF");
	
	private static final int BG_COLOR = Color.parseColor("#25B6ED");
	
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
		mNavigationBar.setSubTitle("标题标题标题标题标题标题标题标题标题标题标题标题");
		mNavigationBar.setTitleTextColor(TEXT_COLOR);
		
		mNavigationBar.setNavigationBarStyle(NavigationBar.Style.TAB);
		mNavigationBar.setTabs(new String[] {"消息", "电话"}, BG_COLOR, TEXT_COLOR, new TabView.OnTabCheckedListener() {

			@Override
			public void onChecked(CompoundButton tab, int position) {
				System.out.println(position);
			}
			
		});

	}

}
