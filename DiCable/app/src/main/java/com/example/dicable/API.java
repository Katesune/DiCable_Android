package com.example.dicable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("/json")
    Call<Answer> search(@Query("ip") String ip, @Query("port") String port);
}
