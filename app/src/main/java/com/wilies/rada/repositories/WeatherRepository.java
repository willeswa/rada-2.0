package com.wilies.rada.repositories;

import android.location.Address;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wilies.rada.WebService;
import com.wilies.rada.models.ErrorResponse;
import com.wilies.rada.models.WeatherDataResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherRepository {
    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static final String API_KEY = "";
    private static final String TAG = WeatherRepository.class.getSimpleName();

    private WebService mWebService;
    private MutableLiveData<WeatherDataResponse> mWeatherData;


    private MutableLiveData<ErrorResponse> mErrorResponse;
    private static WeatherRepository sWeatherRepository;


    private WeatherRepository(){
        mWeatherData = new MutableLiveData<>();
        mErrorResponse = new MutableLiveData<>();

        OkHttpClient client = constructClient();

        mWebService = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService.class);
    }

    public static WeatherRepository getInstance() {
        if(sWeatherRepository == null){
            sWeatherRepository = new WeatherRepository();
        }
        return sWeatherRepository;
    }

    public void loadWeatherData(Address location){

        mWebService.getWeatherData(location.getLatitude(),location.getLongitude(), API_KEY)
        .enqueue(new Callback<WeatherDataResponse>(){

            @Override
            public void onResponse(Call<WeatherDataResponse> call, Response<WeatherDataResponse> response) {
               if(response.isSuccessful() && response != null){
                   mWeatherData.postValue(response.body());
            } else {
                   mWeatherData.postValue(null);
                   Gson gson = new Gson();
                   Type type = new TypeToken<ErrorResponse>(){}.getType();
                   ErrorResponse error = gson.fromJson(response.errorBody().charStream(), type);
                   mErrorResponse.postValue(error);
               }

            }

            @Override
            public void onFailure(Call<WeatherDataResponse> call, Throwable t) {
                Log.i(TAG, "Repository says nothing -> ", t);

                mWeatherData.postValue(null);
            }


        });


    }

    private OkHttpClient constructClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        return client;
    }

    public MutableLiveData<WeatherDataResponse> getWeatherData() {
        return mWeatherData;
    }

    public MutableLiveData<ErrorResponse> getErrorResponse() {
        return mErrorResponse;
    }

}
