package com.nailonline.client.api;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nailonline.client.App;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Roman T. on 13.12.2016.
 */

public class ApiVolley {

    private static final String
            REQUEST_TAG                     = "REQUEST_TAG";

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

    public void sendRequest(int pMethod, String pUrl, Response.Listener<JSONObject> pRL,
                            Response.ErrorListener pEL) {
        sendRequest(pMethod, pUrl, null, pRL, pEL);
    }

    public void sendRequest(int pMethod, String pUrl, final Map<String, String> pParams,
                            Response.Listener<JSONObject> pRL,
                            Response.ErrorListener pEL) {

        //Logger.d("URL: " + pUrl);   //TODO for debug purposes. Commented in release
        //if (!Utility.checkConnection(pEL)) return;

        JsonObjectRequest request = new JsonObjectRequest(pMethod, pUrl, null, pRL, pEL){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (pParams == null) return super.getParams();
                else return pParams;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 8, // 10sec
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(REQUEST_TAG);

        getRequestQueue().add(request);
    }

    public void getFreeToken(Response.Listener<JSONObject> pRL, Response.ErrorListener pEL){
        sendRequest(POST, "http://185.20.225.17:8080/core/core.php?action=get_free_token", pRL, pEL);
    }
}
