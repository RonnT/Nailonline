package com.nailonline.client;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class App extends Application{

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        sContext = getApplicationContext();
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
