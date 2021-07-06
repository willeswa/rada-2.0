package com.wilies.rada.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
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
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkInfo;

import com.wilies.rada.R;
import com.wilies.rada.adapters.HourlyWeatherAdapter;
import com.wilies.rada.models.ErrorResponse;
import com.wilies.rada.models.Weather;
import com.wilies.rada.models.WeatherDataResponse;
import com.wilies.rada.utils.Constants;
import com.wilies.rada.utils.Utility;
import com.wilies.rada.viewmodels.WeatherViewModel;

public class LaunchActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String LOCATION_EXTRA = "com.willies.rada.ui.location_extra";

    private static final String TAG = LaunchActivity.class.getSimpleName();
    public static final String PREFERRED_UNITS_EXTRA = "com.willies.rada.ui.preferred_units_extra";
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
    private Address location;
    private SharedPreferences sharedPreferences;
    private LinearLayoutManager mLinearLayoutManager;
    private Weather mMCurrentWeather;
    private TextView currentUnitsTV;
    private LinearLayout errorScreenLayout;
    private TextView errorCodeTV;
    private TextView errorMessage;
    private TextView timeSinceUpdate;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateViews();



        if(Utility.isInternetConnected(this)){
            loadState();
            mHourlyWeatherViewModel.init();
            mHourlyWeatherViewModel.loadWeatherData(location.getLatitude(), location.getLongitude());
            mHourlyWeatherAdapter.setPREFERRED_UNITS(Utility.getPreferredUnits(getApplication(), sharedPreferences));

            mRecyclerView.setAdapter(mHourlyWeatherAdapter);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);



            loadHourlyWeather();
            mHourlyWeatherViewModel.startSync();

        } else {
            ErrorResponse res = new ErrorResponse();
            res.setCode(500);
            res.setMessage("No internet Connection");
            loadErrorScreen(res, this);
        }

        forecastButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, WeekForecastActivity.class);
            intent.putExtra(LOCATION_EXTRA, preferredLocation());
            intent.putExtra(PREFERRED_UNITS_EXTRA, Utility.getPreferredUnits(getApplication(), sharedPreferences));
            startActivity(intent);
        });
    }


    private void loadState() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mHourlyWeatherViewModel = new WeatherViewModel(getApplication());
        mHourlyWeatherAdapter = new HourlyWeatherAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        location = Utility.getLocation(this, preferredLocation());
    }


    private void loadHourlyWeather() {

        mHourlyWeatherViewModel.getWeatherDataResponseLiveData().observe(this, new Observer<WeatherDataResponse>() {

            @Override
            public void onChanged(WeatherDataResponse weatherDataResponse) {
                if (weatherDataResponse != null) {
                    getSupportActionBar().show();
                    mMCurrentWeather = weatherDataResponse.getCurrentWeather();
                    loadCurrentWeather(mMCurrentWeather);
                    mHourlyWeatherAdapter.setHourlyWeatherList(weatherDataResponse.getHourlyWeathers());
                    Utility.finishLoading(mainProgressBar, mainRootLayout);

                }
            }
        });

        mHourlyWeatherViewModel.getErrorResponse().observe(this, new Observer<ErrorResponse>(){

            @Override
            public void onChanged(ErrorResponse errorResponse) {
                loadErrorScreen(errorResponse, LaunchActivity.this);
            }
        });

    }

    private void loadErrorScreen(ErrorResponse errorResponse, Context context) {
        errorCodeTV.setText(String.valueOf(errorResponse.getCode()));
        errorMessage.setText(Utility.getErrorMessage(errorResponse));
        Utility.finishLoading(mainProgressBar, errorScreenLayout);
        Utility.showErrorResponse(context, errorResponse);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
        Utility.setPreferredUnits(currentTempTV, Utility.getPreferredUnits(getApplication(), sharedPreferences), currentWeather.getTemperature());
        currentUnitsTV.setText(Utility.getPreferredUnits(getApplication(), sharedPreferences));
        currentLocationTV.setText(preferredLocation());
        currentDateTV.setText(Utility.getDateFromTimestamp(currentWeather.getUnixTime()));
        timeSinceUpdate.setText("Last updated: " + Utility.getLastUpdatedTime( currentWeather.getUnixTime()) );
        imageToLoadForWeather(currentWeather);
    }

    private String preferredLocation() {
        return Utility.getPreferredLocation(getApplication(), sharedPreferences);
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
        getSupportActionBar().hide();
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
        currentUnitsTV = findViewById(R.id.units_current);
        errorScreenLayout = findViewById(R.id.error_screen);
        errorCodeTV =  findViewById(R.id.error_code);
        errorMessage = findViewById(R.id.error_message);
        timeSinceUpdate = findViewById(R.id.last_updated);


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getResources().getString(R.string.location_key))) {
            Address location = Utility.getLocation(this, preferredLocation());
            mHourlyWeatherViewModel.loadWeatherData(location.getLatitude(), location.getLongitude());
        } else if (key.equals(getResources().getString(R.string.profile_name_key))) {

        } else if (key.equals(getResources().getString(R.string.units_key))) {
            mHourlyWeatherAdapter.setPREFERRED_UNITS(Utility.getPreferredUnits(getApplication(), sharedPreferences));
            loadCurrentWeather(mMCurrentWeather);

        }


    }
}