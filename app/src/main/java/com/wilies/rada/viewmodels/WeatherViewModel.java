package com.wilies.rada.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wilies.rada.models.WeatherDataResponse;
import com.wilies.rada.repositories.WeatherRepository;

public class WeatherViewModel extends AndroidViewModel {
    private static final String TAG = WeatherViewModel.class.getCanonicalName();
    private WeatherRepository mWeatherRepository;
    private LiveData<WeatherDataResponse> mWeatherDataResponseLiveData;
    private LiveData<Boolean> isLoading;



    public void init() {
        mWeatherRepository = new WeatherRepository();
        mWeatherDataResponseLiveData = mWeatherRepository.getWeatherData();
        isLoading = mWeatherRepository.getIsLoading();
    }
    public WeatherViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadWeatherData(String location){
        mWeatherRepository.loadWeatherData(location);
    }

    public LiveData<WeatherDataResponse> getWeatherDataResponseLiveData() {
        return mWeatherDataResponseLiveData;
    }

    public void setIsLoading(boolean isLoading){
        mWeatherRepository.setIsLoading(isLoading);
    }


    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
