package com.nailonline.client;

import android.app.Application;

import com.nailonline.client.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roman T. on 11.12.2016.
 */

public class App extends Application{

    private static Api api;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://185.20.225.17:8080/core/core.php") //TODO move to BuildConfig
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public static Api getApi(){
        return api;
    }
}
