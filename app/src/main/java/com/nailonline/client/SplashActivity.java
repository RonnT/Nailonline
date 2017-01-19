package com.nailonline.client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nailonline.client.api.ApiVolley;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.MasterLocation;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.entity.Skill;
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
    private boolean isLocationsReady;
    private boolean isSkillsReady;
    private boolean isRegionsReady = true;

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
        syncronizeMasterLocations();
        syncronizeSkills();
        syncronizeRegions();
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

    private void syncronizeRegions(){
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

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
            }
        };
        asyncTask.execute();

        /*
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
        });*/
    }

    private void checkSyncronizeFinish(){
        if (isPromoReady &&
                isThemesReady &&
                isMastersReady &&
                isLocationsReady &&
                isSkillsReady &&
                isRegionsReady){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
