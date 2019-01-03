package com.example.ahmetanilgur.fakearmut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
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
import org.w3c.dom.Text;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
public Context mContext;
public ArrayList<ProfileSingleItemUser> mProfileSingleItemUser;
public RequestQueue mProfileQueue;

private final ItemClickListener mOnClickListener;
public ProfileAdapter(Context context, ArrayList<ProfileSingleItemUser> userList, ItemClickListener listener){
        mContext = context;
        mProfileSingleItemUser = userList;
        mOnClickListener = listener;

        }
@NonNull
@Override
public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mProfileQueue = Volley.newRequestQueue(mContext);
    View view = LayoutInflater.from(mContext).inflate(R.layout.cv_profile_single_item, parent, false);
        ProfileViewHolder viewHolder = new ProfileViewHolder(view,mOnClickListener);
        return viewHolder;
        }
@SuppressLint("ResourceAsColor")
@Override
public void onBindViewHolder(@NonNull ProfileViewHolder holder, final int i) {
        ProfileSingleItemUser currentItem  = mProfileSingleItemUser.get(i);
        String requester = currentItem.getRequester();
        String requestedday = currentItem.getRequestedDay();
        holder.mTextViewRequester.setText(requester);
        holder.mButtonDeleteRes.setClickable(true);
        holder.mButtonDeleteRes.setTag(requester+"&"+requestedday);
        holder.mTextViewRequestedDay.setText(requestedday);

        }

@Override
public int getItemCount() {
        return mProfileSingleItemUser.size();
        }

public class ProfileViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    public TextView mTextViewRequester;
    public TextView mTextViewRequestedDay;
    public Button mButtonDeleteRes;


    public ItemClickListener mListener;

    public ProfileViewHolder(@NonNull View itemView, ItemClickListener listener) {
        super(itemView);
        mTextViewRequester = itemView.findViewById(R.id.tv_profile_single_item_requester);
        mTextViewRequestedDay = itemView.findViewById(R.id.tv_profile_single_item_requestedday);
        mButtonDeleteRes = itemView.findViewById(R.id.button_profile_delete);
        mListener = listener;
        mButtonDeleteRes.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // Log.d(TAG, "onClick: "+ ((Button) v).getTag());
        String userUrl = BuildConfig.nowApiUrl+"/canceldayrequest/" + PreferenceManager
                .getDefaultSharedPreferences(mContext.getApplicationContext())
                .getString("login_preferences", "") + "&" + ((Button) v).getTag();
        Log.d(TAG, "userUrl: " + userUrl);
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, userUrl, null,
                        new Response.Listener <JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "json response: " + response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mProfileQueue.add(request);
       ((Button) v).setText("Cancelled");
        ((Button) v).setTextColor(Color.GREEN);
        mProfileQueue.add(request);
        Toast.makeText(mContext, "You have cancelled your appointment!",
                Toast.LENGTH_LONG).show();
    }
    }
}
