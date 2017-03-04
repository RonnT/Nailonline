package com.nailonline.client.region;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;
import com.nailonline.client.entity.Region;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.master.MasterTabActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Roman T. on 24.02.2017.
 */

public class SelectDistrictActivity extends BaseActivity {

    private List<Region> districtList;
    private Set<Integer> selectedIdSet = new HashSet<>();
    private DistrictListAdapter adapter;

    @Override
    protected void setData(Bundle savedInstanceState) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        districtList = RealmHelper.getRegionList();
        Collections.sort(districtList, new Comparator<Region>() {
            @Override
            public int compare(Region region, Region t1) {
                return region.geteRegionLabel().compareTo(t1.geteRegionLabel());
            }
        });
        selectedIdSet.addAll(getOriginalRegionSet());
        adapter = new DistrictListAdapter();
        ((ListView) findViewById(R.id.districtListView)).setAdapter(adapter);
        ((ListView) findViewById(R.id.districtListView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Region clickedRegion = districtList.get(i);
                if (selectedIdSet.contains(clickedRegion.geteRegionId())) {
                    selectedIdSet.remove(clickedRegion.geteRegionId());
                } else selectedIdSet.add(clickedRegion.geteRegionId());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private Set<Integer> getOriginalRegionSet() {
        Set<Integer> resultSet = new HashSet<>();
        List<Integer> selectedIdsList = getIntent().getIntegerArrayListExtra(MasterTabActivity.REGIONS_KEY);
        if (selectedIdsList == null || selectedIdsList.isEmpty()) {
            resultSet.addAll(getIdSetFromRegionList());
        } else resultSet.addAll(selectedIdsList);
        return resultSet;
    }

    private Set<Integer> getIdSetFromRegionList(){
        Set<Integer> resultSet = new HashSet<>();
        for (Region region : districtList) {
            resultSet.add(region.geteRegionId());
        }
        return resultSet;
    }

    @Override
    public void onBackPressed() {
        if (isFilterChanged()) {
            showSureDialog();
        } else super.onBackPressed();
    }

    private boolean isFilterChanged() {
        Set<Integer> originalSet = getOriginalRegionSet();
        return !originalSet.equals(selectedIdSet);
    }

    private void showSureDialog() {
        final MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title(R.string.warning)
                .content(R.string.confirmation_apply)
                .positiveText(android.R.string.ok)
                .positiveColor(getUserTheme().getParsedAC())
                .negativeColor(getUserTheme().getParsedAC())
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.putIntegerArrayListExtra(MasterTabActivity.REGIONS_KEY, new ArrayList<>(selectedIdSet));
                        setResult(MasterTabActivity.FILTER_CHANGED_CODE, intent);
                        SelectDistrictActivity.super.onBackPressed();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SelectDistrictActivity.super.onBackPressed();
                    }
                })
                .show();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_district_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_district_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.check_all):
                selectedIdSet.clear();
                selectedIdSet.addAll(getIdSetFromRegionList());
                adapter.notifyDataSetChanged();
                return true;
            case (R.id.uncheck_all):
                selectedIdSet.clear();
                adapter.notifyDataSetChanged();
                return true;
            case (android.R.id.home):
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class DistrictListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return districtList.size();
        }

        @Override
        public Object getItem(int i) {
            return districtList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Region region = (Region) getItem(i);
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.view_region_item, viewGroup, false);
            }
            ((TextView) view.findViewById(R.id.regionTitle)).setText(region.geteRegionLabel());
            ImageView checkView = (ImageView) view.findViewById(R.id.imageViewCheck);
            if (selectedIdSet.contains(region.geteRegionId()))
                checkView.setVisibility(View.VISIBLE);
            else checkView.setVisibility(View.GONE);
            checkView.setColorFilter(getUserTheme().getParsedAC());
            return view;
        }
    }
}
