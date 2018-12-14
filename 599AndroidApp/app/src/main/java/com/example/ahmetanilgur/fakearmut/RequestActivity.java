package com.example.ahmetanilgur.fakearmut;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestActivity extends AppCompatActivity {

    private static final String TAG = "lels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences login_preference = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Intent intentThatStartedThisActivity = getIntent();
       // Log.d(TAG, "onCreate: ilk oncrrate"+ intentThatStartedThisActivity.getStringExtra("username"));
        StringRequest firstStringRequest = new StringRequest(Request.Method.GET,
                BuildConfig.nowApiUrl+"/isaccepted/"+PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext())
                        .getString("login_preferences","")
                        +"&"+intentThatStartedThisActivity.getStringExtra("username"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d(TAG, "onCreateteki isaccepted muhabbeti, calisiyor."+ response);
                        if(Boolean.parseBoolean(response)) {
                            finish();
                            Intent secondIntent = new Intent(getApplicationContext(), CalendarActivity.class);
                            secondIntent.putExtras(getIntent());
                            startActivity(secondIntent);
                        }
                        if(!Boolean.parseBoolean(response)) {
                            setContentView(R.layout.activity_request);
                            //Shared preflere asagidaki linedan "admin" alabiliyoruz.
                            //Log.d(TAG, "onCreate: "+login_preference.getString("login_preferences",""));
                            final TextView username = findViewById(R.id.tv_requests_username);
                            final TextView question = findViewById(R.id.tv_detail_requests_question);
                            Button button_decline = findViewById(R.id.btn_req_decline);
                            Button button_accept = findViewById(R.id.btn_req_accept);
                            Log.d(TAG, "onCreate: Does it have an intent?: yes, requestactivity.java icindeki");
                            String usernameFromIntent = getIntent().getStringExtra("username");
                            username.setText(usernameFromIntent);
                            question.setText("Would you like to request "+usernameFromIntent+"'s calendar?\nYou can click Send button to do so.");
                            button_decline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

                            // button click Accept asagida, tıklanan user icin calendar request atıyor.
                            button_accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                            BuildConfig.nowApiUrl+"/request/"+PreferenceManager
                                                    .getDefaultSharedPreferences(getApplicationContext())
                                                    .getString("login_preferences","")
                                                    +"&"+username.getText(),
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    // Display the first 500 characters of the response string.
                                                    Log.d(TAG, "onResponse: aynn knks clsyr");
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "onErrorResponse didnt work");
                                        }
                                    });
                                    Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
                                    Toast.makeText(RequestActivity.this, "Your request has been sent!",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse didnt work");
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(firstStringRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    }

