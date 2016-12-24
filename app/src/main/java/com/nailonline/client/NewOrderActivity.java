package com.nailonline.client;

import android.os.Bundle;
import android.widget.TextView;

import com.nailonline.client.entity.Master;
import com.nailonline.client.helper.RealmHelper;

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
        ((TextView)findViewById(R.id.textView)).setText(master.getMasterFirstName());
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
