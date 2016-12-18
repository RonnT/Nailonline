package com.nailonline.client.helper;

import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Promo;
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
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(Master.class, jsonArray);
            }
        });
        realm.close();
    }
}
