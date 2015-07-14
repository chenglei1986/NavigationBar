# NavigationBar

![image](https://github.com/chenglei1986/NavigationBar/blob/master/gif/screen_capture.gif)

# Usage

### In your layout
```xml
<org.chenglei.ios8.NavigationBar
    android:id="@+id/navigation_bar"
    android:layout_width="match_parent"
    android:layout_height="@dimen/min_navigation_bar_height" />
    
<org.chenglei.ios8.SearchView
    android:id="@+id/search_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```
### In your activity

```java
mNavigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
mNavigationBar.setBackgroundColor(BG_COLOR);

mNavigationBar.setNavigationBarStyle(NavigationBar.Style.NORMAL);
mNavigationBar.setTitle("Title Title Title");
mNavigationBar.setSubTitle("Subtitle Subtitle");
mNavigationBar.setTitleTextColor(TEXT_COLOR);

mNavigationBar.addLeftButton(
		"Back", 
		DrawableUtil.getDrawable(this, R.drawable.ic_back), 
		TEXT_COLOR, 
		new OnClickListener() {
			
	@Override
	public void onClick(View v) {
				
	}
});

mNavigationBar.addLeftButton(
		"", 
		DrawableUtil.getDrawable(this, R.drawable.ic_close), 
		TEXT_COLOR, 
		new OnClickListener() {
			
	@Override
	public void onClick(View v) {
				
	}
});
		
mRightButton = mNavigationBar.addRightButton("Tab", null, TEXT_COLOR, new OnClickListener() {
			
	@Override
	public void onClick(View v) {
			
	}
});
```

```java
mNavigationBar.setNavigationBarStyle(NavigationBar.Style.TAB);
mNavigationBar.setTabs(new String[] {"MSG", "TEL"}, BG_COLOR, TEXT_COLOR, new TabView.OnTabCheckedListener() {
			
	@Override
	public void onChecked(CompoundButton tab, int position) {
		Toast.makeText(MainActivity.this, "tab " + position + " is selected", Toast.LENGTH_SHORT).show();
	}
});
mNavigationBar.setTabSizeFixed(true);
```

```java
mSearchView = (SearchView) findViewById(R.id.search_view);
mSearchView.setImeOption(EditorInfo.IME_ACTION_SEARCH);
mSearchView.setOnEditorActionListener(new OnEditorActionListener() {
			
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
});
		
mSearchView.setOnFocusChangeListener(new OnFocusChangeListener() {
			
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
				
	}
});
mSearchView.setButtonTextColor(BG_COLOR);
mSearchView.setHint("Search");
mSearchView.setHintTextColor(0xFF8E8E93);
mSearchView.setButtonText("Cancel");
mSearchView.setStyle(SearchView.Style.ROUND);
```
