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
        setContentView(R.layout.activity_request);
        SharedPreferences login_preference = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        //Shared preflere asagidaki linedan "admin" alabiliyoruz.
        //Log.d(TAG, "onCreate: "+login_preference.getString("login_preferences",""));
        final TextView username = findViewById(R.id.tv_requests_username);
        final TextView question = findViewById(R.id.tv_detail_requests_question);
        Button button_decline = findViewById(R.id.btn_req_decline);
        Button button_accept = findViewById(R.id.btn_req_accept);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("username")){
            Log.d(TAG, "onCreate: Does it have an intent?: yes ");
            String usernameFromIntent = intentThatStartedThisActivity.getStringExtra("username");
            username.setText(usernameFromIntent);
            question.setText("Would you like to request "+usernameFromIntent+"'s calendar?\nYou can click Send button to do so.");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        "http://192.168.47.40:8001/request/"+PreferenceManager
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

