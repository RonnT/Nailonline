package com.nailonline.client.order;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;
import com.nailonline.client.helper.RealmHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by Roman T. on 24.12.2016.
 */

public class NewOrderActivity extends BaseActivity {

    public static final String TAG_MASTER_ID = "TAG_MASTER_ID";

    private Master master;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_order;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
        int masterId = getIntent().getIntExtra(TAG_MASTER_ID, -1);
        if (masterId >= 0) master = RealmHelper.getMasterById(masterId);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this)
                .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + master.getMasterPhoto())
                .into((ImageView) findViewById(R.id.photoImageView));
        ((TextView)findViewById(R.id.masterName)).setText(master.getMasterFirstName());
        ((TextView)findViewById(R.id.masterAddress)).setText(master.getMasterLocation().getAddress());

    }
}
