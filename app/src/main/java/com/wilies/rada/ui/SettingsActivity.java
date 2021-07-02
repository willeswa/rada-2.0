package com.wilies.rada.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.wilies.rada.R;
import com.wilies.rada.utils.Utility;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }




    public static class SettingsFragment extends PreferenceFragmentCompat {
        private Preference maps_location;
        private EditTextPreference locationPreference;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            locationPreference = findPreference(getResources().getString(R.string.location_key));

            maps_location = findPreference(getResources().getString(R.string.map_key));

            maps_location.setSummary("Explore "+ locationPreference.getText() + "on Google Maps");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q="+ maps_location.getSummary()));
            maps_location.setIntent(intent);
        }
    }


}