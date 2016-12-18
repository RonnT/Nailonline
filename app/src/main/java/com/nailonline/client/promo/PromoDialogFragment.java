package com.nailonline.client.promo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.helper.RealmHelper;
import com.squareup.picasso.Picasso;

import static com.nailonline.client.promo.PromoSlidePageFragment.PROMO_ID_KEY;

/**
 * Created by Roman T. on 18.12.2016.
 */

public class PromoDialogFragment extends DialogFragment {

    private Promo promo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int promoId = getArguments().getInt(PROMO_ID_KEY);
        promo = RealmHelper.getPromoById(promoId);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_promo, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.promoImage);
        imageView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Log.d("Nail", String.valueOf(imageView.getMeasuredWidth()));

        TextView textLabel = (TextView) view.findViewById(R.id.promoTextLabel);
        TextView timeLeftText = (TextView) view.findViewById(R.id.promoTimeLeft);
        TextView bodyText = (TextView) view.findViewById(R.id.promoTextBody);

        ViewGroup submitLayout = (ViewGroup) view.findViewById(R.id.submitLayout);
        submitLayout.setBackgroundColor(((BaseActivity) getActivity()).getUserTheme().getParcedAC());

        textLabel.setText(promo.getLabel());
        bodyText.setText(promo.getBody());

        Picasso.with(getActivity())
                .load(BuildConfig.SERVER_IMAGE_PROMO + promo.getPhotoName())
                .into(imageView);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.customView(view, false);

        return builder.build();
    }
}
