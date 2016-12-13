package com.nailonline.client.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Roman T. on 11.12.2016.
 */

public interface ApiService {

    @POST("core.php?action=get_free_token")
    Call<ResponseBody> getFreeToken();
}
