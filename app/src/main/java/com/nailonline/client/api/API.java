package com.nailonline.client.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Roman T. on 12.12.2016.
 */

public class Api {

    //TODO move to BuildConfig
    public static final String API_URL = "http://185.20.225.17:8080/core/";

    private static ApiService sApiService = createService(ApiService.class);

    protected static <S> S createService(Class<S> serviceClass) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(builder.build())
                .build();
        return  retrofit.create(serviceClass);
    }

    public static Call<ResponseBody> getFreeToken(){
        return sApiService.getFreeToken();
    }
}
