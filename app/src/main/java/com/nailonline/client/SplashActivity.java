package com.nailonline.client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.DutyChart;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.MasterLocation;
import com.nailonline.client.entity.Present;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.entity.SkillsTemplate;
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
    public static final String FROM_PUSH = "FROM_PUSH";

    private boolean isThemesReady;
    private boolean isPromoReady;
    private boolean isMastersReady;
    private boolean isLocationsReady;
    private boolean isSkillsReady;
    private boolean isSkillsTemplatesReady;
    private boolean isPresentsReady;
    private boolean isDutiesReady;
    private boolean isRegionsReady = false;

    private boolean fromPush = false;

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
        fromPush = getIntent().getBooleanExtra(FROM_PUSH, false);
        if (PrefsHelper.getInstance().getToken().isEmpty()) getTokenFromApi();
        else {
            syncronizeData();
        }
        String pushToken = FirebaseInstanceId.getInstance().getToken();
        if (!PrefsHelper.getInstance().getUserToken().isEmpty() &&
                pushToken != null &&
                !pushToken.equals(PrefsHelper.getInstance().getPushToken())){
            ApiVolley.getInstance().bindPushToken(pushToken);
        }
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
        syncronizeMasterLocations();
        syncronizeSkills();
        syncronizeRegions();
        syncronizeSkillsTemplates();
        syncronizePresents();
        syncronizeDuties();
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

                        Log.d(TAG, "get_all_masters error " + responseHttp.getError().getType());
                        Log.d(TAG, "get_all_masters content " + responseHttp.getError().getMessage());

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

    private void syncronizeMasterLocations(){
        ApiVolley.getInstance().getAllMasterLocations(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isLocationsReady = true;
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_all_locations " + responseHttp.getError().getType());
                        Log.d(TAG, "get_all_locations content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(MasterLocation.class);
                        }
                        return;
                    }
                    try {
                        ParserHelper.parseAndSaveMasterLocations(response);
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
                isLocationsReady = true;
                //TODO make error
            }
        });
    }

    private void syncronizeSkills(){
        ApiVolley.getInstance().getAllSkills(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isSkillsReady = true;
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_all_skills " + responseHttp.getError().getType());
                        Log.d(TAG, "get_all_skills content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(Skill.class);
                        }
                        return;
                    }
                    try {
                        ParserHelper.parseAndSaveSkills(response);
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
                isSkillsReady = true;
                //TODO make error
            }
        });
    }

    private void syncronizeSkillsTemplates(){
        ApiVolley.getInstance().getSkillsTemplates(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isSkillsTemplatesReady = true;
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_skills_templates " + responseHttp.getError().getType());
                        Log.d(TAG, "get_skills_templates content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(SkillsTemplate.class);
                        }
                        return;
                    }
                    try {
                        ParserHelper.parseAndSaveSkillsTemplates(response);
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
                isSkillsTemplatesReady = true;
                //TODO make error
            }
        });
    }

    private void syncronizePresents(){
        ApiVolley.getInstance().getAllPresents(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isPresentsReady = true;
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_all_presents " + responseHttp.getError().getType());
                        Log.d(TAG, "get_all_presents content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(Present.class);
                        }
                        return;
                    }
                    try {
                        ParserHelper.parseAndSavePresents(response);
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
                isPresentsReady = true;
                //TODO make error
            }
        });
    }

    private void syncronizeDuties(){
        ApiVolley.getInstance().getAllDuties(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                isDutiesReady = true;
                ResponseHttp responseHttp = new ResponseHttp(response);
                if (responseHttp != null) {
                    if (responseHttp.getError() != null) {

                        Log.d(TAG, "get_all_duties " + responseHttp.getError().getType());
                        Log.d(TAG, "get_all_duties content " + responseHttp.getError().getMessage());

                        if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) { //TODO уточнить с ошибкой
                            RealmHelper.clearAllForClass(DutyChart.class);
                        }
                        return;
                    }
                    try {
                        ParserHelper.parseAndSaveDuties(response);
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
                isDutiesReady = true;
                //TODO make error
            }
        });
    }

    private void syncronizeRegions(){
        ApiVolley.getInstance().getRegions(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ParserHelper.parseAndSaveRegions(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                isRegionsReady = true;
                checkSyncronizeFinish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                isRegionsReady = true;
                checkSyncronizeFinish();
            }
        });

/*
        AsyncTask<Void, String, JSONObject> asyncTask = new AsyncTask<Void, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(BuildConfig.SERVER_REGIONS_JSON);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line+"\n");
                        Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                    }
                    return new JSONObject(buffer.toString());

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);
                ParserHelper.parseAndSaveRegions(jsonObject);
                isRegionsReady = true;
            }
        };
        asyncTask.execute();*/
    }

    private void checkSyncronizeFinish(){
        if (isPromoReady &&
                isThemesReady &&
                isMastersReady &&
                isLocationsReady &&
                isSkillsReady &&
                isRegionsReady &&
                isSkillsTemplatesReady &&
                isPresentsReady &&
                isDutiesReady){
            finishActivity();
        }
    }

    private void finishActivity(){
        if (fromPush) finish();
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
