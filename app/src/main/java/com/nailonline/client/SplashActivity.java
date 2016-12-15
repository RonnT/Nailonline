package com.nailonline.client;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.UserTheme;
import com.nailonline.client.helper.ParserHelper;
import com.nailonline.client.helper.PrefsHelper;
import com.nailonline.client.helper.RealmHelper;
import com.nailonline.client.server.ErrorHttp;
import com.nailonline.client.server.ResponseHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

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

                Log.d(TAG, response.toString());
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_master_skills error " + responseHttp.getError().getType());
                        Log.d(TAG, "get_master_skills content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                            RealmHelper.clearAllForClass(UserTheme.class);
                        }
                        return;
                    }

                    try {
                        ParserHelper.parseAndSaveThemes(response);
                    } catch (JSONException e) {
                        //TODO make error
                        e.printStackTrace();
                    }
//                            context.sendBroadcast(new Intent(Constants.INTENT_GET_MASTER));
                }

                /*UserThemeWrapper wrapper = new Gson().fromJson(response.toString(), UserThemeWrapper.class);
                List<UserTheme> themeList = wrapper.getList();
                Log.d("Splash", String.valueOf(themeList.size()));*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void onButtonClick(View view){
        List<UserTheme> list = RealmHelper.getAllThemes();
        for (UserTheme theme : list){
            Log.d(TAG, theme.toString());
        }
    }
}
