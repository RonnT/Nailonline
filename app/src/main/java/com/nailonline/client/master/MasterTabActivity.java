package com.nailonline.client.master;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.NewOrderActivity;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;
import com.nailonline.client.helper.RealmHelper;

import java.util.List;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class MasterTabActivity extends BaseActivity {

    private List<Master> masterList;

    public List<Master> getMasterList() {
        return masterList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_master_tab;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNeedDeepSetColor = false;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTabLayout();
        masterList = RealmHelper.getAllMasters();
    }

    private void setTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.master_tablayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setBackgroundColor(userTheme.getParsedMC());
        tabLayout.setSelectedTabIndicatorColor(userTheme.getParsedAC());
        ViewPager pager = (ViewPager) findViewById(R.id.master_viewpager);
        MasterTabAdapter adapter = new MasterTabAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    public void makeNewOrder(Master master) {
        Intent intent = new Intent(this, NewOrderActivity.class);
        intent.putExtra(NewOrderActivity.TAG_MASTER_ID, master.getMasterId());
        startActivity(intent);
    }

    public void makeNewOrder(int masterPosition) {
        Intent intent = new Intent(this, NewOrderActivity.class);
        intent.putExtra(NewOrderActivity.TAG_MASTER_ID, masterList.get(masterPosition).getMasterId());
        startActivity(intent);
    }

    private class MasterTabAdapter extends android.support.v4.app.FragmentPagerAdapter {

        private Fragment[] fragments = {
                new MasterMapFragment(),
                new MasterListFragment(),
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
