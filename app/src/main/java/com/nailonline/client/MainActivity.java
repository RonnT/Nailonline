package com.nailonline.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.nailonline.client.entity.Promo;
import com.nailonline.client.extension.IOnPagerItemClick;
import com.nailonline.client.helper.PrefsHelper;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.master.MasterTabActivity;
import com.nailonline.client.order.OrderTabActivity;
import com.nailonline.client.present.PresentDialogFragment;
import com.nailonline.client.profile.ProfileActivity;
import com.nailonline.client.promo.PromoDialogFragment;
import com.nailonline.client.promo.PromoMainSlideAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.nailonline.client.promo.PromoSlidePageFragment.POSITION_KEY;

public class MainActivity extends BaseActivity implements IOnPagerItemClick {

    private static int AUTOSCROLL_INTERVAL = 5000;

    public List<Promo> promoList = new ArrayList<>();
    private AutoScrollViewPager pager;
    private PromoMainSlideAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
        initViewPager();
    }

    private void checkUserRegistration(){
        if (TextUtils.isEmpty(PrefsHelper.getInstance().getUserToken())){
            findViewById(R.id.orderLayout).setVisibility(View.GONE);
        } else findViewById(R.id.orderLayout).setVisibility(View.VISIBLE);
    }

    private void initViewPager() {
        promoList.clear();
        promoList.addAll(RealmHelper.getAllPromo());
        pager = (AutoScrollViewPager) findViewById(R.id.promoPager);
        pager.setInterval(AUTOSCROLL_INTERVAL);
        adapter = new PromoMainSlideAdapter(getSupportFragmentManager(), promoList);
        pager.setAdapter(adapter);
    }

    public void onMasterClick(View v) {
        Intent intent = new Intent(this, MasterTabActivity.class);
        startActivity(intent);
    }

    public void onServiceClick(View v) {
        Intent intent = new Intent(this, ServiceListActivity.class);
        startActivity(intent);
    }

    public void onGiftClick(View v) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        PresentDialogFragment newFragment = new PresentDialogFragment();
        newFragment.show(fm, "abc");
    }

    public void onProfileClick(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPagerItemClick(View view, int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        PromoDialogFragment newFragment = new PromoDialogFragment();
        newFragment.setArguments(args);
        newFragment.show(fm, "abc");
    }

    public void onOrderClick(View v){
        Intent intent = new Intent(this, OrderTabActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        pager.startAutoScroll();
        checkUserRegistration();
        super.onResume();

    }

    @Override
    protected void onPause() {
        pager.stopAutoScroll();
        super.onPause();
    }
}
