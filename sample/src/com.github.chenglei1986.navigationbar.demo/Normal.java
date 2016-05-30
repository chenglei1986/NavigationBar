package com.github.chenglei1986.navigationbar.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.github.chenglei1986.navigationbar.NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Normal extends Activity {

    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
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
                .setTitle(getIntent().getStringExtra("title"))
                .addItem(0, "Settings", ActivityCompat.getDrawable(this, R.drawable.ic_settings_white_24dp))
                .addItem(1, "More", ActivityCompat.getDrawable(this, R.drawable.ic_more_vert_white_24dp))
                .setOnMenuItemClickListener(new NavigationBar.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int id) {
                        Toast.makeText(getApplicationContext(), "" + id, Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
