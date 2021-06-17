package com.wilies.rada.Models;

/**
 * The object returned by the server
 */
public class WeatherDataResponse {
    private int id;
    private String timezone;
    private Weather weather;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



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
