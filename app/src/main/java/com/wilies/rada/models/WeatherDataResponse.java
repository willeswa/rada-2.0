package com.wilies.rada.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The object returned by the server
 */
public class WeatherDataResponse {

    private String timezone;

    @SerializedName("current")
    private Weather currentWeather;

    @SerializedName("hourly")
    private List<Weather> hourlyWeathers;

    @SerializedName("daily")
    private List<DailyWeather> dailyWeathers;


    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    @Override
    public String toString() {
        return "WeatherDataResponse{" +
                "timezone='" + timezone + '\'' +
                ", weather=" + currentWeather +
                '}';
    }

    public List<Weather> getHourlyWeathers() {
        return hourlyWeathers;
    }

    public void setHourlyWeathers(List<Weather> hourlyWeathers) {
        this.hourlyWeathers = hourlyWeathers;
    }

    public List<DailyWeather> getDailyWeathers() {
        return dailyWeathers;
    }

    public void setDailyWeathers(List<DailyWeather> dailyWeathers) {
        this.dailyWeathers = dailyWeathers;
    }
}
