package com.nailonline.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nailonline.client.entity.Promo;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.master.MasterTabActivity;
import com.nailonline.client.promo.PromoSlidePageAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends BaseActivity {

    private static int AUTOSCROLL_INTERVAL = 5000;

    private List<Promo> promoList = new ArrayList<>();
    private AutoScrollViewPager pager;
    private PromoSlidePageAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
        initViewPager();
    }

    private void initViewPager(){
        promoList.clear();
        promoList.addAll(RealmHelper.getAllPromo());
        pager = (AutoScrollViewPager) findViewById(R.id.promoPager);
        pager.setInterval(AUTOSCROLL_INTERVAL);
        pager.startAutoScroll();
        adapter = new PromoSlidePageAdapter(getSupportFragmentManager(), promoList);
        pager.setAdapter(adapter);
    }

    public void onMasterClick(View v){
        Intent intent = new Intent(this, MasterTabActivity.class);
        startActivity(intent);
    }

    public void onServiceClick(View v){
        Toast.makeText(this,"onServiceClick", Toast.LENGTH_SHORT).show();
    }

    public void onGiftClick(View v){
        Toast.makeText(this,"onGiftClick", Toast.LENGTH_SHORT).show();
    }

    public void onProfileClick(View v){
        Intent intent = new Intent(this, UserThemeActivity.class);
        startActivity(intent);
    }

}
