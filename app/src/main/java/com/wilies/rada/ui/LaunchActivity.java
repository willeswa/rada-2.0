
    package com.wilies.rada.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.wilies.rada.R;
import com.wilies.rada.adapters.HourlyWeatherAdapter;
import com.wilies.rada.models.WeatherDataResponse;
import com.wilies.rada.viewmodels.HourlyWeatherViewModel;

    public class LaunchActivity extends AppCompatActivity {
        private static final String TAG = LaunchActivity.class.getSimpleName();
        private Button forecastButton;
        private HourlyWeatherAdapter mHourlyWeatherAdapter;
        private HourlyWeatherViewModel mHourlyWeatherViewModel;
        private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        loadState();
        mHourlyWeatherAdapter = new HourlyWeatherAdapter(this);
        mHourlyWeatherViewModel = new HourlyWeatherViewModel(getApplication());
        mHourlyWeatherViewModel.init();
        mHourlyWeatherViewModel.loadWeatherData("Nairobi");
        mRecyclerView.setAdapter(mHourlyWeatherAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mHourlyWeatherViewModel.getWeatherDataResponseLiveData().observe(this, new Observer<WeatherDataResponse>() {

            @Override
            public void onChanged(WeatherDataResponse weatherDataResponse) {

                if(weatherDataResponse != null){
                    mHourlyWeatherAdapter.setHourlyWeatherList(weatherDataResponse.getHourlyWeathers());
                } else {
                    Log.i(TAG, "nah man, didn't happen");
                }
            }
        });

        forecastButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForecastActivity.class);
            startActivity(intent);
        });
    }

        private void loadState() {

        forecastButton = findViewById(R.id.forecast_button);
        mRecyclerView = findViewById(R.id.hourly_recycler);

        }
    }