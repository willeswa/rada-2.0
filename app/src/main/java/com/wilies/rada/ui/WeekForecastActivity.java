package com.wilies.rada.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.wilies.rada.R;
import com.wilies.rada.adapters.DailyWeatherAdapter;
import com.wilies.rada.models.WeatherDataResponse;
import com.wilies.rada.utils.Utility;
import com.wilies.rada.viewmodels.WeatherViewModel;

public class WeekForecastActivity extends AppCompatActivity {
    private WeatherViewModel mWeatherViewModel;
    private RecyclerView mRecyclerView;
    private DailyWeatherAdapter mWeatherAdapter;
    private ProgressBar progressBar;
    private LinearLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mRecyclerView = findViewById(R.id.forecast_recycler_view);
        progressBar = findViewById(R.id.weekly_progress_bar);
        rootLayout = findViewById(R.id.weekly_root_layout);


        mWeatherViewModel = new WeatherViewModel(getApplication());
        mWeatherViewModel.init();

        Intent intent = getIntent();
        String location = intent.getStringExtra(LaunchActivity.LOCATION_EXTRA);
        String preferred_units = intent.getStringExtra(LaunchActivity.PREFERRED_UNITS_EXTRA);

        mWeatherViewModel.loadWeatherData(Utility.getLocation(this, location));
        mWeatherAdapter = new DailyWeatherAdapter(this);
        mWeatherAdapter.setPREFERRED_UNITS(preferred_units);
        mRecyclerView.setAdapter(mWeatherAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        mWeatherViewModel.getWeatherDataResponseLiveData().observe(this, new Observer<WeatherDataResponse>(){

            @Override
            public void onChanged(WeatherDataResponse weatherDataResponse) {
                mWeatherAdapter.setDailyForecasts(weatherDataResponse.getDailyWeathers());
                Utility.finishLoading(progressBar, rootLayout);
            }
        });

    }
}