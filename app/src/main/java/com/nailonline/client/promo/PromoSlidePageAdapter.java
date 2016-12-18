package com.nailonline.client.promo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nailonline.client.entity.Promo;

import java.util.List;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class PromoSlidePageAdapter extends FragmentPagerAdapter {

    private List<Promo> itemList;

    public PromoSlidePageAdapter(FragmentManager fm, List<Promo> promoList){
        super(fm);
        itemList = promoList;
    }

    @Override
    public Fragment getItem(int position) {
        return PromoSlidePageFragment.newInstance(itemList.get(position).getPromoId());
    }

    @Override
    public int getCount() {
        return itemList.size();
    }
}
