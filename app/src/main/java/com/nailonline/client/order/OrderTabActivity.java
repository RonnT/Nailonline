package com.nailonline.client.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;

import java.util.List;

/**
 * Created by Roman T. on 15.02.2017.
 */

public class OrderTabActivity extends BaseActivity {

    public static final String TEMPLATE_ID_KEY = "TEMPLATE_ID_KEY";

    private List<Master> orderList;

    public List<Master> getOrderList() {
        return orderList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_tab;
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
        //orderList = RealmHelper.getAllMasters();
    }

    private void setTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.order_tablayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setBackgroundColor(userTheme.getParsedMC());
        tabLayout.setSelectedTabIndicatorColor(userTheme.getParsedAC());
        ViewPager pager = (ViewPager) findViewById(R.id.order_viewpager);
        OrderTabActivity.OrderTabAdapter adapter = new OrderTabActivity.OrderTabAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    private class OrderTabAdapter extends android.support.v4.app.FragmentPagerAdapter {

        private Fragment[] fragments = {
                new OrderByMasterFragment(),
                new OrderByDateFragment()};

        private int tabTitlesId[] = {R.string.tab_by_master, R.string.tab_by_date};

        OrderTabAdapter(FragmentManager fm) {
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
