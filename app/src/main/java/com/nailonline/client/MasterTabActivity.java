package com.nailonline.client;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class MasterTabActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_tab;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTabLayout();
    }

    private void setTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.master_tablayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setBackgroundColor(userTheme.getParsedMC());
        tabLayout.setSelectedTabIndicatorColor(userTheme.getParcedAC());
        ViewPager pager = (ViewPager) findViewById(R.id.master_viewpager);
        MasterTabAdapter adapter = new MasterTabAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    private class MasterTabAdapter extends android.support.v4.app.FragmentStatePagerAdapter{

        private Fragment[] fragments = {
                new MasterGalleryFragment(),
                new MasterGalleryFragment(),
                new MasterGalleryFragment()};

        private int tabTitlesId[] = {R.string.tab_map, R.string.tab_list, R.string.tab_gallery};

        MasterTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(tabTitlesId[position]);
        }
    }
}
