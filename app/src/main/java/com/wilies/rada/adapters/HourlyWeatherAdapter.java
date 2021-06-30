package com.wilies.rada.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wilies.rada.R;
import com.wilies.rada.models.Weather;
import com.wilies.rada.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourlyViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<Weather> mHourlyWeatherList = new ArrayList<>();
    private String PREFERRED_UNITS;


    public HourlyWeatherAdapter(Context context) {
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
        holder.getUnitsTV().setText(PREFERRED_UNITS);
        Utility.setPreferredUnits(holder.getHourlyTempTextView(), PREFERRED_UNITS, weather.getTemperature());
        holder.getHourlyTimeTextView().setText(Utility.getHoursFromTimestamp(weather.getUnixTime()));
        Utility.loadFromURLToImageView(mContext, weather.getWeatherDescription().get(0).getIcon(), holder.getHourlyIcon());
    }


    @Override
    public int getItemCount() {
        return mHourlyWeatherList.size();
    }

    public void setHourlyWeatherList(List<Weather> weatherList) {
        mHourlyWeatherList = weatherList;
        notifyDataSetChanged();
    }

    public void setPREFERRED_UNITS(String PREFERRED_UNITS) {
        this.PREFERRED_UNITS = PREFERRED_UNITS;
        notifyDataSetChanged();
    }


    public class HourlyViewHolder extends RecyclerView.ViewHolder {
        private final TextView hourlyTimeTextView;
        private final ImageView hourlyIcon;
        private final TextView hourlyTempTextView;
        private final TextView unitsTV;

        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            hourlyIcon = itemView.findViewById(R.id.hourly_cloud_image);
            hourlyTimeTextView = itemView.findViewById(R.id.hour);
            hourlyTempTextView = itemView.findViewById(R.id.hourly_daily_temp);
            unitsTV = itemView.findViewById(R.id.units);
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


        public TextView getUnitsTV() {
            return unitsTV;
        }
    }
}
