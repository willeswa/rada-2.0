
    package com.wilies.rada.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.wilies.rada.R;

    public class LaunchActivity extends AppCompatActivity {
        private Button forecastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadState();

        forecastButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForecastActivity.class);
            startActivity(intent);
        });
    }

        private void loadState() {
            forecastButton = findViewById(R.id.forecast_button);
        }
    }