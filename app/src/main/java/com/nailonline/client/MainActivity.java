package com.nailonline.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends BaseActivity {

    public AutoScrollViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Toast.makeText(this,"onProfileClick", Toast.LENGTH_SHORT).show();
    }

}
