package com.wilies.rada.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utility {
    private static SimpleDateFormat formatter;


    public static String getLocationName(double lat, double lon, Context context){
        List<Address> address;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String city = null;
        String country = null;

        try {
            address = geocoder.getFromLocation(lat, lon, 1);
            city = address.get(0).getSubLocality();
            country = address.get(0).getCountryName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return city + ", "+ country;
    }

    public static String getHoursFromTimestamp(int unixTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(unixTime*1000L);
        formatter = new SimpleDateFormat("h aa");

        return formatter.format(cal.getTime()).toUpperCase();
    }

    public static String getDateFromTimestamp(int unixTime){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(unixTime*1000L);

        formatter = new SimpleDateFormat("E, dd MMM yyyy");
        String stringDate = formatter.format(cal.getTime());

        return stringDate;
    }
}
