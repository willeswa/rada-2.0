package com.wilies.rada.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wilies.rada.models.WeatherDataResponse;
import com.wilies.rada.repositories.WeatherRepository;

public class HourlyWeatherViewModel extends AndroidViewModel {
    private static final String TAG = HourlyWeatherViewModel.class.getCanonicalName();
    private WeatherRepository mWeatherRepository;
    private LiveData<WeatherDataResponse> mWeatherDataResponseLiveData;


    public void init() {
        mWeatherRepository = new WeatherRepository();
        mWeatherDataResponseLiveData = mWeatherRepository.getWeatherData();
    }
    public HourlyWeatherViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadWeatherData(String location){
        mWeatherRepository.loadWeatherData(location);
    }

    public LiveData<WeatherDataResponse> getWeatherDataResponseLiveData() {
        return mWeatherDataResponseLiveData;
    }
}
