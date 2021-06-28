package com.wilies.rada.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wilies.rada.R;
import com.wilies.rada.adapters.HourlyWeatherAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utility {
    private static SimpleDateFormat formatter;


    public static Address getLocation(Context context, String locationName) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList = null;

        try {
            addressList = geocoder.getFromLocationName(locationName, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressList.get(0);
    }


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

    public static String getDayOfTheWeek(int unixTime){
        formatter = new SimpleDateFormat("EEEE");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(unixTime*1000L);
        return  formatter.format(cal.getTime());
    }


    public static String parseFloatToString(float floatValue) {
        return String.valueOf(floatValue);
    }


    /**
     * Loads an image into an ImageView using the Glide library
     * @param view
     * @param fileName
     * @param defType
     * @param context
     * @param imageView
     */
    public static void loadDrawableToImageView(View view, String fileName, String defType, Context context, ImageView imageView) {
        Glide.with(view)
                .load(context.getResources()
                        .getIdentifier(
                                fileName,
                                defType,
                                context.getPackageName()))
                .into(imageView);
    }


    public static void loadFromURLToImageView(Context context, String imageName, ImageView imageView){
        URL url = getUrlFromString("http://openweathermap.org/img/wn/" + imageName + ".png");
        Glide.with(context).load(url).
                into(imageView);
    }

    private static URL getUrlFromString(String s) {
        URL url = null;

        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String capitalize(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    /**
     * Convert Kelvin to degrees celcius
     * @param floatTemp
     * @return
     */
    public static String kelvinToCelcius(float floatTemp){
         int cel = (int) (floatTemp - 273.15);
         return  String.valueOf(cel);
    }

    public static String getPreferredLocation(Application application, SharedPreferences sharedPreferences) {
        Context context = application.getApplicationContext();
        String location = sharedPreferences.getString(context.getResources().getString(R.string.location_key), context.getString(R.string.default_location));
        return location;
    }

    public static int getPreferredUnits(Application application, SharedPreferences sharedPreferences){
        Context context = application.getApplicationContext();
        String units = sharedPreferences.getString(context.getResources().getString(R.string.units_key), context.getString(R.string.default_units));

        if(units == "Celcius"){
            return 0;
        } else {
            return 1;
        }
    }

}
