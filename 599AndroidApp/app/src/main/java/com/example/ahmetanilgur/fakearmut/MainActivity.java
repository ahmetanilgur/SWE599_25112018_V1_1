package com.example.ahmetanilgur.fakearmut;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmetanilgur.fakearmut.data.UserContract;
import com.example.ahmetanilgur.fakearmut.data.UserDBHelper;
import com.example.ahmetanilgur.fakearmut.utilities.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickListener{
    private SQLiteDatabase mDatabase;
    private static final String TAG = "ye";
    private RecyclerView mUserRecyclerView;
    private UserAdapter mUserAdapter;
    private ArrayList<SingleItemUser> mSingleItemUser;
    private RequestQueue mUserRequestQueue;
    private TextView mTextMessage;
    public ItemClickListener mItemClickListener;
    ContentResolver mResolver;

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
    public void onListItemClick(int clickedItemIndex) {
        Log.v("onClick","Item#"+Integer.toString(clickedItemIndex));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserDBHelper dbHelper = new UserDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        mUserRecyclerView = findViewById(R.id.rv_users);
        mUserRecyclerView.setHasFixedSize(true);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSingleItemUser = new ArrayList<>();
        mUserRequestQueue = Volley.newRequestQueue(this);
        mUserAdapter = new UserAdapter(MainActivity.this, mSingleItemUser, this);
        mUserRecyclerView.setAdapter(mUserAdapter);
        Context context = getApplicationContext();
        mResolver = context.getContentResolver();
        SharedPreferences sortPref = PreferenceManager.getDefaultSharedPreferences(this);
        String cityString = sortPref.getString("sort_city","IST.");
        String priceString = sortPref.getString("sort_price","Ascending prices");
        String jobString = sortPref.getString("sort_job","House Painting");
        parseJSON(cityString, priceString, jobString);
        Log.d(TAG, "onCreate: buildconfig dalgası: "+BuildConfig.nowApiUrl);
        showUsers();
        checkforDayReq();
        checkforReq();
    }
  Uri insertNewUser (String name, String job, String city, String price,
                              String employer, String monday, String tuesday, String wednesday,
                              String thursday, String friday, String saturday, String sunday)
    {
        ContentValues cv = new ContentValues();
        cv.put(UserContract.UserEntry.COLUMN_NAME, name);
        cv.put(UserContract.UserEntry.COLUMN_JOB, job);
        cv.put(UserContract.UserEntry.COLUMN_CITY, city);
        cv.put(UserContract.UserEntry.COLUMN_PRICE, price);
        cv.put(UserContract.UserEntry.COLUMN_ISEMPLOYER, employer);
        cv.put(UserContract.UserEntry.COLUMN_MONDAY, monday);
        cv.put(UserContract.UserEntry.COLUMN_TUESDAY, tuesday);
        cv.put(UserContract.UserEntry.COLUMN_WEDNESDAY, wednesday);
        cv.put(UserContract.UserEntry.COLUMN_THURSDAY, thursday);
        cv.put(UserContract.UserEntry.COLUMN_FRIDAY, friday);
        cv.put(UserContract.UserEntry.COLUMN_SATURDAY, saturday);
        cv.put(UserContract.UserEntry.COLUMN_SUNDAY, sunday);

        return getContentResolver().insert(UserContract.UserEntry.CONTENT_URI, cv);
    }

 void showUsers (){
     //JSONArray userAvailableDays = new JSONArray();
     Cursor cursor =getContentResolver().query(UserContract.UserEntry.CONTENT_URI,null,null,null,null);
    for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
        String userName = cursor.getString(cursor.getColumnIndex("name"));
        String userJob = cursor.getString(cursor.getColumnIndex("job"));
        String userCity = cursor.getString(cursor.getColumnIndex("city"));
        String userPrice = cursor.getString(cursor.getColumnIndex("price"));
        String buttonMonday = cursor.getString(cursor.getColumnIndex("monday"));
        String buttonTuesday = cursor.getString(cursor.getColumnIndex("tuesday"));
        String buttonWednesday = cursor.getString(cursor.getColumnIndex("wednesday"));
        String buttonThursday = cursor.getString(cursor.getColumnIndex("thursday"));
        String buttonFriday = cursor.getString(cursor.getColumnIndex("friday"));
        String buttonSaturday = cursor.getString(cursor.getColumnIndex("saturday"));
        String buttonSunday = cursor.getString(cursor.getColumnIndex("sunday"));

     /*   mSingleItemUser.add(new SingleItemUser(userName, userJob, userCity,
                userPrice, userAvailableDays,
                buttonMonday, buttonTuesday, buttonWednesday,
                buttonThursday, buttonFriday, buttonSaturday, buttonSunday));
        Log.d(TAG, "showUsers: "+ cursor.getString(cursor.getColumnIndex("city")));*/
    }
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
        if (id == R.id.calendar_requests) {
            Intent intent = new Intent(MainActivity.this, ListOfRequestsActivity.class);
            startActivity(intent);
        }
       if (id == R.id.day_calendar_requests) {
            Intent intent = new Intent(MainActivity.this, ListOfDayRequestsActivity.class);
            startActivity(intent);
        }
        /*if(id == R.id.accepted_requests){
            Intent intent = new Intent(MainActivity.this, AcceptedActivity.class);
            startActivity(intent);
        }*/
        if(id == R.id.profile){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    private void parseJSON(String cityPref, String pricePref, String jobPref) {

        String userUrl = BuildConfig.nowApiUrl+"/user/filter_3/"+cityPref+"&"+pricePref+"&"+jobPref;
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
                                        JSONArray userAvailableDays = hit.getJSONArray("availableDays");
                                        String buttonMonday = userAvailableDays.getString(0);
                                        String buttonTuesday = userAvailableDays.getString(1);
                                        String buttonWednesday = userAvailableDays.getString(2);
                                        String buttonThursday = userAvailableDays.getString(3);
                                        String buttonFriday= userAvailableDays.getString(4);
                                        String buttonSaturday = userAvailableDays.getString(5);
                                        String buttonSunday = userAvailableDays.getString(6);
                                        insertNewUser(userName,userJob,userCity,userPrice,"false",
                                                buttonMonday,buttonTuesday,buttonWednesday,buttonThursday,
                                                buttonFriday,buttonSaturday,buttonSunday);
                                        mSingleItemUser.add(new SingleItemUser(userName, userJob, userCity,
                                                userPrice, userAvailableDays,
                                                buttonMonday, buttonTuesday, buttonWednesday,
                                                buttonThursday, buttonFriday, buttonSaturday, buttonSunday));
                                        Log.d(TAG, "onResponse: "+ jsonArray.getJSONObject(i)+ " "+i);
                                    }
                                    mUserAdapter = new UserAdapter(MainActivity.this, mSingleItemUser, mItemClickListener);
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
    String username;
    JSONArray dayreqjsonarray;

    private void checkforDayReq(){

        StringRequest firstStringRequest = new StringRequest(Request.Method.GET,
                BuildConfig.nowApiUrl+"/requesteddaylist/"+PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext())
                        .getString("login_preferences",""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dayreqjsonarray = new JSONArray(response);
                            Log.d(TAG, "onResponseza: "+ dayreqjsonarray);
                            //   Log.d(TAG, "onResponse: try: "+dayreqjsonarray.getString(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG, "reel response: "+dayreqjsonarray.length());
                        if(dayreqjsonarray.length()<2){
                            //2 yazmasının nedeni 1 ise null geliyor 2 ise username ve day
                            Log.d(TAG, "onResponse: girdi ife");
                        }
                        else {
                            try {
                                username=dayreqjsonarray.getString(1);
     //                           Log.d(TAG, "onResponseelsetry: "+ username);
                                NotificationUtils.remindUserBecauseCharging(getApplicationContext(), username);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Display the first 500 characters of the response string.

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse didnt work_requesteddaylist "+error);
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(firstStringRequest);
    }

    private void checkforReq(){

        StringRequest sr = new StringRequest(Request.Method.GET,
                BuildConfig.nowApiUrl+"/requestlist/"+PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext())
                        .getString("login_preferences",""),
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.d(TAG, "reel response: "+response.length());
                        if(response.length()<1){
                            Log.d(TAG, "onResponse: girdi ife");
                        }
                        else {
                            username=response;
                            NotificationUtils.remindUserForANewRequest(getApplicationContext(), username);
                        }
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse didnt work_2"+error);
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(sr);
    }



}
