package com.example.ahmetanilgur.fakearmut;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "za";
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        TextView username = findViewById(R.id.tv_detail_calendar_username);
        TextView job = findViewById(R.id.tv_detail_calendar_job);
        TextView price = findViewById(R.id.tv_detail_calendar_price);
        Button b_monday = findViewById(R.id.button_calendar_monday);
        Button b_tuesday = findViewById(R.id.button_calendar_tuesday);
        Button b_wednesday = findViewById(R.id.button_calendar_wednesday);
        Button b_thursday = findViewById(R.id.button_calendar_thursday);
        Button b_friday = findViewById(R.id.button_calendar_friday);
        Button b_saturday = findViewById(R.id.button_calendar_saturday);
        Button b_sunday = findViewById(R.id.button_calendar_sunday);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("username")){

            Log.d(TAG, "onCreate: Does it have an intent?:  ");
            String usernameFromIntent = intentThatStartedThisActivity.getStringExtra("username");
            String jobFromIntent = intentThatStartedThisActivity.getStringExtra("job");
            String priceFromIntent = intentThatStartedThisActivity.getStringExtra("price");
            String buttonMonday = intentThatStartedThisActivity.getStringExtra("availability_monday");
            String buttonTuesday = intentThatStartedThisActivity.getStringExtra("availability_tuesday");
            String buttonWednesday = intentThatStartedThisActivity.getStringExtra("availability_wednesday");
            String buttonThursday = intentThatStartedThisActivity.getStringExtra("availability_thursday");
            String buttonFriday = intentThatStartedThisActivity.getStringExtra("availability_friday");
            String buttonSaturday = intentThatStartedThisActivity.getStringExtra("availability_saturday");
            String buttonSunday = intentThatStartedThisActivity.getStringExtra("availability_sunday");

            username.setText(usernameFromIntent);
            job.setText("You can hire "+usernameFromIntent+" as a "+jobFromIntent+" for $"+priceFromIntent+" daily.");
            price.setText("Buttons with blue text are "+usernameFromIntent+"'s available days.\n Unfortunately red ones are already taken.\n\n");
            b_monday.setClickable(Boolean.parseBoolean(buttonMonday));
            if(Boolean.parseBoolean(buttonMonday)){
                b_monday.setTextColor(Color.BLUE);
            } else b_monday.setTextColor(Color.RED);
            b_tuesday.setClickable(Boolean.parseBoolean(buttonTuesday));
            if(Boolean.parseBoolean(buttonTuesday)){
                b_tuesday.setTextColor(Color.BLUE);
            } else b_tuesday.setTextColor(Color.RED);
            b_wednesday.setClickable(Boolean.parseBoolean(buttonWednesday));
            if(Boolean.parseBoolean(buttonWednesday)){
                b_wednesday.setTextColor(Color.BLUE);
            } else b_wednesday.setTextColor(Color.RED);
            b_thursday.setClickable(Boolean.parseBoolean(buttonThursday));
            if(Boolean.parseBoolean(buttonThursday)){
                b_thursday.setTextColor(Color.BLUE);
            } else b_thursday.setTextColor(Color.RED);
            b_friday.setClickable(Boolean.parseBoolean(buttonFriday));
            if(Boolean.parseBoolean(buttonFriday)){
                b_friday.setTextColor(Color.BLUE);
            } else b_friday.setTextColor(Color.RED);
            b_saturday.setClickable(Boolean.parseBoolean(buttonSaturday));
            if(Boolean.parseBoolean(buttonSaturday)){
                b_saturday.setTextColor(Color.BLUE);
            } else b_saturday.setTextColor(Color.RED);
            b_sunday.setClickable(Boolean.parseBoolean(buttonSunday));
            if(Boolean.parseBoolean(buttonSunday)){
                b_sunday.setTextColor(Color.BLUE);
            } else b_sunday.setTextColor(Color.RED);

        }


    }


}
