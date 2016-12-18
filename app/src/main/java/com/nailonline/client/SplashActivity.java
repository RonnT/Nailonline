package com.nailonline.client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.entity.UserTheme;
import com.nailonline.client.helper.ParserHelper;
import com.nailonline.client.helper.PrefsHelper;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.server.ErrorHttp;
import com.nailonline.client.server.ResponseHttp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    private boolean isThemesReady;
    private boolean isPromoReady;
    private boolean isMastersReady;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initTheme() {
        //don't need to init
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        super.setData(savedInstanceState);
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
        syncronizeThemes();
        syncronizePromo();
        syncronizeMasters();
    }

    private void syncronizeThemes(){
        ApiVolley.getInstance().getThemes(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isThemesReady = true;
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_master_skills error " + responseHttp.getError().getType());
                        Log.d(TAG, "get_master_skills content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(UserTheme.class);
                        }
                        return;
                    }

                    try {
                        ParserHelper.parseAndSaveThemes(response);
                        checkSyncronizeFinish();
                    } catch (JSONException e) {
                        //TODO make error
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isThemesReady = true;
                //TODO make error
            }
        });
    }

    private void syncronizePromo(){
        ApiVolley.getInstance().getAllSliderPromo(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isPromoReady = true;
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_all_sliderPromo error " + responseHttp.getError().getType());
                        Log.d(TAG, "get_all_sliderPromo content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(Promo.class);
                        }
                        return;
                    }

                    try {
                        ParserHelper.parseAndSavePromo(response);
                        checkSyncronizeFinish();
                    } catch (JSONException e) {
                        //TODO make error
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isPromoReady = true;
                //TODO make error
            }
        });
    }

    private void syncronizeMasters(){
        ApiVolley.getInstance().getAllMasters(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isMastersReady = true;
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_all_sliderPromo error " + responseHttp.getError().getType());
                        Log.d(TAG, "get_all_sliderPromo content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(Master.class);
                        }
                        return;
                    }

                    try {
                        ParserHelper.parseAndSaveMasters(response);
                        checkSyncronizeFinish();
                    } catch (JSONException e) {
                        //TODO make error
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isMastersReady = true;
                //TODO make error
            }
        });
    }

    private void checkSyncronizeFinish(){
        if (isPromoReady && isThemesReady && isMastersReady){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
