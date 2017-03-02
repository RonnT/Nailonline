package com.nailonline.client.region;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;
import com.nailonline.client.entity.Region;
import com.nailonline.client.helper.RealmHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Roman T. on 24.02.2017.
 */

public class SelectDistrictActivity extends BaseActivity {

    List<Region> districtList;
    Set<Integer> selectedIdSet = new HashSet<>();
    DistrictListAdapter adapter;

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
        addAllRegionToSelected();
        adapter = new DistrictListAdapter();
        ((ListView) findViewById(R.id.districtListView)).setAdapter(adapter);
        ((ListView) findViewById(R.id.districtListView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Region clickedRegion = districtList.get(i);
                if (selectedIdSet.contains(clickedRegion.geteRegionId())){
                    selectedIdSet.remove(clickedRegion.geteRegionId());
                } else selectedIdSet.add(clickedRegion.geteRegionId());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void addAllRegionToSelected(){
        for (Region region : districtList){
            selectedIdSet.add(region.geteRegionId());
        }

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
        switch (item.getItemId()){
            case (R.id.check_all):
                addAllRegionToSelected();
                adapter.notifyDataSetChanged();
                return true;
            case (R.id.uncheck_all):
                selectedIdSet.clear();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class DistrictListAdapter extends BaseAdapter{

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
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.view_region_item, viewGroup, false);
            }
            ((TextView)view.findViewById(R.id.regionTitle)).setText(region.geteRegionLabel());
            ImageView checkView = (ImageView)view.findViewById(R.id.imageViewCheck);
            if (selectedIdSet.contains(region.geteRegionId())) checkView.setVisibility(View.VISIBLE);
            else checkView.setVisibility(View.GONE);
            checkView.setColorFilter(getUserTheme().getParsedAC());
            return view;
        }
    }
}
