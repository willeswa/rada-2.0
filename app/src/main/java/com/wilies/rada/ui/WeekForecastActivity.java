package com.wilies.rada.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.wilies.rada.R;
import com.wilies.rada.adapters.DailyWeatherAdapter;
import com.wilies.rada.models.WeatherDataResponse;
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
        mWeatherViewModel.loadWeatherData("Nairobi");
        mWeatherAdapter = new DailyWeatherAdapter(this);
        mRecyclerView.setAdapter(mWeatherAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWeatherViewModel.setIsLoading(true);

        mWeatherViewModel.getWeatherDataResponseLiveData().observe(this, new Observer<WeatherDataResponse>(){

            @Override
            public void onChanged(WeatherDataResponse weatherDataResponse) {
                mWeatherAdapter.setDailyForecasts(weatherDataResponse.getDailyWeathers());
                mWeatherViewModel.setIsLoading(false);
            }
        });

        mWeatherViewModel.getIsLoading().observe(this, new Observer<Boolean>(){

            @Override
            public void onChanged(Boolean isLoading) {
                if(!isLoading){
                    rootLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}