package com.nailonline.client.promo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nailonline.client.entity.Promo;

import java.util.List;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class PromoMainSlideAdapter extends FragmentPagerAdapter {

    private List<Promo> itemList;

    public PromoMainSlideAdapter(FragmentManager fm, List<Promo> promoList){
        super(fm);
        itemList = promoList;
    }

    @Override
    public Fragment getItem(int position) {
        return PromoSlidePageFragment.newInstance(position, itemList.get(position).getPromoId());
    }

    @Override
    public int getCount() {
        return itemList.size();
    }
}
