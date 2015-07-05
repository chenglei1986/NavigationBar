package org.chenglei.demo;

import org.chenglei.ios8.NavigationBar;
import org.chenglei.utils.DrawableUtil;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private NavigationBar mNavigationBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mNavigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mNavigationBar.setLeftButton("设置", DrawableUtil.getDrawable(this, R.drawable.ic_back), null);
	}

}
