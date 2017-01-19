package com.nailonline.client.promo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.MainActivity;
import com.nailonline.client.NewOrderActivity;
import com.nailonline.client.R;
import com.nailonline.client.entity.Promo;

import java.util.List;

import static com.nailonline.client.promo.PromoSlidePageFragment.POSITION_KEY;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class PromoDialogFragment extends DialogFragment {

    private List<Promo> promoList;
    private PromoDialogPageAdapter adapter;
    private ViewPager pager;
    private int currentPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPosition = getArguments().getInt(POSITION_KEY);
        promoList = ((MainActivity)getActivity()).promoList;
    }

    private void initViewPager(View view){
        pager = (ViewPager) view.findViewById(R.id.promoViewPager);
        adapter = new PromoDialogPageAdapter(getChildFragmentManager(), promoList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentPosition);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(pager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_promo, null);
        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e){
            e.printStackTrace();
        }
        View submitView = view.findViewById(R.id.submitLayout);
        submitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPos = pager.getCurrentItem();
                int masterId = promoList.get(currentPos).getMasterId();
                makeNewOrder(masterId);
            }
        });
        submitView.setBackgroundColor(((BaseActivity) getActivity()).getUserTheme().getParsedAC());
        initViewPager(view);
        return view;
    }

    public void makeNewOrder(int masterId) {
        Intent intent = new Intent(getActivity(), NewOrderActivity.class);
        intent.putExtra(NewOrderActivity.TAG_MASTER_ID, masterId);
        startActivity(intent);
    }
}
