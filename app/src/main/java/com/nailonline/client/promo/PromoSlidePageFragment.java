package com.nailonline.client.promo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.helper.RealmHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class PromoSlidePageFragment extends Fragment {

    public static final String PROMO_ID_KEY = "PROMO_ID_KEY";

    public Promo promo;

    static PromoSlidePageFragment newInstance(int promoId){
        PromoSlidePageFragment fragment = new PromoSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(PROMO_ID_KEY, promoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo_page, null);
        int promoId = getArguments().getInt(PROMO_ID_KEY);
        promo = RealmHelper.getPromoById(promoId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt(PROMO_ID_KEY, promo.getPromoId());
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                PromoDialogFragment newFragment = new PromoDialogFragment();
                newFragment.setArguments(args);
                newFragment.show(fm, "abc");
            }
        });

        ImageView promoImageView = ((ImageView) view.findViewById(R.id.promoImage));
        TextView labelTextView = ((TextView)view.findViewById(R.id.promoTextLabel));
        TextView bodyTextView = ((TextView)view.findViewById(R.id.promoTextBody));

        Picasso.with(getActivity())
                .load(BuildConfig.SERVER_IMAGE_PROMO + promo.getPhotoName())
                .into(promoImageView);

        labelTextView.setText(promo.getLabel());
        bodyTextView.setText(promo.getBody());

        return view;
    }
}
