package com.wilies.rada.models;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("cod")
    private int code;

    private String message;

    public int getCode() {
        return code;
    }



    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
