package com.nailonline.client.master;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Region;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.order.makenew.NewOrderActivity;
import com.nailonline.client.region.SelectDistrictActivity;

import java.util.ArrayList;
import java.util.List;

import static com.nailonline.client.helper.RealmHelper.getAllMasters;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class MasterTabActivity extends BaseActivity {

    public static final String TEMPLATE_ID_KEY = "TEMPLATE_ID_KEY";
    public static final String REGIONS_KEY = "REGIONS_KEY";

    private static final int FILTER_REQUEST_CODE = 999;
    public static final int FILTER_CHANGED_CODE = 888;

    private List<Master> masterList = new ArrayList<>();

    public List<Master> getMasterList() {
        return masterList;
    }

    private ArrayList<Integer> regionIdList = new ArrayList<>();
    private MasterTabAdapter adapter;

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
        updateMasterList();
    }

    private void updateMasterList(){
        masterList.clear();
        int skillsTemplateId = getIntent().getIntExtra(TEMPLATE_ID_KEY, -1);
        if (skillsTemplateId > -1) {
            masterList.addAll(RealmHelper.getMastersWithSkillsTemplate(skillsTemplateId));
        } else masterList.addAll(RealmHelper.getAllMasters());
        //apply region filter
        if (!regionIdList.isEmpty()){
            List<Master> newMasterList = new ArrayList<>();
            for (Master master : masterList){
                if (regionIdList.contains(master.getMasterLocation().geteRegionId())) newMasterList.add(master);
            }
            masterList.clear();
            masterList.addAll(newMasterList);
        }
    }

    private void setTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.master_tablayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setBackgroundColor(userTheme.getParsedMC());
        tabLayout.setSelectedTabIndicatorColor(userTheme.getParsedWC());
        ViewPager pager = (ViewPager) findViewById(R.id.master_viewpager);
        adapter = new MasterTabAdapter(getSupportFragmentManager());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.master_tab_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            Intent intent = new Intent(this, SelectDistrictActivity.class);
            intent.putIntegerArrayListExtra(REGIONS_KEY, regionIdList);
            startActivityForResult(intent, FILTER_REQUEST_CODE);
            return true;

        } else return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILTER_REQUEST_CODE){
            if (resultCode == FILTER_CHANGED_CODE){
                regionIdList = data.getIntegerArrayListExtra(REGIONS_KEY);
                updateMasterList();
                notifyFragments();
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    private void notifyFragments(){
        Fragment[] fragments = adapter.fragments;
        for (Fragment fragment : fragments){
            ((IUpdatable) fragment).onUpdate();
        }
    }

    public List<Integer> getRegionIdList() {
        return regionIdList;
    }

    private class MasterTabAdapter extends android.support.v4.app.FragmentPagerAdapter {

        private Fragment[] fragments = {
                new MasterListFragment(),
                new MasterMapFragment(),
                new MasterGalleryFragment()};

        private int tabTitlesId[] = {R.string.tab_list, R.string.tab_map, R.string.tab_gallery};

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
