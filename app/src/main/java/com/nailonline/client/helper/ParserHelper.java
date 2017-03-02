package com.nailonline.client.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nailonline.client.entity.DutyChart;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.MasterLocation;
import com.nailonline.client.entity.Present;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.entity.Region;
import com.nailonline.client.entity.ShortJob;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.entity.SkillsTemplate;
import com.nailonline.client.entity.UserTheme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

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

    public static List<ShortJob> parseShortJobs(JSONObject jsonObject) throws JSONException{
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        Type listType = new TypeToken<List<ShortJob>>() {}.getType();
        List<ShortJob> result = new Gson().fromJson(jsonArray.toString(), listType);
        return result;
    }

    public static void parseAndSaveRegions(JSONObject jsonObject) throws JSONException {
        final JSONArray jsonArray = jsonObject.getJSONArray("Data");
        Realm realm = Realm.getDefaultInstance();
        RealmHelper.clearAllForClass(realm, Region.class);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(Region.class, jsonArray);
            }
        });
        realm.close();
    }
}
