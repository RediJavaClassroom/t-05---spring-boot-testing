package com.redi.demo.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("current")
    Call<WeatherResponse> getWeather(@Query("lat") Double lat, @Query("lon") Double lon);

}