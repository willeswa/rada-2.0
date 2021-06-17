package com.wilies.rada.Models;

/**
 * Standard weather object.
 */
public class Weather {
    private int id;
    private int unixTime;
    private float temperature;
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
