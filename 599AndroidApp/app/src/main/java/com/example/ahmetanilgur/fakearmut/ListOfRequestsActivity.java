package com.example.ahmetanilgur.fakearmut;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.util.List;

public class ListOfRequestsActivity extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_requests);
        final String TAG="lols";
        final TextView requests_header = findViewById(R.id.requests_header);
        final TextView question = findViewById(R.id.requests_question);
        final Button button_decline = findViewById(R.id.requests_q_decline);
        final Button button_accept = findViewById(R.id.requests_q_accept);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* Log.d(TAG, "onCreate: "+PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString("login_preferences",""));*/
        StringRequest firstStringRequest = new StringRequest(Request.Method.GET,
                BuildConfig.nowApiUrl+"/requestlist/"+PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext())
                        .getString("login_preferences",""),
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "reel response: "+response.length());
                        if(response.length()<1){
                            Log.d(TAG, "onResponse: girdi ife");
                            requests_header.setText("No new requests!");
                            question.setText("Come back anytime.");
                            button_accept.setVisibility(View.GONE);
                            button_decline.setText("Back");
                        }
                        else {
                            username=response;
                            // Display the first 500 characters of the response string.
                            requests_header.setText("New requests!");
                            question.setText(response+" has requested to access your calendar.\n " +
                                    "Would you like to accept the request?");
                            button_accept.setText("Accept");
                            button_decline.setText("Decline");
                        }

                        Log.d(TAG, "onResponse: clysr jsonp sonuc: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse didnt work_2"+error);
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(firstStringRequest);
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
                        BuildConfig.nowApiUrl+"/accept/"+
                                username+"&"+PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                        .getString("login_preferences",""),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.d(TAG, "onResponse: aynn knks clsyr"
                                        + username
                                        + PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                        .getString("login_preferences",""));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse didnt work");
                    }
                });
                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
                Toast.makeText(ListOfRequestsActivity.this, "You have accepted "+username+"\'s request"+ "!",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

}
