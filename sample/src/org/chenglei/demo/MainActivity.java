package org.chenglei.demo;

import org.chenglei.ios8.NavigationBar;
import org.chenglei.utils.DrawableUtil;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private NavigationBar mNavigationBar;
//	private TabItem mTabItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mNavigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mNavigationBar.addLeftButton("返回", DrawableUtil.getDrawable(this, R.drawable.ic_back), null);
		mNavigationBar.addLeftButton("", DrawableUtil.getDrawable(this, R.drawable.ic_close), null);
		
		mNavigationBar.addRightButton("", DrawableUtil.getDrawable(this, R.drawable.ic_delete), null);
		mNavigationBar.addRightButton("取消", null, null);
		
		mNavigationBar.setTitle("标题标题标题标题标题标题标题标题标题标题");
		mNavigationBar.setSubTitle("标题标题标题标题标题标题标题标题标题标题标题标题");
		
		mNavigationBar.setNavigationBarStyle(NavigationBar.Style.TAB);
		mNavigationBar.setTabs(new String[] {"消息", "电话"});
//		mTabItem = (TabItem) findViewById(R.id.tab_item);
//		mTabItem.setPosition(TabItem.POSITION_MIDDLE);
//		mTabItem.setItemColors(Color.BLUE, Color.WHITE);
//		mTabItem.setText("123");
		//mTabItem.setChecked(true);
	}

}
