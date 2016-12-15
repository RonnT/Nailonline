package com.nailonline.client.server;

/**
 * Created by Olga Riabkova on 17.10.2016.
 * Cheese
 */

class Parser {
/*
    static String getToken(String response) {
        String res = SettingsMaster.DEFAULT_STRING;

        Log.d(Constants.TAG, "Parser response " + response);

        try {
            JSONObject o = new JSONObject(response);
            if (o.has(Constants.JSON_TOKEN)) {
                res = o.getString(Constants.JSON_TOKEN);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }

    static void parseGetMaster(String response) {

        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject o = new JSONObject(response);
            final JSONObject jsonObjectUser = o.optJSONObject("master");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateObjectFromJson(RealmMaster.class, jsonObjectUser);
                }
            });

            ModelLocation.deleteInfo();
            if (o.has("location")) {
                final JSONObject jsonObjectLocation = o.optJSONObject("location");
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.createOrUpdateObjectFromJson(RealmLocation.class, jsonObjectLocation);
                    }
                });
            }

            ModelDutyChart.deleteInfo();
            if (o.has("dutyChart")) {
                final JSONArray jsonArrayDutyChart = o.optJSONArray("dutyChart");
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.createOrUpdateAllFromJson(RealmDutyChart.class, jsonArrayDutyChart);
                    }
                });
            }

            ModelPresent.deleteInfo();
            if (o.has("presents")) {
                final JSONArray jsonArrayPresent = o.optJSONArray("presents");
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.createOrUpdateAllFromJson(RealmPresent.class, jsonArrayPresent);
                    }
                });
            }

            ModelPromo.deleteInfo();
            if (o.has("promoActions")) {
                final JSONArray jsonArrayPromo = o.optJSONArray("promoActions");
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.createOrUpdateAllFromJson(RealmPromo.class, jsonArrayPromo);
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        realm.close();

    }

    static void parseGetMasterSkills(String response) {

        ModelSkills.deleteInfo();

        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject o = new JSONObject(response);
            final JSONArray jsonObjectUser = o.optJSONArray("list");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateAllFromJson(RealmSkill.class, jsonObjectUser);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        realm.close();

    }

    static void parseGetMasterJobs(String response) {

        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject o = new JSONObject(response);
            final JSONArray jsonObjectJobs = o.optJSONArray("list");

            // удалить те, которых нет в запросе
            ModelJobs.deleteOld(realm, jsonObjectJobs);

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateAllFromJson(RealmJob.class, jsonObjectJobs);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        realm.close();

    }

    static void parseGetMasterUsers(String response) {

        ModelUser.deleteInfo();

        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject o = new JSONObject(response);
            final JSONArray jsonObjectUser = o.optJSONArray("list");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateAllFromJson(RealmUser.class, jsonObjectUser);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        realm.close();

    }

    static void parseSkillsTemplates(String response) {

        ModelTemplate.deleteInfo();

        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject o = new JSONObject(response);
            final JSONArray jsonObjectUser = o.optJSONArray("list");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateAllFromJson(RealmTemplate.class, jsonObjectUser);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        realm.close();

    }

    static void parseRegions(String response) {

        ModelRegion.deleteInfo();

        Realm realm = Realm.getDefaultInstance();
        try {
            JSONObject o = new JSONObject(response);
            final JSONArray jsonArray = o.optJSONArray("Data");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateAllFromJson(RealmRegion.class, jsonArray);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        realm.close();

    }*/
}
