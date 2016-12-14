package com.nailonline.client;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.UserTheme;
import com.nailonline.client.entity.UserThemeWrapper;
import com.nailonline.client.helper.PrefsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (PrefsHelper.getInstance().getFreeToken().isEmpty()) getTokenFromApi();
        else syncronizeData();
    }

    private void getTokenFromApi() {

        ApiVolley.getInstance().getFreeToken(new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    if (token != null && !token.isEmpty()) {
                        PrefsHelper.getInstance().setFreeToken(token);
                        syncronizeData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //TODO process error
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO process error
            }
        });
    }

    private void syncronizeData() {
        ApiVolley.getInstance().getThemes(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                UserThemeWrapper wrapper = new Gson().fromJson(response.toString(), UserThemeWrapper.class);
                List<UserTheme> themeList = wrapper.getList();
                Log.d("Splash", String.valueOf(themeList.size()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
