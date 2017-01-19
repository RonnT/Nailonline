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

import static com.nailonline.client.promo.PromoSlidePageFragment.PROMO_ID_KEY;

/**
 * Created by Roman T. on 06.01.2017.
 */

public class PromoDialogPageFragment extends Fragment {

    static PromoDialogPageFragment newInstance(int promoId) {
        PromoDialogPageFragment fragment = new PromoDialogPageFragment();
        Bundle args = new Bundle();
        args.putInt(PROMO_ID_KEY, promoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_promo_page, null);
        int promoId = getArguments().getInt(PROMO_ID_KEY);
        Promo promo = RealmHelper.getPromoById(promoId);
        ImageView imageView = (ImageView) view.findViewById(R.id.promoImage);

        TextView textLabel = (TextView) view.findViewById(R.id.promoTextLabel);
        TextView timeLeftText = (TextView) view.findViewById(R.id.promoTimeLeft);
        TextView bodyText = (TextView) view.findViewById(R.id.promoTextBody);

        textLabel.setText(promo.getLabel());
        bodyText.setText(promo.getBody());

        Picasso.with(getActivity())
                .load(BuildConfig.SERVER_IMAGE_PROMO + promo.getPhotoName())
                .into(imageView);
        return view;
    }

}
