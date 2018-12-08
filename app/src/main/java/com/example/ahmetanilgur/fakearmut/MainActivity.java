package com.example.ahmetanilgur.fakearmut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ye";
    private RecyclerView mUserRecyclerView;
    private UserAdapter mUserAdapter;
    private ArrayList<SingleItemUser> mSingleItemUser;
    private RequestQueue mUserRequestQueue;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*
        Toast.makeText(MainActivity.this, "last saved sort was: "+sortString,
           Toast.LENGTH_LONG).show();
        */
        setContentView(R.layout.activity_main);
        mUserRecyclerView = findViewById(R.id.rv_users);
        mUserRecyclerView.setHasFixedSize(true);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSingleItemUser = new ArrayList<>();
        mUserRequestQueue = Volley.newRequestQueue(this);
        mUserAdapter = new UserAdapter(MainActivity.this, mSingleItemUser);
        mUserRecyclerView.setAdapter(mUserAdapter);
        Context context = getApplicationContext();
        SharedPreferences sortPref = PreferenceManager.getDefaultSharedPreferences(this);
        String cityString = sortPref.getString("sort_city","34");
        String priceString = sortPref.getString("sort_price","Ascending prices");
        String jobString = sortPref.getString("sort_job","House Painting");

        parseJSON(cityString, priceString, jobString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.price_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void parseJSON(String cityPref, String pricePref, String jobPref) {

        String userUrl = "https://599api-dvfgrnalif.now.sh/user/filter_3/"+cityPref+"&"+pricePref+"&"+jobPref;
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, userUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("result");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject hit = jsonArray.getJSONObject(i);
                                        String userName = hit.getString("name");
                                        String userJob = hit.getString("job");
                                        String userCity = hit.getString("city");
                                        String userPrice = hit.getString("price");
                                        mSingleItemUser.add(new SingleItemUser(userName, userJob, userCity, userPrice));
                                    }
                                    mUserAdapter = new UserAdapter(MainActivity.this, mSingleItemUser);
                                    mUserRecyclerView.setAdapter(mUserAdapter);
                                    mUserAdapter.notifyDataSetChanged();
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
        mUserRequestQueue.add(request);
    }
}
