package com.nailonline.client.helper;

import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.entity.UserTheme;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by Roman T. on 16.12.2016.
 */

public class RealmHelper {

    public static void clearAllForClass(final Class<? extends RealmModel> cls) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(cls);
            }
        });
        realm.close();
    }

    public static UserTheme getDefaultTheme() {
        Realm realm = Realm.getDefaultInstance();
        UserTheme result = realm.copyFromRealm(realm.where(UserTheme.class).equalTo("themeDefault", 1).findFirst());
        realm.close();
        return result;
    }

    public static UserTheme getUserTheme(int id) {
        Realm realm = Realm.getDefaultInstance();
        UserTheme result = realm.copyFromRealm(realm.where(UserTheme.class).equalTo("themeId", id).findFirst());
        realm.close();
        return result;
    }

    public static List<UserTheme> getAllThemes() {
        Realm realm = Realm.getDefaultInstance();
        List<UserTheme> result = realm.copyFromRealm(realm.where(UserTheme.class).findAll());
        realm.close();
        return result;
    }

    public static Promo getPromoById(int id){
        Realm realm = Realm.getDefaultInstance();
        Promo result = realm.copyFromRealm(realm.where(Promo.class).equalTo("promoId", id).findFirst());
        realm.close();
        return result;
    }

    public static List<Promo> getAllPromo() {
        Realm realm = Realm.getDefaultInstance();
        List<Promo> result = realm.copyFromRealm(realm.where(Promo.class).findAll());
        realm.close();
        return result;
    }

    public static List<Master> getAllMasters(){
        Realm realm = Realm.getDefaultInstance();
        List<Master> result = realm.copyFromRealm(realm.where(Master.class).findAll());
        realm.close();
        return result;
    }
}
