package com.example.ahmetanilgur.fakearmut.utilities;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmetanilgur.fakearmut.BuildConfig;
import com.example.ahmetanilgur.fakearmut.ListOfRequestsActivity;

import org.json.JSONArray;
import org.json.JSONException;

public class ActionReceiver extends BroadcastReceiver {

    final String TAG="actionreceiver";
    String username;
    JSONArray dayreqjsonarray;

    @Override
    public void onReceive(final Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String action=intent.getStringExtra("action");
        if(action.equals("accept")){

            notificationManager.cancel(NotificationUtils.DAY_REQUEST_NOTIFICATION_ID);

            StringRequest firstStringRequest = new StringRequest(Request.Method.GET,
                    BuildConfig.nowApiUrl+"/requesteddaylist/"+PreferenceManager
                            .getDefaultSharedPreferences(context)
                            .getString("login_preferences",""),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                dayreqjsonarray = new JSONArray(response);
                                Log.d(TAG, "onResponse: try: "+dayreqjsonarray.getString(0));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "reel response: "+dayreqjsonarray.length());
                            if(dayreqjsonarray.length()<2){
                                Toast.makeText(context, "Sorry, there are no day requests available to accept!",
                                        Toast.LENGTH_LONG).show();
                            }
                            else {
                                try {
                                    username=dayreqjsonarray.getString(1);
                                    Log.d("username", username);
                                    Log.d("dayreq", dayreqjsonarray.toString());

                                    StringRequest stringRequest = null;
                                    try {
                                        stringRequest = new StringRequest(Request.Method.GET,
                                                BuildConfig.nowApiUrl+"/acceptdayrequest/"
                                                        +username
                                                        +"&"
                                                        +PreferenceManager.getDefaultSharedPreferences(context)
                                                        .getString("login_preferences","")+"&"+dayreqjsonarray.getString(0),
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        // Display the first 500 characters of the response string.
                                                        Log.d(TAG, "onResponse: aynn knks clsyr"
                                                                + username
                                                                + PreferenceManager.getDefaultSharedPreferences(context)
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
                                    Volley.newRequestQueue(context).add(stringRequest);
                                    try {
                                        Toast.makeText(context, "You have accepted "+username+"\'s day request for "+ dayreqjsonarray.getString(0)+"!",
                                                Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse didnt work_requesteddaylist "+error);
                }
            });
            Volley.newRequestQueue(context).add(firstStringRequest);
        } else if(action.equals("acceptReq")) {
            StringRequest firstStringRequest = new StringRequest(Request.Method.GET,
                    BuildConfig.nowApiUrl+"/requestlist/"+PreferenceManager
                            .getDefaultSharedPreferences(context)
                            .getString("login_preferences",""),
                    new Response.Listener<String>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "reel response: "+response.length());
                            if(response.length()<1){
                                Log.d(TAG, "There are no new requests for you to accept!");
                            }
                            else {
                                username=response;
                                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                        BuildConfig.nowApiUrl+"/accept/"+
                                                username+"&"+PreferenceManager.getDefaultSharedPreferences(context)
                                                .getString("login_preferences",""),
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Display the first 500 characters of the response string.
                                                Log.d(TAG, "oldu"
                                                        + username
                                                        + PreferenceManager.getDefaultSharedPreferences(context)
                                                        .getString("login_preferences",""));
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "onErrorResponse didnt work");
                                    }
                                });
                                Volley.newRequestQueue(context).add(stringRequest);
                                Toast.makeText(context, "You have accepted "+username+"\'s request"+ "!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse didnt work_2"+error);
                }
            });
            Volley.newRequestQueue(context).add(firstStringRequest);
        }

        else if(action.equals("ignore")){
            Toast.makeText(context,"Ignored",Toast.LENGTH_LONG).show();
            notificationManager.cancel(NotificationUtils.DAY_REQUEST_NOTIFICATION_ID);
        }
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

}
