package com.nailonline.client.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Roman T. on 18.02.2017.
 */

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.bonus_textView)
    public TextView bonusTextView;

    @BindView(R.id.profile_refill)
    public TextView refillTextView;

    @BindView(R.id.profile_bonus_programm)
    public TextView programmTextView;

    @BindView(R.id.profile_change_theme)
    public TextView changeThemeTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        setClickListeners();

        setBonusText();
    }

    private void setBonusText(){
        String bonusString = getResources().getQuantityString(R.plurals.bonus, 0, 0);
        bonusTextView.setText(bonusString);
    }

    private void setClickListeners(){
        refillTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        programmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        changeThemeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UserThemeActivity.class);
                startActivity(intent);
            }
        });
    }
}
