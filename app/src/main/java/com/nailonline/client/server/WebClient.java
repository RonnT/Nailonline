package com.nailonline.client.server;

/**
 * Created by Olga Riabkova on 17.10.2016.
 * Cheese
 */

public class WebClient {}
    /*
    private static final String TAG = "WebClient";
    
    private static String RESPONSE_IS_NULL = "Response is null";

    public static void getCode(final String phone, final WebCallback callback) {
        Log.d(TAG, "HTTP get_master_code");

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);

                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {
                                callback.onResult(false, responseHttp.getError().getMessage());
                                return;
                            }

                            callback.onResult(true, response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_master_code");
                params.put("phone", phone);
                showParam(params);
                return params;
            }
        };

        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void getToken(final Context context, final String phone, final String code, final WebCallback callback) {
        Log.d(TAG, "HTTP get_master_token");

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);

                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.INCORRECT)) {

                                }

                                callback.onResult(false, responseHttp.getError().getMessage());
                                return;
                            }

                            SettingsMaster.setString(
                                    context,
                                    SettingsMaster.PREF_USER_TOKEN,
                                    Parser.getToken(response));
                            SettingsMaster.setString(context, SettingsMaster.PREF_PHONE, SettingsMaster.DEFAULT_STRING);
                            SettingsMaster.setString(context, SettingsMaster.PREF_PHONE_GET_CODE, SettingsMaster.DEFAULT_STRING);
//                            SharedPrefs.addNewBonuses(context, 100);
                            callback.onResult(true, response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_master_token");
                params.put("phone", phone);
                params.put("code", code);
                showParam(params);
                return params;
            }
        };

        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void bindPushToken(final Context context, final String pushToken) {
        Log.d(TAG, "HTTP bind_push_token");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);

                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {

                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.INCORRECT)) {

                                }
                                return;
                            }

                            SettingsMaster.setString(context, SettingsMaster.PREF_PUSH_TOKEN, pushToken);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "bind_push_token");
                params.put("token", token);
                params.put("push_token", pushToken);
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void getMaster(final Context context) {
        Log.d(TAG, "HTTP get_master");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {

                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

                            Parser.parseGetMaster(response);
                            context.sendBroadcast(new Intent(Constants.INTENT_GET_MASTER));
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_master");
                params.put("token", token);
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void getMaster(final Context context, final WebCallback callback) {
        Log.d(TAG, "HTTP get_master");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                callback.onResult(false, responseHttp.getError().getMessage());
                                return;
                            }

                            Parser.parseGetMaster(response);
                            context.sendBroadcast(new Intent(Constants.INTENT_GET_MASTER));
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_master");
                params.put("token", token);
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void getMasterSkills(final Context context) {
        Log.d(TAG, "HTTP get_master_skills");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {

                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "get_master_skills error " + responseHttp.getError().getType());
                                Log.d(TAG, "get_master_skills content " + responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

                            Parser.parseGetMasterSkills(response);
//                            context.sendBroadcast(new Intent(Constants.INTENT_GET_MASTER));
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_master_skills");
                params.put("token", token);
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void getMasterJobs(final Context context, final WebCallback callback) {
        Log.d(TAG, "HTTP get_master_jobs");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            if (callback != null)
                                callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "get_master_jobs error " + responseHttp.getError().getType());
                                Log.d(TAG, "get_master_jobs content " + responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                if (callback != null)
                                    callback.onResult(false, responseHttp.getError().getMessage());
                                return;
                            }

                            ModelJobs.deleteInfo();
                            Parser.parseGetMasterJobs(response);
                            context.sendBroadcast(new Intent(Constants.INTENT_GET_MASTER_JOBS));
                            context.sendBroadcast(new Intent(Constants.UPDATE_CONTENT));
                            if (callback != null)
                                callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        if (callback != null)
                            callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_master_jobs");
                params.put("token", token);
                params.put("fromDate", "0");
                params.put("toDate", "0");
                params.put("stateId", "0");
                params.put("limit", "0");
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void getMasterUsers(final Context context) {
        Log.d(TAG, "HTTP get_all_users");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {

                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "get_all_users error " + responseHttp.getError().getType());
                                Log.d(TAG, "get_all_users content " + responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

                            Parser.parseGetMasterUsers(response);
                            context.sendBroadcast(new Intent(Constants.INTENT_GET_MASTER_JOBS));
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_all_users");
                params.put("token", token);
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void updateMaster(final Context context, final WebCallback callback,
                                    final String name, final String birthday,
                                    final int masterGender, final int masterMainLocationId,
                                    final String mail, final String masterVk,
                                    final String masterFb, final String masterOk,
                                    final String masterIg) {

        Log.d(TAG, "HTTP update_master");


        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "update_master");
                params.put("token", token);
                params.put("name", name);
                params.put("birthday", birthday);
                params.put("gender", String.valueOf(masterGender));
                params.put("masterMainLocationId", String.valueOf(masterMainLocationId));
                params.put("mail", mail);
                params.put("masterVk", masterVk);
                params.put("masterFb", masterFb);
                params.put("masterOk", masterOk);
                params.put("masterIg", masterIg);
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void getAddressGeocode(final Context context, final WebCallback callback,
                                         String address) {

        Log.d(TAG, "HTTP get_address_geocode");
        address = address + ",RU";
        address = address.replaceAll(" ", "%20");

        String uri = String.format("http://maps.google.com/maps/api/geocode/json?address=%1$s",
                address);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        MasterApp.getInstance().addToRequestQueue(jsObjRequest);
        Log.d(TAG, "strRequest " + jsObjRequest.getUrl());


    }

    public static void getRegions() {

        Log.d(TAG, "HTTP get_regions");

        String uri = "http://185.20.225.17:8080/images/eRegions.json";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Parser.parseRegions(response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MasterApp.getInstance().addToRequestQueue(jsObjRequest);
        Log.d(TAG, "strRequest " + jsObjRequest.getUrl());

    }

    public static void getSkillsTemplates() {

        Log.d(TAG, "HTTP get_skills_templates");

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {

                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                return;
                            }

                            Parser.parseSkillsTemplates(response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_skills_templates");
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);

    }

    public static void addMasterLocation(final Context context, final WebCallback callback,
                                         final String label, final String address,
                                         final int regionId, final double lat,
                                         final double lng, final String comments) {

        Log.d(TAG, "HTTP add_master_location");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "add_master_location");
                params.put("token", token);
                params.put("label", label);
                params.put("address", address);
                params.put("eRegionId", String.valueOf(regionId));
                params.put("lat", String.valueOf(lat));
                params.put("lng", String.valueOf(lng));
                params.put("comments", comments);
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void addMasterSkill(final Context context, final WebCallback callback,
                                      final Integer templateId, final String label,
                                      final String duration, final String userBonus,
                                      final int unitId, final String price, final Integer presentId) {

        Log.d(TAG, "HTTP add_master_skill");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "add_master_skill");
                params.put("token", token);
                if (templateId != null)
                    params.put("templateId", String.valueOf(templateId));
                params.put("label", label);
                params.put("duration", duration);
                params.put("userBonus", userBonus);
                params.put("unitId", String.valueOf(unitId));
                params.put("price", price);
                if (presentId != null)
                    params.put("presentId", String.valueOf(presentId));
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void addMasterDuty(final Context context, final WebCallback callback,
                                     final Integer id,
                                     final String type, final String days,
                                     final long startDate, final long finishDate) {

        Log.d(TAG, "HTTP add_master_duty");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }

                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "add_master_duty");
                params.put("token", token);
                if (id == null)
                    params.put("dutyId", "0");
                else
                    params.put("dutyId", String.valueOf(id));
                params.put("type", type);
                params.put("days", days);
                params.put("startDate", String.valueOf(startDate));
                params.put("finishDate", String.valueOf(finishDate));
                params.put("order", String.valueOf(1));
                params.put("isOn", String.valueOf(1));
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void addMasterPresent(final Context context, final WebCallback callback,
                                        final String label, final String photoBase64,
                                        final String ext, final int enable) {

        Log.d(TAG, "HTTP add_master_present");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }

                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new Hashtable<>();
                params.put("action", "add_master_present");
                params.put("token", token);
                params.put("label", label);
                params.put("photo", photoBase64);
                params.put("ext", ext);
                params.put("enable", String.valueOf(enable));
                showParam(params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void updateMasterPresent(final Context context, final WebCallback callback,
                                           final int presentId, final String label, final String photoBase64,
                                           final String ext, final int enable) {

        Log.d(TAG, "HTTP update_master_present");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }

                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "update_master_present");
                params.put("token", token);
                params.put("presentId", String.valueOf(presentId));
                params.put("label", label);
                params.put("photo", photoBase64);
                params.put("ext", ext);
                params.put("enable", String.valueOf(enable));
                showParam(params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void addMasterPromo(final Context context, final WebCallback callback,
                                      final String label, final String description,
                                      final long startDate, final long endDate,
                                      final String photoBase64, final String ext) {

        Log.d(TAG, "HTTP add_master_promo");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }

                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new Hashtable<>();
                params.put("action", "add_master_promo");
                params.put("token", token);
                params.put("label", label);
                params.put("body", description);
                params.put("startDate", String.valueOf(startDate));
                params.put("endDate", String.valueOf(endDate));
                params.put("photo", photoBase64);
                params.put("ext", ext);
                showParam(params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void updateMasterPromo(final Context context, final WebCallback callback,
                                         final int promoId,
                                         final String label, final String description,
                                         final long startDate, final long endDate,
                                         final String photoBase64, final String ext) {

        Log.d(TAG, "HTTP add_master_promo");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }

                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "add_master_promo");
                params.put("token", token);
                params.put("promoId", String.valueOf(promoId));
                params.put("label", label);
                params.put("body", description);
                params.put("startDate", String.valueOf(startDate));
                params.put("endDate", String.valueOf(endDate));
                params.put("photo", photoBase64);
                params.put("ext", ext);
                showParam(params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void updateMasterSkill(final Context context, final WebCallback callback,
                                         final int skillId,
                                         final Integer templateId, final String label,
                                         final String duration, final String userBonus,
                                         final int unitId, final String price, final Integer presentId) {

        Log.d(TAG, "HTTP update_master_skill");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "update_master_skill");
                params.put("token", token);
                if (templateId != null)
                    params.put("templateId", String.valueOf(templateId));
                params.put("skillId", String.valueOf(skillId));
                params.put("label", label);
                params.put("duration", duration);
                params.put("userBonus", userBonus);
                params.put("unitId", String.valueOf(unitId));
                params.put("price", price);
                if (presentId != null)
                    params.put("presentId", String.valueOf(presentId));
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void setJobState(final Context context, final WebCallback callback,
                                   final int jobId, final int jobStateId, final long startDate) {

        Log.d(TAG, "HTTP set_job_state");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }

                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "set_job_state");
                params.put("token", token);
                params.put("jobId", String.valueOf(jobId));
                params.put("jobStateId", String.valueOf(jobStateId));
                params.put("startDate", String.valueOf(startDate));
                params.put("comments", "");

                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void sendPromoSlider(final Context context, final WebCallback callback, final int promoId) {

        Log.d(TAG, "HTTP send_promo_slider");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "send_promo_slider");
                params.put("token", token);
                params.put("promoId", String.valueOf(promoId));
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    public static void revokeMasterPromo(final Context context, final WebCallback callback, final int promoId) {

        Log.d(TAG, "HTTP revoke_master_promo");

        final String token = SettingsMaster.getString(context, SettingsMaster.PREF_USER_TOKEN);

        StringRequest strRequest = new StringRequest(Request.Method.POST, Api.API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ResponseHttp responseHttp = getResponse(response);
                        if (responseHttp == null) {
                            callback.onResult(false, RESPONSE_IS_NULL);
                        } else {
                            if (responseHttp.getError() != null) {

                                Log.d(TAG, "error " + responseHttp.getError().getType());
                                Log.d(TAG, "content " + responseHttp.getError().getMessage());

                                callback.onResult(false, responseHttp.getError().getMessage());

                                if (responseHttp.getError().getType().equals(ErrorHttp.ErrorType.ENTITY_NOT_FOUND)) {
                                    context.sendBroadcast(new Intent(Constants.ACCOUNT_CHANGED));

                                    ModelMaster.deleteAllInfo(context);
                                }
                                return;
                            }

//                            Parser.parseGetMaster(response);
                            callback.onResult(true, response);
                        }
                    }
                }

                ,
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        callback.onResult(false, error.getMessage());
                    }
                }
        )

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "revoke_master_promo");
                params.put("token", token);
                params.put("promoId", String.valueOf(promoId));
                showParam(params);
                return params;
            }
        };

        MasterApp.getInstance().addToRequestQueue(strRequest);
    }

    private static ResponseHttp getResponse(String rawStr) {
        return new ResponseHttp(rawStr);
    }

    private static void showParam(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.d(TAG, entry.getKey() + " " + entry.getValue());
        }
    }
}
*/