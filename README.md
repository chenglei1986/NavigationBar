# NavigationBar

Normal
![normal](screenshots/normal.png)

Round Tab
![round_tab](screenshots/round_tab.png)

Round Rect Tab
![round_rect_tab](screenshots/round_rect_tab.png)

Custom Tab
![custom_tab](screenshots/custom_tab.png)

# Usage

### Gradle
Step 1. Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Step 2. Add the dependency

```groovy
dependencies {
    compile 'com.github.chenglei1986:NavigationBar:-SNAPSHOT'
}
```

### Maven
```groovy
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```groovy
<dependency>
    <groupId>com.github.chenglei1986</groupId>
    <artifactId>NavigationBar</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```

### In your layout
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/root"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <com.github.chenglei1986.navigationbar.NavigationBar
        android:id="@+id/navigation_bar"
        android:layout_width="match_parent"
        android:layout_height="@dimen/min_navigation_bar_height"/>

</LinearLayout>
```
### In your activity

```java
mNavigationBar.setDisplayBackButton(true)
    .setBackButtonImageResource(R.drawable.ic_chevron_left_white_24dp)
    .setBackButtonText("Back")
    .setOnBackButtonClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    })
    .setTitle("Your title text")
    .addItem(0, "Settings", ActivityCompat.getDrawable(this, R.drawable.ic_settings_white_24dp))
    .addItem(1, "More", ActivityCompat.getDrawable(this, R.drawable.ic_more_vert_white_24dp))
    .setOnMenuItemClickListener(new NavigationBar.OnMenuItemClickListener() {
        @Override
        public void onMenuItemClick(int id) {
            // Do something
        }
    });
```

### In your AndroidManifest.xml

```xml
<activity
    android:name=".ActivityName"
    android:label="@string/label"
    android:theme="@style/NavigationBar">
</activity>
```

### Theming

```xml
<!-- Activity theme. -->
<style name="NavigationBar" parent="AppTheme">
    <item name="navigationBar">@style/NavigationBarStyle</item>
</style>

<!-- NavigationBar theme. -->
<style name="NavigationBarStyle">
    <item name="nv_type">normal</item>
    <item name="android:background">@color/nv_blue</item>
    <item name="nv_buttonTextColor">@color/nv_white</item>
    <item name="nv_buttonTextSize">@dimen/buttonTextSize</item>
    <item name="nv_titleColor">@color/titleColor</item>
    <item name="nv_titleSize">@dimen/titleSize</item>
    <item name="nv_subtitleColor">@color/titleColor</item>
    <item name="nv_subtitleSize">@dimen/subtitleSize</item>

    <item name="nv_tabFirstBackground">@drawable/round_rect_tab_first</item>
    <item name="nv_tabMiddleBackground">@drawable/tab_middle</item>
    <item name="nv_tabLastBackground">@drawable/round_rect_tab_last</item>
    <item name="nv_tabTextColor">@color/tab_text_color</item>
</style>
```

### Attributes

|attr                          |description                                                                  |
|------------------------------|-----------------------------------------------------------------------------|
|nv_buttonTextColor            |Button text color on both sides of the `NavigationBar`                       |
|nv_buttonTextSize             |Button text size on both sides of the `NavigationBar`                        |
|nv_titleColor                 |Title text color                                                             |
|nv_titleSize                  |Title text size                                                              |
|nv_titleText                  |Title text                                                                   |
|nv_subtitleColor              |Subtitle text color                                                          |
|nv_subtitleSize               |Subtitle text size                                                           |
|nv_subtitleText               |Subtitle text                                                                |
|nv_tabFirstBackground         |Background drawable of first tab, should be selector                         |
|nv_tabMiddleBackground        |Background drawable of tabs except first or last, should be selector         |
|nv_tabLastBackground          |Background drawable of last tab, should be selector                          |
|nv_tabTextColor               |Text color of the tabs                                                       |
|nv_type                       |Type of the `NavigationBar`, should be `normal` or `tab`, default is `normal`|

### Bind with `ViewPager`

```java
mNavigationBar.bindViewPager(mViewPager);
```

# Licence

NavigationBar is released under the MIT license. See [LICENSE](https://raw.githubusercontent.com/wangjwchn/AImage/master/LICENSE) for details.
