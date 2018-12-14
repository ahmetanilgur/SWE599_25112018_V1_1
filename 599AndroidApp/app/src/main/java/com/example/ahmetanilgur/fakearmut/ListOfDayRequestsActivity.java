package com.example.ahmetanilgur.fakearmut;

import android.annotation.SuppressLint;
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

import org.json.JSONArray;
import org.json.JSONException;

public class ListOfDayRequestsActivity extends AppCompatActivity {
    String username;
    JSONArray dayreqjsonarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG="listofdayrequestsactivity";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_day_requests);
        final TextView requests_header = findViewById(R.id.day_requests_header);
        final TextView question = findViewById(R.id.day_requests_question);
        final Button button_decline = findViewById(R.id.day_requests_q_decline);
        final Button button_accept = findViewById(R.id.day_requests_q_accept);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StringRequest firstStringRequest = new StringRequest(Request.Method.GET,
                BuildConfig.nowApiUrl+"/requesteddaylist/"+PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext())
                        .getString("login_preferences",""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dayreqjsonarray = new JSONArray(response);
                         //   Log.d(TAG, "onResponse: try: "+dayreqjsonarray.getString(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       Log.d(TAG, "reel response: "+dayreqjsonarray.length());
                        if(dayreqjsonarray.length()<2){
                            //2 yazmasının nedeni 1 ise null geliyor 2 ise username ve day
                            Log.d(TAG, "onResponse: girdi ife");
                            requests_header .setText("No new requests!");
                            question.setText("Come back anytime.");
                            button_accept.setVisibility(View.GONE);
                            button_decline.setText("Back");
                        }
                        else {
                            try {
                                username=dayreqjsonarray.getString(1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Display the first 500 characters of the response string.
                            requests_header.setText("New requests!");
                            try {
                                question.setText(username+" has requested to hire you on "+dayreqjsonarray.getString(0)
                                        +".\n " +
                                        "Would you like to accept the request?");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            button_accept.setText("Accept");
                            button_decline.setText("Decline");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse didnt work_requesteddaylist "+error);
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

                StringRequest stringRequest = null;
                try {
                    stringRequest = new StringRequest(Request.Method.GET,
                            BuildConfig.nowApiUrl+"/acceptdayrequest/"
                                    +username
                                    +"&"
                                    +PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                    .getString("login_preferences","")+"&"+dayreqjsonarray.getString(0),
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
                try {
                    Toast.makeText(ListOfDayRequestsActivity.this, "You have accepted "+username+"\'s request for "+ dayreqjsonarray.getString(0)+"!",
                            Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

}
