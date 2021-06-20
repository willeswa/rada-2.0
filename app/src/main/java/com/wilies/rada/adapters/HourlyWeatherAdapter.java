package com.wilies.rada.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wilies.rada.R;
import com.wilies.rada.models.Weather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourlyViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<Weather> mHourlyWeatherList = new ArrayList<>();


    public HourlyWeatherAdapter(Context context){
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.hourly_recycler_item, parent, false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        Weather weather = mHourlyWeatherList.get(position);
        holder.getHourlyTempTextView().setText(parseToString(weather.getTemperature()));
        holder.getHourlyTimeTextView().setText(getTimeFromTimestamp(weather.getUnixTime()));
        loadImage(holder);
    }

    private String getTimeFromTimestamp(int unixTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(unixTime*1000L);
        int hour = cal.get(Calendar.HOUR);
        return String.valueOf(hour);
    }


    private String parseToString(float temperature) {
        return String.valueOf(temperature);
    }

    private void loadImage(HourlyViewHolder holder) {
        Glide.with(holder.itemView)
                .load(mContext.getResources()
                        .getIdentifier(
                                "ic_baseline_cloud_25",
                                "drawable",
                                mContext.getPackageName()))
        .into(holder.getHourlyIcon());
    }

    @Override
    public int getItemCount() {
        return mHourlyWeatherList.size();
    }

    public void setHourlyWeatherList(List<Weather> weatherList){
        mHourlyWeatherList = weatherList;
        notifyDataSetChanged();
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder {
        private TextView hourlyTimeTextView;
        private ImageView hourlyIcon;
        private TextView hourlyTempTextView;

        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            hourlyIcon = itemView.findViewById(R.id.hourly_cloud_image);
            hourlyTimeTextView = itemView.findViewById(R.id.hour);
            hourlyTempTextView = itemView.findViewById(R.id.hourly_daily_temp);
        }

        public TextView getHourlyTimeTextView() {
            return hourlyTimeTextView;
        }



        public ImageView getHourlyIcon() {
            return hourlyIcon;
        }



        public TextView getHourlyTempTextView() {
            return hourlyTempTextView;
        }


    }
}