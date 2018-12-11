package com.example.ahmetanilgur.fakearmut;

import android.app.SharedElementCallback;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_prices);
        Preference sortPreference = findPreference("sort_price");
        Preference citySortPreference = findPreference("sort_city");
        Preference jobSortPreference = findPreference("sort_job");
        Preference loginPreference = findPreference("login_preferences");
        sortPreference.setOnPreferenceChangeListener(this);
        citySortPreference.setOnPreferenceChangeListener(this);
        jobSortPreference.setOnPreferenceChangeListener(this);
        loginPreference.setOnPreferenceChangeListener(this);
        SharedPreferences sort_price = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(sortPreference, sort_price.getString(sortPreference.getKey(),""));
        SharedPreferences sort_city = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(citySortPreference, sort_city.getString(citySortPreference.getKey(),""));
        SharedPreferences sort_job = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(jobSortPreference, sort_job.getString(citySortPreference.getKey(),""));
        SharedPreferences login_preference = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(loginPreference, login_preference.getString(loginPreference.getKey(),""));
        Log.d(TAG, "onPreferenceChange, You are now logged as: "+ login_preference.getString(loginPreference.getKey(),""));
        // >>>> Yukarıdaki line of code admin ve Anıl usernamelerini vermektedir. <<<<<
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) { preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue); }
            return true;
    }
}