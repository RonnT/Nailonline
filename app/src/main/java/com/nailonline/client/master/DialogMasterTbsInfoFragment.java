package com.nailonline.client.master;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.RealmHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roman T. on 21.01.2017.
 */

public class DialogMasterTbsInfoFragment extends DialogFragment {
    public static final String MASTER_ID_KEY = "MASTER_ID_KEY";

    private Master master;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int masterId = getArguments().getInt(MASTER_ID_KEY);
        master = RealmHelper.getMasterById(masterId);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_view_master_tbs_info, null);
        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fillTbs(view);
        fillImage(view);
        fillSocialNetworks(view);
        view.findViewById(R.id.giftsText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add gifts
            }
        });
        return view;
    }

    private void fillImage(View rootView) {
        ImageView photoView = (ImageView) rootView.findViewById(R.id.master_image_view);

        Picasso.with(getActivity())
                .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + master.getMasterPhoto())
                .into(photoView);
    }

    private void fillSocialNetworks(View rootview) {
        int iconColor = ((BaseActivity) getActivity()).getUserTheme().getParsedMC();
        ImageView imageFb = (ImageView) rootview.findViewById(R.id.imageFacebook);
        ImageView imageVk = (ImageView) rootview.findViewById(R.id.imageVk);
        ImageView imageInsta = (ImageView) rootview.findViewById(R.id.imageInsta);
        ImageView imageOk = (ImageView) rootview.findViewById(R.id.imageOk);
        ImageView imagePhone = (ImageView) rootview.findViewById(R.id.imagePhone);

        int iconResId;
        if (checkUrlCorrection(master.getMasterFb())) {
            iconResId = R.drawable.ic_facebook_fill;
            imageFb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(master.getMasterFb()));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), getString(R.string.error_activity_not_found), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else iconResId = R.drawable.ic_facebook;
        imageFb.setImageDrawable(VectorDrawableCompat.create(getResources(), iconResId, null));
        imageFb.setColorFilter(iconColor);


        if (checkUrlCorrection(master.getMasterVk())) {
            iconResId = R.drawable.ic_vk_fill;
            imageVk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(master.getMasterVk()));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), getString(R.string.error_activity_not_found), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else iconResId = R.drawable.ic_vk;
        imageVk.setImageDrawable(VectorDrawableCompat.create(getResources(), iconResId, null));
        imageVk.setColorFilter(iconColor);


        if (checkUrlCorrection(master.getMasterIg())) {
            iconResId = R.drawable.ic_insta_fill;
            imageInsta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    //need change uri string for correct processing intent by instargam app
                    String instaUri = master.getMasterIg();
                    int pos = instaUri.indexOf("instagram.com");
                    String newUri = instaUri.substring(0, pos) + "instagram.com/_u" + instaUri.substring(pos + 13, instaUri.length());

                    intent.setData(Uri.parse(newUri));
                    intent.setPackage("com.instagram.android");
                    if (isIntentAvailable(intent)) startActivity(intent);
                    else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(master.getMasterIg())));
                        } catch (ActivityNotFoundException ex) {
                            Toast.makeText(getContext(), getString(R.string.error_activity_not_found), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else iconResId = R.drawable.ic_insta;
        imageInsta.setImageDrawable(VectorDrawableCompat.create(getResources(), iconResId, null));
        imageInsta.setColorFilter(iconColor);

        if (checkUrlCorrection(master.getMasterOk())) {
            iconResId = R.drawable.ic_ok_fill;
            imageOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(master.getMasterOk()));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), getString(R.string.error_activity_not_found), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else iconResId = R.drawable.ic_ok;
        imageOk.setImageDrawable(VectorDrawableCompat.create(getResources(), iconResId, null));
        imageOk.setColorFilter(iconColor);

        if (!TextUtils.isEmpty(master.getMasterPhone())) {
            iconResId = R.drawable.ic_phone_fill;
            imagePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + master.getMasterPhone()));
                    startActivity(intent);
                }
            });
        } else iconResId = R.drawable.ic_phone;
        imagePhone.setImageDrawable(VectorDrawableCompat.create(getResources(), iconResId, null));
        imagePhone.setColorFilter(iconColor);
    }

    private boolean isIntentAvailable(Intent intent) {
        final PackageManager packageManager = getContext().getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean checkUrlCorrection(String url) {
        return !TextUtils.isEmpty(url) && url.contains("http");
    }

    private void fillTbs(View rootView) {
        ImageView tbs1View = (ImageView) rootView.findViewById(R.id.image_tbs1);
        ImageView tbs2View = (ImageView) rootView.findViewById(R.id.image_tbs2);
        ImageView tbs3View = (ImageView) rootView.findViewById(R.id.image_tbs3);
        ImageView tbs4View = (ImageView) rootView.findViewById(R.id.image_tbs4);
        tbs1View.setVisibility(View.GONE);
        tbs2View.setVisibility(View.GONE);
        tbs3View.setVisibility(View.GONE);
        tbs4View.setVisibility(View.GONE);

        Integer[] tbsList = master.getTbsArray();
        switch (tbsList.length) {
            case 1:
                loadTbsImage(tbs3View, tbsList[0]);
                break;
            case 2:
                loadTbsImage(tbs3View, tbsList[0]);
                loadTbsImage(tbs1View, tbsList[1]);
                break;
            case 3:
                loadTbsImage(tbs3View, tbsList[0]);
                loadTbsImage(tbs2View, tbsList[1]);
                loadTbsImage(tbs1View, tbsList[2]);
                break;
            case 4:
                loadTbsImage(tbs3View, tbsList[0]);
                loadTbsImage(tbs2View, tbsList[1]);
                loadTbsImage(tbs1View, tbsList[2]);
                loadTbsImage(tbs4View, tbsList[3]);
                break;
        }
    }

    private void loadTbsImage(ImageView tbsImageView, int skillId) {
        Skill skill = RealmHelper.getSkillById(skillId);
        if (skill == null) return;
        Picasso.with(getActivity())
                .load(BuildConfig.SERVER_IMAGE_MASTER_TBS + skill.getPhotoName())
                .into(tbsImageView);
        tbsImageView.setVisibility(View.VISIBLE);
    }
}
