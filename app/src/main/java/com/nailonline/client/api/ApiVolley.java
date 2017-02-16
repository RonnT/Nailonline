package com.nailonline.client.api;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nailonline.client.App;
import com.nailonline.client.helper.PrefsHelper;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman T. on 13.12.2016.
 */

public class ApiVolley {

    //TODO to Build.Config
    private static final String API_URL = "http://185.20.225.17:8080/core/core.php";

    private static final String
            ACTION = "action",
            TOKEN = "token",
            MASTER_ID = "masterId",
            START_DATE = "startDate",
            END_DATE = "endDate",
            PHONE = "phone",
            CODE = "code",
            SKILL_ID = "skillId",
            AMOUNT = "amount",
            LOCATION_ID = "locationId",
            COMMENTS = "comments",
            STATE_ID = "stateId",
            LIMIT = "limit",
            FROM_DATE = "fromDate",
            TO_DATE = "toDate",

    REQUEST_TAG = "REQUEST_TAG";

    private static final int POST = Request.Method.POST;

    private RequestQueue mRequestQueue;

    public void stopRequest() {
        if (null != mRequestQueue) mRequestQueue.cancelAll(REQUEST_TAG);
    }

    private static class SingletonHolder {
        public static final ApiVolley INSTANCE = new ApiVolley();
    }

    public static ApiVolley getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        setupRequestQueueIfNeed();
        return mRequestQueue;
    }

    private void setupRequestQueueIfNeed() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(App.getAppContext());
        }
    }

    private String buildUrl(Map<String, String> param) {
        return API_URL + "?" + getParamStr(param);
    }

    private String getParamStr(Map<String, String> p) {
        String paramStr = "";

        if (p != null) {
            for (String key : p.keySet()) {
                try {
                    paramStr = paramStr + "&" + key + "=" +
                            URLEncoder.encode(p.get(key), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return paramStr;
    }

    private Map<String, String> getDefaultParams(String action) {
        Map<String, String> params = new HashMap<>();
        params.put(ACTION, action);
        //TODO if token is empty ?
        params.put(TOKEN, PrefsHelper.getInstance().getToken());
        return params;
    }


    public void sendRequest(int pMethod, final Map<String, String> pParams,
                            Response.Listener<JSONObject> pRL,
                            Response.ErrorListener pEL) {

        //Logger.d("URL: " + pUrl);   //TODO for debug purposes. Commented in release
        //if (!Utility.checkConnection(pEL)) return;

        String url = buildUrl(pParams);
        Log.d("APIRequest", pParams.toString());

        JsonObjectRequest request = new JsonObjectRequest(pMethod, url, null, pRL, pEL);

        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 8, // 10sec
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(REQUEST_TAG);

        getRequestQueue().add(request);
    }

    public void getFreeToken(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        Map<String, String> params = new HashMap<>();
        params.put(ACTION, "get_free_token");
        sendRequest(POST, params, pRL, pEL);
    }

    public void getThemes(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        sendRequest(POST, getDefaultParams("get_themes"), pRL, pEL);
    }

    public void getAllSliderPromo(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        sendRequest(POST, getDefaultParams("get_all_sliderPromo"), pRL, pEL);
    }

    public void getAllMasters(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        sendRequest(POST, getDefaultParams("get_all_masters"), pRL, pEL);
    }

    public void getAllMasterLocations(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        sendRequest(POST, getDefaultParams("get_all_locations"), pRL, pEL);
    }

    public void getAllSkills(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        sendRequest(POST, getDefaultParams("get_all_skills"), pRL, pEL);
    }

    public void getSkillsTemplates(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        sendRequest(POST, getDefaultParams("get_skills_templates"), pRL, pEL);
    }

    public void getAllPresents(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        sendRequest(POST, getDefaultParams("get_all_presents"), pRL, pEL);
    }

    public void getAllDuties(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        sendRequest(POST, getDefaultParams("get_all_duties"), pRL, pEL);
    }

    public void getMasterShortJobs(int masterId, long startDate, long endDate, Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        Map<String, String> params = getDefaultParams("get_master_shortJobs");
        params.put(MASTER_ID, String.valueOf(masterId));
        params.put(START_DATE, String.valueOf(startDate));
        params.put(END_DATE, String.valueOf(endDate));
        sendRequest(POST, params, pRL, pEL);
    }

    public void getUserCode(String phone, Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        Map<String, String> params = getDefaultParams("get_user_code");
        params.put(PHONE, phone);
        sendRequest(POST, params, pRL, pEL);
    }

    public void getUserToken(String phone, String code, Response.Listener<JSONObject> pRL, Response.ErrorListener pEL) {
        Map<String, String> params = getDefaultParams("get_user_token");
        params.put(PHONE, phone);
        params.put(CODE, code);
        sendRequest(POST, params, pRL, pEL);
    }

    public void addJob(int masterId, int skillId, int amount, int locationId, long startDate,
                       String comments, Response.Listener<JSONObject> pRL, Response.ErrorListener pEL){
        Map<String, String> params = getDefaultParams("add_job");
        params.put(MASTER_ID, String.valueOf(masterId));
        params.put(SKILL_ID, String.valueOf(skillId));
        params.put(AMOUNT, String.valueOf(amount));
        params.put(LOCATION_ID, String.valueOf(locationId));
        params.put(START_DATE, String.valueOf(startDate));
        params.put(COMMENTS, comments);
        sendRequest(POST, params, pRL, pEL);
    }

    public void getUserJobs(int stateId, int limit, Response.Listener<JSONObject> pRL, Response.ErrorListener pEL){
        Map<String, String> params = getDefaultParams("get_user_jobs");
        params.put(STATE_ID, String.valueOf(stateId));
        params.put(LIMIT, String.valueOf(limit));
        params.put(FROM_DATE, "0");
        params.put(TO_DATE, "1700000000");
        sendRequest(POST, params, pRL, pEL);
    }
}
