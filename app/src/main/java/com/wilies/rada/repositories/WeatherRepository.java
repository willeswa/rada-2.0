package com.wilies.rada.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.wilies.rada.WebService;
import com.wilies.rada.models.WeatherDataResponse;

import okhttp3.OkHttpClient;
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
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();;

    public WeatherRepository(){
        mWeatherData = new MutableLiveData<>();

        OkHttpClient client = constructClient();

        mWebService = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService.class);
    }

    public void loadWeatherData(String location){
        mWebService.getWeatherData(-1.3074432,36.78208, API_KEY)
        .enqueue(new Callback<WeatherDataResponse>(){

            @Override
            public void onResponse(Call<WeatherDataResponse> call, Response<WeatherDataResponse> response) {
               if(response.body() != null){
                   mWeatherData.postValue(response.body());}
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

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.postValue(isLoading);
    }
}
