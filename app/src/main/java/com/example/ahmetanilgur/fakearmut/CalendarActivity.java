package com.example.ahmetanilgur.fakearmut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "za";
    public TextView mDetailCalendarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        TextView t= findViewById(R.id.tv_detail_calendar);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            Log.d(TAG, "onCreate: it does "+intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT));
            String forecastStr = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            Log.d(TAG, "onCreate: calactivity"+ forecastStr);
            t.setText(forecastStr);
        }
    }
}
