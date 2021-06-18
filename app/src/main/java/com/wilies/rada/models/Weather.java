package com.wilies.rada.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Standard weather object.
 */
public class Weather {

    @Expose
    private int id;

    @SerializedName("dt")
    private int unixTime;

    @SerializedName("temp")
    private float temperature;

    @SerializedName("weather")
    private WeatherDescription weatherDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(int unixTime) {
        this.unixTime = unixTime;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public WeatherDescription getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(WeatherDescription weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
