package com.sandscape.rmvchinese.Listeners;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sandscape.rmvchinese.R;

public abstract class PageChangeListener implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1) {
            onAllAppSelected();
        } else {
            onChinaAppSelected();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.all_apps) {
            onAllAppSelected();
            return true;
        } else if (item.getItemId() == R.id.china_app) {
            onChinaAppSelected();
            return true;
        }
        return false;
    }

    public abstract void onChinaAppSelected();

    public abstract void onAllAppSelected();
}
