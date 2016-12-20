package com.nailonline.client.api;

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
        params.put(TOKEN, PrefsHelper.getInstance().getFreeToken());
        return params;
    }


    public void sendRequest(int pMethod, final Map<String, String> pParams,
                            Response.Listener<JSONObject> pRL,
                            Response.ErrorListener pEL) {

        //Logger.d("URL: " + pUrl);   //TODO for debug purposes. Commented in release
        //if (!Utility.checkConnection(pEL)) return;

        String url = buildUrl(pParams);

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
}
