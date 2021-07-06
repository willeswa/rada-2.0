package com.wilies.rada.viewmodels;

import android.app.Application;
import android.content.Context;
import android.location.Address;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.wilies.rada.models.ErrorResponse;
import com.wilies.rada.models.WeatherDataResponse;
import com.wilies.rada.repositories.WeatherRepository;
import com.wilies.rada.utils.Constants;
import com.wilies.rada.utils.Utility;
import com.wilies.rada.work.SyncWeatherWorker;

import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

public class WeatherViewModel extends AndroidViewModel {
    private static final String TAG = WeatherViewModel.class.getCanonicalName();
    private WeatherRepository mWeatherRepository;
    private LiveData<WeatherDataResponse> mWeatherDataResponseLiveData;
    private MutableLiveData<ErrorResponse> mErrorResponse;
    public WorkManager mWorkManager;
    private Context mContext;
    private double mLat;
    private double mLon;


    public void init() {
        mWeatherRepository = WeatherRepository.getInstance();
        mWeatherDataResponseLiveData = mWeatherRepository.getWeatherData();
        mErrorResponse = mWeatherRepository.getErrorResponse();
        mWorkManager = WorkManager.getInstance(mContext);

    }
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
    }

    public void loadWeatherData(double lat, double lon){
        mLat = lat;
        mLon = lon;
        mWeatherRepository.loadWeatherData(lat, lon);
    }

    public LiveData<WeatherDataResponse> getWeatherDataResponseLiveData() {
        return mWeatherDataResponseLiveData;
    }


    public MutableLiveData<ErrorResponse> getErrorResponse() {
        return mErrorResponse;
    }

    public void startSync(){
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(SyncWeatherWorker.class, 15, TimeUnit.MINUTES)
                .setInputData(inputLocationData())
                .setInitialDelay(15, TimeUnit.MINUTES)
                .build();
        mWorkManager.enqueueUniquePeriodicWork(Constants.WORK_MANAGER_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest);
        Utility.showFeedBack("Sync started", getApplication().getApplicationContext());
    }


    private Data inputLocationData(){
        Data.Builder builder = new Data.Builder();
            builder.putDouble(Constants.LAT, mLat);
            builder.putDouble(Constants.LON, mLon);
        return builder.build();
    }
}
