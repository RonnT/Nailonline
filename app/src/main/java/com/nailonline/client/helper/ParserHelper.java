package com.nailonline.client.helper;

import com.nailonline.client.entity.DutyChart;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.MasterLocation;
import com.nailonline.client.entity.Present;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.entity.SkillsTemplate;
import com.nailonline.client.entity.UserTheme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * Created by Roman T. on 15.12.2016.
 */

public class ParserHelper {

    public static void parseAndSaveThemes(final JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("list");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, UserTheme.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(UserTheme.class, jsonArray);
            }
        });
        realm.close();
    }

    public static void parseAndSavePromo(final JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("list");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, Promo.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(Promo.class, jsonArray);
            }
        });
        realm.close();
    }

    public static void parseAndSaveMasters(final JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("list");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, Master.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(Master.class, jsonArray);
            }
        });
        realm.close();
    }

    public static void parseAndSaveMasterLocations(final JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("list");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, MasterLocation.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(MasterLocation.class, jsonArray);
            }
        });
        realm.close();
    }

    public static void parseAndSaveSkills(final JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("list");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, Skill.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(Skill.class, jsonArray);
            }
        });
        realm.close();
    }

    public static void parseAndSaveSkillsTemplates(final JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("list");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, SkillsTemplate.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(SkillsTemplate.class, jsonArray);
            }
        });
        realm.close();
    }

    public static void parseAndSavePresents(final JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("list");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, Present.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(Present.class, jsonArray);
            }
        });
        realm.close();
    }

    public static void parseAndSaveDuties(final JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("list");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, DutyChart.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(DutyChart.class, jsonArray);
            }
        });
        realm.close();
    }

/*
    public static void parseAndSaveRegions(JSONObject jsonObject){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Region realmRegion = realm.createObject(Region.class);
                realmRegion.seteRegionId(object.getInt("eRegionId"));
                realmRegion.seteRegionLabel(object.getString("eRegionLabel"));
                realmRegion.seteRegionBelt(object.getInt("eRegionBelt"));

               JSONArray boundsArray = object.getJSONArray("eRegionBounds");
                List<Region.Coords> latLngList = new ArrayList<>();
                for (int j = 0; j < boundsArray.length(); j++){
                    JSONArray coordsArray = boundsArray.getJSONArray(j);
                    double lat = coordsArray.getDouble(0);
                    double lng = coordsArray.getDouble(1);

                    latLngList.add(new Region.Coords(lat, lng));
                }
                realmRegion.seteRegionBounds(latLngList);
            }
        } catch (JSONException e){
            e.printStackTrace();
        } finally {
            realm.cancelTransaction();
        }
    }*/
}
