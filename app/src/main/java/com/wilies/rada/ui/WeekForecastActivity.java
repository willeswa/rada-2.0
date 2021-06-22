package com.wilies.rada.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wilies.rada.R;
import com.wilies.rada.adapters.DailyWeatherAdapter;
import com.wilies.rada.models.WeatherDataResponse;
import com.wilies.rada.viewmodels.WeatherViewModel;

public class WeekForecastActivity extends AppCompatActivity {
    private WeatherViewModel mWeatherViewModel;
    private RecyclerView mRecyclerView;
    private DailyWeatherAdapter mWeatherAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mRecyclerView = findViewById(R.id.forecast_recycler_view);

        mWeatherViewModel = new WeatherViewModel(getApplication());
        mWeatherViewModel.init();
        mWeatherViewModel.loadWeatherData("Nairobi");
        mWeatherAdapter = new DailyWeatherAdapter(this);
        mRecyclerView.setAdapter(mWeatherAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mWeatherViewModel.getWeatherDataResponseLiveData().observe(this, new Observer<WeatherDataResponse>(){

            @Override
            public void onChanged(WeatherDataResponse weatherDataResponse) {
                mWeatherAdapter.setDailyForecasts(weatherDataResponse.getDailyWeathers());
            }
        });
    }
}