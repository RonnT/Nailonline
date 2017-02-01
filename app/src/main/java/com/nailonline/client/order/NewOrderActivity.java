package com.nailonline.client.order;

import android.content.Intent;
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
    public static final String TAG_SKILL_ID = "TAG_SKILL_ID";

    public static final int REQUEST_CODE_SKILL = 111;

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

    @BindView(R.id.skillLabel)
    protected TextView skillLabelText;

    @BindView(R.id.price)
    protected TextView skillPriceText;

    @BindView(R.id.additionalLayout)
    protected View additionalLayout;
    @BindView(R.id.unitNumberText)
    protected TextView unitNumberText;

    @BindView(R.id.minusButton)
    protected ImageView minusButton;
    @BindView(R.id.plusButton)
    protected ImageView plusButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_order;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int masterId = getIntent().getIntExtra(TAG_MASTER_ID, -1);
        if (masterId >= 0) master = RealmHelper.getMasterById(masterId);
        else {
            //TODO exit with error
        }
        int skillId = getIntent().getIntExtra(TAG_SKILL_ID, -1);
        if (skillId > -1) skill = RealmHelper.getSkillById(skillId);

        Picasso.with(this)
                .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + master.getMasterPhoto())
                .into((ImageView) findViewById(R.id.photoImageView));
        masterName.setText(master.getMasterFirstName());
        masterAddress.setText(master.getMasterLocation().getAddress());

        skillLabelText.setTextColor(getUserTheme().getParsedMC());
        if (skill == null) {
            skillLabelText.setText(getString(R.string.select_skill));
            skillPriceText.setVisibility(View.GONE);
        } else {
            updateSkillInfo();
        }
    }

    private void updateSkillInfo() {
        skillLabelText.setText(skill.getLabel());
        skillPriceText.setVisibility(View.VISIBLE);
        switch (skill.getUnitId()) {
            case UNIT_PERSON:
                if (currentUnit == UNIT_PERSON) break;
                numberOfUnits = 1;
                additionalLayout.setVisibility(View.GONE);
                currentUnit = UNIT_PERSON;
                break;
            case UNIT_NAIL:
                initAdditionalLayout(UNIT_NAIL);
                break;
            case UNIT_HAND:
                initAdditionalLayout(UNIT_HAND);
                break;
        }
        skillPriceText.setText(getPriceText(numberOfUnits));
    }

    private void initAdditionalLayout(int unit){
        if (currentUnit != unit) {
            numberOfUnits = 1;
            currentUnit = unit;
        }
        additionalLayout.setVisibility(View.VISIBLE);
        String unitNumberString = "";
        switch (unit){
            case UNIT_HAND:
                unitNumberString = getResources().getQuantityString(R.plurals.numberOfHands, numberOfUnits, numberOfUnits);
                break;
            case UNIT_NAIL:
                unitNumberString = getResources().getQuantityString(R.plurals.numberOfNail, numberOfUnits, numberOfUnits);
        }
        unitNumberText.setText(unitNumberString);
        updateButtons();
    }

    private void updateButtons(){
        int minNumber = 1;
        int maxNumber = currentUnit == UNIT_HAND ? 2 : 10;

        if (numberOfUnits == minNumber){
            minusButton.setEnabled(false);
            plusButton.setEnabled(true);
            minusButton.setColorFilter(ContextCompat.getColor(this, R.color.just_grey));
            plusButton.setColorFilter(getUserTheme().getParsedMC());
        } else if (numberOfUnits == maxNumber){
            plusButton.setEnabled(false);
            minusButton.setEnabled(true);
            plusButton.setColorFilter(ContextCompat.getColor(this, R.color.just_grey));
            minusButton.setColorFilter(getUserTheme().getParsedMC());
        } else {
            plusButton.setEnabled(true);
            minusButton.setEnabled(true);
            plusButton.setColorFilter(getUserTheme().getParsedMC());
            minusButton.setColorFilter(getUserTheme().getParsedMC());
        }
    }

    private String getPriceText(int ratio){
        StringBuilder builder = new StringBuilder();
        builder.append(getString(R.string.minutes, skill.getDuration() * ratio));
        builder.append(" / ");
        builder.append(getString(R.string.roubles, skill.getPrice() * ratio));
        return builder.toString();
    }

    @OnClick(R.id.minusButton)
    public void onMinusClick(){
        numberOfUnits--;
        updateSkillInfo();
    }

    @OnClick(R.id.plusButton)
    public void onPlusClick(){
        numberOfUnits++;
        updateSkillInfo();
    }

    @OnClick(R.id.skillLayout)
    public void onSelectSkillClick(){
        Intent intent = new Intent(this, SelectSkillActivity.class);
        intent.putExtra(TAG_MASTER_ID, master.getMasterId());
        startActivityForResult(intent, REQUEST_CODE_SKILL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_SKILL:
                    int newSkillId = data.getIntExtra(TAG_SKILL_ID, -1);
                    if (newSkillId > -1) skill = RealmHelper.getSkillById(newSkillId);
                    updateSkillInfo();
            }
        }
    }
}
