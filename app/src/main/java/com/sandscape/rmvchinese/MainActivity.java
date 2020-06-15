package com.sandscape.rmvchinese;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sandscape.rmvchinese.Fragments.AllApps;
import com.sandscape.rmvchinese.Fragments.ChinaApps;
import com.sandscape.rmvchinese.Listeners.PageChangeListener;
import com.sandscape.rmvchinese.Utils.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView navigationView;
    private TextView title;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setFrameLayout();
        loadAds();
    }

    private void init() {
        viewPager = findViewById(R.id.nav_host_fragment);
        navigationView = findViewById(R.id.bottom_nav);
        title = findViewById(R.id.title);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_id));
    }

    private void loadAds() {
        final Dialog dialog = Utils.materialDialog(this, R.layout.loading_dialog);
        dialog.setCancelable(false);
        dialog.show();
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                dialog.cancel();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                dialog.cancel();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                dialog.cancel();
                interstitialAd.show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setFrameLayout() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChinaApps(), "China Apps");
        viewPagerAdapter.addFragment(new AllApps(), "All Apps");
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new PageChangeListener() {
            @Override
            public void onChinaAppSelected() {
                navigationView.setSelectedItemId(R.id.china_app);
                title.setText("China Apps");
            }

            @Override
            public void onAllAppSelected() {
                navigationView.setSelectedItemId(R.id.all_apps);
                title.setText("All Apps");
            }
        });

        navigationView.setOnNavigationItemSelectedListener(new PageChangeListener() {
            @Override
            public void onChinaAppSelected() {
                viewPager.setCurrentItem(0, true);
                title.setText("China Apps");
            }

            @Override
            public void onAllAppSelected() {
                viewPager.setCurrentItem(1, true);
                title.setText("All Apps");
            }
        });
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        Fragment getFragment(int pos) {
            return fragments.get(pos);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }

}
