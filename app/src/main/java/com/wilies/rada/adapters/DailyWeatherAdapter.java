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
import com.wilies.rada.models.DailyWeather;
import com.wilies.rada.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.DailyViewHolder>{
    private final LayoutInflater mLayoutInflater;
    private List<DailyWeather> mDailyForecasts = new ArrayList<>();
    private Context mContext;

    public DailyWeatherAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.forecast_recycler_item, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        DailyWeather dailyWeather = mDailyForecasts.get(position);
        holder.getDailyHumidity().setText(Utility.parseFloatToString(dailyWeather.getHumidity()));
        holder.getDailyMaxTemp().setText(Utility.parseFloatToString(dailyWeather.getDailyTemperature().getMaxTemp()));
        holder.getDailyMinTemp().setText(Utility.parseFloatToString(dailyWeather.getDailyTemperature().getMinTemp()));
        holder.getDailyRain().setText(Utility.parseFloatToString(dailyWeather.getRain()));
        holder.getDayOfWeek().setText(Utility.getDayOfTheWeek(dailyWeather.getTime()));
//        Utility.loadLocalToImageView(holder.itemView, "ic_baseline_cloud_25", "drawable", mContext, holder.getDailyImageView());
        Utility.loadFromURLToImageView(mContext, dailyWeather.getWeatherDescription().get(0).getIcon(), holder.getDailyImageView());
    }


    @Override
    public int getItemCount() {
        return this.mDailyForecasts.size();
    }

    public void setDailyForecasts(List<DailyWeather> dailyWeathers) {
        this.mDailyForecasts = dailyWeathers;
        notifyDataSetChanged();
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder {
        private TextView dayOfWeek;
        private ImageView dailyImageView;
        private TextView dailyMaxTemp;
        private TextView dailyMinTemp;
        private TextView dailyRain;
        private TextView dailyHumidity;

        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);

            dailyImageView = itemView.findViewById(R.id.daily_icon_view);
            dailyMaxTemp = itemView.findViewById(R.id.daily_max_temp);
            dailyMinTemp = itemView.findViewById(R.id.daily_min_temp);
            dailyRain = itemView.findViewById(R.id.daily_rain);
            dailyHumidity = itemView.findViewById(R.id.daily_humidty);
            dayOfWeek = itemView.findViewById(R.id.daily_day_name);
        }

        public ImageView getDailyImageView() {
            return dailyImageView;
        }

        public TextView getDailyMaxTemp() {
            return dailyMaxTemp;
        }

        public TextView getDailyMinTemp() {
            return dailyMinTemp;
        }

        public TextView getDailyRain() {
            return dailyRain;
        }

        public TextView getDailyHumidity() {
            return dailyHumidity;
        }

        public TextView getDayOfWeek() {
            return dayOfWeek;
        }
    }
}
