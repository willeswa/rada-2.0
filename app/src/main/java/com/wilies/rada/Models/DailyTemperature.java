package com.wilies.rada.Models;


/**
 * The model specifically for the daily temperature.
 * Defines max and min temperature.
 */
public class DailyTemperature {
    private int id;

    private float minTemp;

    private float maxTemp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }


}
