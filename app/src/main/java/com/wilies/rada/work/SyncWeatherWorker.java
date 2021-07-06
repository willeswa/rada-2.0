package com.wilies.rada.work;

import android.content.Context;
import android.location.Address;
import android.media.MediaDrmResetException;

import androidx.annotation.NonNull;
import androidx.work.PeriodicWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.wilies.rada.repositories.WeatherRepository;
import com.wilies.rada.utils.Constants;
import com.wilies.rada.utils.Utility;

import java.util.concurrent.TimeUnit;

public class SyncWeatherWorker extends Worker {
    private WeatherRepository mWeatherRepository;

    public SyncWeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mWeatherRepository = WeatherRepository.getInstance();
    }

    @NonNull
    @Override
    public Result doWork() {
        double lat = getInputData().getDouble(Constants.LAT, 0);
        double lon = getInputData().getDouble(Constants.LON, 0);
        mWeatherRepository.loadWeatherData(lat, lon);
        return Result.success();
    }
}
