package com.nailonline.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nailonline.client.entity.Promo;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class PromoPageFragment extends Fragment {

    public static final String PROMO_KEY = "PROMO_KEY";

    static PromoPageFragment newInstance(Promo promo){
        PromoPageFragment fragment = new PromoPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(PROMO_KEY, promo);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo_page, null);

        //((ImageView) view.findViewById(R.id.promoImage)).setBackgroundColor();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
