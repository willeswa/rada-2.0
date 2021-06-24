package com.wilies.rada.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wilies.rada.R;
import com.wilies.rada.adapters.HourlyWeatherAdapter;
import com.wilies.rada.models.Weather;
import com.wilies.rada.models.WeatherDataResponse;
import com.wilies.rada.utils.Utility;
import com.wilies.rada.viewmodels.WeatherViewModel;

public class LaunchActivity extends AppCompatActivity {
    private static final String TAG = LaunchActivity.class.getSimpleName();
    private Button forecastButton;
    private HourlyWeatherAdapter mHourlyWeatherAdapter;
    private WeatherViewModel mHourlyWeatherViewModel;
    private RecyclerView mRecyclerView;
    private TextView currentWeatherTV;
    private TextView currentLocationTV;
    private TextView currentDateTV;
    private TextView currentTempTV;
    private ImageView currentIconIV;
    private LinearLayout mLinearLayout;
    private ProgressBar mainProgressBar;
    private LinearLayout mainRootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        populateViews();

        mHourlyWeatherAdapter = new HourlyWeatherAdapter(this);
        mHourlyWeatherViewModel = new WeatherViewModel(getApplication());
        mHourlyWeatherViewModel.init();
        mHourlyWeatherViewModel.loadWeatherData("Nairobi");
        mRecyclerView.setAdapter(mHourlyWeatherAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mHourlyWeatherViewModel.setIsLoading(true);


        mHourlyWeatherViewModel.getWeatherDataResponseLiveData().observe(this, new Observer<WeatherDataResponse>() {

            @Override
            public void onChanged(WeatherDataResponse weatherDataResponse) {

                if (weatherDataResponse != null) {
                    loadCurrentWeather(weatherDataResponse.getCurrentWeather());
                    mHourlyWeatherAdapter.setHourlyWeatherList(weatherDataResponse.getHourlyWeathers());
                    mHourlyWeatherViewModel.setIsLoading(false);
                } else {
                    Log.i(TAG, "nah man, didn't happen");
                }
            }
        });

        mHourlyWeatherViewModel.getIsLoading().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean isLoading) {
                if (!isLoading) {
                    mainProgressBar.setVisibility(View.GONE);
                    mainRootLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        forecastButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, WeekForecastActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                goToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void loadCurrentWeather(Weather currentWeather) {
        currentWeatherTV.setText(Utility.capitalize(currentWeather.getWeatherDescription().get(0).getDescription()));
        currentTempTV.setText(Utility.kelvinToCelcius(currentWeather.getTemperature()));
        currentLocationTV.setText(Utility.getLocationName(-1.3074432, 36.78208, this));
        currentDateTV.setText(Utility.getDateFromTimestamp(currentWeather.getUnixTime()));
        imageToLoadForWeather(currentWeather);
    }


    /**
     * Matches a weather group with the appropriate image for the main screen
     * current weather view
     *
     * @param currentWeather
     */
    private void imageToLoadForWeather(Weather currentWeather) {
        String weatherGroup = currentWeather.getWeatherDescription().get(0).getMain();
        switch (weatherGroup) {
            case "Snow":
                imageToLoad("snoowy");
                break;
            case "Rain":
            case "Drizzle":
            case "Thunderstorm":
                imageToLoad("rainy");
                break;
            case "Clear":
                imageToLoad("sunny");
                break;
            case "Clouds":
                imageToLoad("cloudy");
                break;
            default:
                imageToLoad("default_weather");
                break;
        }
    }

    /**
     * Takes the name of the image to load and
     * passes it to a utility method for actual loading
     *
     * @param imageName
     */
    private void imageToLoad(String imageName) {
        Utility.loadDrawableToImageView(mLinearLayout, imageName, "drawable", this, currentIconIV);

    }

    private void populateViews() {

        forecastButton = findViewById(R.id.forecast_button);
        mRecyclerView = findViewById(R.id.hourly_recycler);
        currentWeatherTV = findViewById(R.id.current_weather_tv);
        currentLocationTV = findViewById(R.id.current_location_tv);
        currentDateTV = findViewById(R.id.current_date_tv);
        currentTempTV = findViewById(R.id.current_temp_tv);
        mLinearLayout = findViewById(R.id.home_root_linearlayout);
        currentIconIV = findViewById(R.id.current_weather_icon_iv);
        mainProgressBar = findViewById(R.id.main_loading_bar);
        mainRootLayout = findViewById(R.id.main_screen_root_layout);


    }
}