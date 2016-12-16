package com.nailonline.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends BaseActivity {

    public AutoScrollViewPager pager;

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
        pager = (AutoScrollViewPager) findViewById(R.id.viewPager);

    }

    public void onMasterClick(View v){
        Toast.makeText(this,"onMasterClick", Toast.LENGTH_SHORT).show();
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
