package com.nailonline.client.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.nailonline.client.BaseActivity;
import com.nailonline.client.R;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

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

        ApiVolley.getInstance().getUser(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject userJson = null;
                try {
                    userJson = response.getJSONObject("user");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (userJson != null){
                    User user = new Gson().fromJson(userJson.toString(), User.class);
                    int bonus = user.getUserBalance();
                    String bonusString = getResources().getQuantityString(R.plurals.bonus, bonus, bonus);
                    bonusTextView.setText(bonusString);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
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
