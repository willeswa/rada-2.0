package com.wilies.rada;

import com.wilies.rada.models.WeatherDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The service client interface to connect with he API
 */
public interface WebService {

    //The query method takes in the coordinates & the API key
    // Returns a Call object
    @GET("/data/2.5/onecall")
    Call<WeatherDataResponse> getWeatherData(@Query("lat") Double lat,
                                             @Query("lon") Double lon,
                                             @Query("appid") String key);
}
