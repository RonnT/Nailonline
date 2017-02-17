package com.nailonline.client.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.Job;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman T. on 15.02.2017.
 */

public class OrderTabActivity extends BaseActivity {

    public static final String TEMPLATE_ID_KEY = "TEMPLATE_ID_KEY";
    public static final String JOB_LIST_KEY = "JOB_LIST_KEY";

    private ArrayList<Job> jobList;

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.order_tablayout);
        tabLayout.setBackgroundColor(userTheme.getParsedMC());

        ApiVolley.getInstance().getUserJobs(0, 0, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jobList = parseJobs(response);
                    Log.d("Debug", jobList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setTabLayout();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public static ArrayList<Job> parseJobs(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Type listType = new TypeToken<List<Job>>() {}.getType();
        ArrayList<Job> result = new Gson().fromJson(jsonArray.toString(), listType);
        return result;
    }

    private void setTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.order_tablayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setSelectedTabIndicatorColor(userTheme.getParsedAC());
        ViewPager pager = (ViewPager) findViewById(R.id.order_viewpager);
        OrderTabActivity.OrderTabAdapter adapter = new OrderTabActivity.OrderTabAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    private class OrderTabAdapter extends android.support.v4.app.FragmentPagerAdapter {

        private Fragment[] fragments = {
                getOrderByMasterFragment(),
                getOrderByDateFragment()};

        private int tabTitlesId[] = {R.string.tab_by_master, R.string.tab_by_date};

        private OrderByMasterFragment getOrderByMasterFragment(){
            OrderByMasterFragment fragment = new OrderByMasterFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList(JOB_LIST_KEY, jobList);
            fragment.setArguments(args);
            return fragment;
        }

        private OrderByDateFragment getOrderByDateFragment(){
            OrderByDateFragment fragment = new OrderByDateFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList(JOB_LIST_KEY, jobList);
            fragment.setArguments(args);
            return fragment;
        }


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
