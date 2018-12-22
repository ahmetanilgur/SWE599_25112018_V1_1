package com.example.ahmetanilgur.fakearmut;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements ItemClickListener{
    private ArrayList<ProfileSingleItemUser> mProfileSingleItemUser;
    private RequestQueue mProfileUserRequestQueue;
    private ProfileAdapter mProfileAdapter;
    private RecyclerView mProfileRecyclerView;
    public ItemClickListener mProfileItemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProfileSingleItemUser = new ArrayList<>();
        mProfileUserRequestQueue= Volley.newRequestQueue(this);
        mProfileRecyclerView = findViewById(R.id.rv_profile);
        mProfileRecyclerView.setHasFixedSize(true);
        mProfileRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProfileRecyclerView.setAdapter(mProfileAdapter);
        mProfileAdapter = new ProfileAdapter(ProfileActivity.this, mProfileSingleItemUser, this);

        Log.d("dd", "onCreate: "+PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString("login_preferences",""));
        parseProfileJSON();
    }
    private void parseProfileJSON() {

        String userUrl = "http://192.168.42.174:8000/profile/"+PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString("login_preferences","");
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, userUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("result");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject hit = jsonArray.getJSONObject(i);
                                        String requester = hit.getString("requester");
                                        String requestedday = hit.getString("requestedDay");
                                        mProfileSingleItemUser.add(new ProfileSingleItemUser(requester,requestedday));
                                        //String TAG="iksde";
                                        //Log.d(TAG, "onResponse: "+ jsonArray.getJSONObject(i)+ " "+i);
                                    }
                                    mProfileAdapter = new ProfileAdapter(ProfileActivity.this, mProfileSingleItemUser, mProfileItemClickListener);
                                    mProfileRecyclerView.setAdapter(mProfileAdapter);
                                    mProfileAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mProfileUserRequestQueue.add(request);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.v("onClick","Item#"+Integer.toString(clickedItemIndex));

    }
}
