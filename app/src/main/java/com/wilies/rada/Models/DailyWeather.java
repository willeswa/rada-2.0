package com.wilies.rada.Models;

/**
 * A daily weather objects with more fields compared to
 * the weather object
 */
public class DailyWeather {
    private int id;
    private int time;
    private DailyTemperature dailyTemperature;
    private float humidity;
    private float rain;
    private WeatherDescription weatherDescription;

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

    public WeatherDescription getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(WeatherDescription weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
