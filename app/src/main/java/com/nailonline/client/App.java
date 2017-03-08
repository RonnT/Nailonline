package com.nailonline.client;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class App extends Application{

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics crashlytics = new Crashlytics.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, crashlytics);
        Stetho.initializeWithDefaults(this);
        sContext = getApplicationContext();
        Realm.init(this);
    }

    public static Context getAppContext() {
        return sContext;
    }

    @Override
    public void onTerminate() {
        sContext = null;
        super.onTerminate();
    }

}
