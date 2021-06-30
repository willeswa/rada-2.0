package com.wilies.rada.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A daily weather objects with more fields compared to
 * the weather object
 */
public class DailyWeather {

    @Expose
    private int id;

    @SerializedName("dt")
    private int time;

    @SerializedName("temp")
    private DailyTemperature dailyTemperature;

    private float humidity;

    private float rain;

    @SerializedName("weather")
    private List<WeatherDescription> weatherDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DailyTemperature getDailyTemperature() {
        return dailyTemperature;
    }

    public void setDailyTemperature(DailyTemperature dailyTemperature) {
        this.dailyTemperature = dailyTemperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }

    public List<WeatherDescription> getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(List<WeatherDescription> weatherDescription) {
        this.weatherDescription = weatherDescription;
    }


}
