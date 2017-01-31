package com.nailonline.client.order;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.helper.RealmHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Roman T. on 24.12.2016.
 */

public class NewOrderActivity extends BaseActivity {

    public static final String TAG_MASTER_ID = "TAG_MASTER_ID";

    public static final int
            UNIT_PERSON = 1,
            UNIT_NAIL = 2,
            UNIT_HAND = 3;

    private Master master;
    private Skill skill;
    private int numberOfUnits;
    private int currentUnit;

    @BindView(R.id.masterName)
    protected TextView masterName;

    @BindView(R.id.masterAddress)
    protected TextView masterAddress;

    @BindView(R.id.skillLayout)
    protected View skillLayout;

    @BindView(R.id.skillLabel)
    protected TextView skillLabelText;

    @BindView(R.id.price)
    protected TextView skillPriceText;

    @BindView(R.id.handLayout)
    protected View handLayout;
    @BindView(R.id.handText)
    protected TextView handText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_order;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
        ButterKnife.bind(this);
        int masterId = getIntent().getIntExtra(TAG_MASTER_ID, -1);
        if (masterId >= 0) master = RealmHelper.getMasterById(masterId);

        skill = RealmHelper.getSkillById(27);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this)
                .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + master.getMasterPhoto())
                .into((ImageView) findViewById(R.id.photoImageView));
        masterName.setText(master.getMasterFirstName());
        masterAddress.setText(master.getMasterLocation().getAddress());

        skillLabelText.setTextColor(getUserTheme().getParsedMC());
        if (skill == null) {
            skillLabelText.setText(getString(R.string.select_skill));
            skillPriceText.setText("");
        } else {
            fillSkillInfo();
        }
    }

    private void fillSkillInfo() {
        skillLabelText.setText(skill.getLabel());
        switch (skill.getUnitId()) {
            case UNIT_PERSON:
                skillPriceText.setText(getPriceText(1));
                handLayout.setVisibility(View.GONE);
                currentUnit = UNIT_PERSON;
                break;
            case UNIT_NAIL:
                break;
            case UNIT_HAND:
                initHandLayout();
                skillPriceText.setText(getPriceText(numberOfUnits));
                break;
        }
    }

    private String getPriceText(int ratio){
        StringBuilder builder = new StringBuilder();
        builder.append(skill.getDuration() * ratio);
        builder.append(getString(R.string.minutes));
        builder.append(" / ");
        builder.append(skill.getPrice() * ratio);
        builder.append(getString(R.string.roubles));
        return builder.toString();
    }

    private void initHandLayout(){
        if (currentUnit == UNIT_HAND) return;
        handLayout.setVisibility(View.VISIBLE);
        numberOfUnits = 1;
        handText.setText(getString(R.string.one_hand));
        findViewById(R.id.handMinusButton).setEnabled(false);
        ((ImageView)findViewById(R.id.handMinusButton)).setColorFilter(ContextCompat.getColor(this, R.color.just_grey));
        ((ImageView)findViewById(R.id.handPlusButton)).setColorFilter(getUserTheme().getParsedMC());
    }

    @OnClick(R.id.handMinusButton)
    public void onHandMinusClick(){
        numberOfUnits = 1;
        skillPriceText.setText(getPriceText(numberOfUnits));
        handText.setText(getString(R.string.one_hand));
        disableButton((ImageView)findViewById(R.id.handMinusButton));
        ((ImageView)findViewById(R.id.handPlusButton)).setColorFilter(getUserTheme().getParsedMC());
        findViewById(R.id.handPlusButton).setEnabled(true);
    }

    @OnClick(R.id.handPlusButton)
    public void onHandPlusClick(){
        numberOfUnits = 2;
        skillPriceText.setText(getPriceText(numberOfUnits));
        handText.setText(getString(R.string.two_hands));
        enableButton((ImageView)findViewById(R.id.handMinusButton));
        disableButton((ImageView)findViewById(R.id.handPlusButton));
    }

    private void enableButton(ImageView button){
        button.setEnabled(true);
        button.setColorFilter(getUserTheme().getParsedMC());
    }

    private void disableButton(ImageView button){
        button.setEnabled(false);
        button.setColorFilter(ContextCompat.getColor(this, R.color.just_grey));
    }
}
