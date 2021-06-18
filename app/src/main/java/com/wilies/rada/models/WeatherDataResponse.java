package com.wilies.rada.models;

import com.google.gson.annotations.SerializedName;

/**
 * The object returned by the server
 */
public class WeatherDataResponse {

    private String timezone;

    @SerializedName("current")
    private Weather weather;


    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }



}
