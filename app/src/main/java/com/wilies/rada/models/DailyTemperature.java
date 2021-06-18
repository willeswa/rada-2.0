package com.wilies.rada.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The model specifically for the daily temperature.
 * Defines max and min temperature.
 * Decorated with @SerializedName for Gson serialization
 */
public class DailyTemperature {

    @Expose
    private int id;

    @SerializedName("min")
    private float minTemp;

    @SerializedName("max")
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
