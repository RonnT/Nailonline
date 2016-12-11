package com.nailonline.client.api;

import com.nailonline.client.entity.UserTheme;

import retrofit2.Callback;
import retrofit2.http.POST;

/**
 * Created by Roman T. on 11.12.2016.
 */

public interface Api {

    @POST()
    String getFreeToken(Callback<String> callback);

    @POST()
    UserTheme getThemes(Callback<UserTheme> callback);
}
