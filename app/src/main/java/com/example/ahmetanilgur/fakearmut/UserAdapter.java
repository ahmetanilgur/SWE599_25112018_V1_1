package com.example.ahmetanilgur.fakearmut;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    public Context mContext;
    public ArrayList<SingleItemUser> mSingleItemUser;
    private final ItemClickListener mOnClickListener;
    public UserAdapter(Context context, ArrayList<SingleItemUser> userList, ItemClickListener listener){
        mContext = context;
        mSingleItemUser = userList;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                     parent,
                                             int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout
                .cv_user_single_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(view,mOnClickListener);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int
            i) {
        SingleItemUser currentItem  = mSingleItemUser.get(i);
        String userName = currentItem.getItemUserName();
        String userJob = currentItem.getItemUserJob();
        String userCity = currentItem.getItemUserCity();
        String userPrice = currentItem.getItemUserPrice();
        JSONArray userAvailableDays = currentItem.getItemUserAvailableDays();
        holder.mTextViewUserName.setText(userName);
        holder.mTextViewUserJob.setText("Job: "+userJob);
        holder.mTextViewUserPrice.setText("Price: "+userPrice);
        holder.mTextViewUserCity.setText("City: "+userCity);
        holder.mTextViewUserAvailableDays.setText("Available Days");
        try {
            holder.mButtonMonday.setClickable(!Boolean.parseBoolean(userAvailableDays.getString(0)));
            if(!Boolean.parseBoolean(userAvailableDays.getString(0))){
                holder.mButtonMonday.setBackgroundColor(R.color.colorAccent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.mButtonTuesday.setClickable(!Boolean.parseBoolean(userAvailableDays.getString(1)));
            if(!Boolean.parseBoolean(userAvailableDays.getString(1))){
                holder.mButtonTuesday.setBackgroundColor(R.color.colorAccent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.mButtonWednesday.setClickable(!Boolean.parseBoolean(userAvailableDays.getString(2)));
            if(!Boolean.parseBoolean(userAvailableDays.getString(2))){
                holder.mButtonWednesday.setBackgroundColor(R.color.colorAccent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.mButtonThursday.setClickable(!Boolean.parseBoolean(userAvailableDays.getString(3)));
            if(!Boolean.parseBoolean(userAvailableDays.getString(3))){
                holder.mButtonThursday.setBackgroundColor(R.color.colorAccent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.mButtonFriday.setClickable(!Boolean.parseBoolean(userAvailableDays.getString(4)));
            if(!Boolean.parseBoolean(userAvailableDays.getString(4))){
                holder.mButtonFriday.setBackgroundColor(R.color.colorAccent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.mButtonSaturday.setClickable(!Boolean.parseBoolean(userAvailableDays.getString(5)));
            if(!Boolean.parseBoolean(userAvailableDays.getString(5))){
                holder.mButtonSaturday.setBackgroundColor(R.color.colorAccent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.mButtonSunday.setClickable(!Boolean.parseBoolean(userAvailableDays.getString(6)));
            if(!Boolean.parseBoolean(userAvailableDays.getString(6))){
                holder.mButtonSunday.setBackgroundColor(R.color.colorAccent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return mSingleItemUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        public TextView mTextViewUserName;
        public TextView mTextViewUserJob;
        public TextView mTextViewUserPrice;
        public TextView mTextViewUserCity;
        public TextView mTextViewUserAvailableDays;
        public Button mButtonMonday;
        public Button mButtonTuesday;
        public Button mButtonWednesday;
        public Button mButtonThursday;
        public Button mButtonFriday;
        public Button mButtonSaturday;
        public Button mButtonSunday;

        public ItemClickListener mListener;
        public UserViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            mTextViewUserName = itemView.findViewById(R.id.tv_single_item_name);
            mTextViewUserJob = itemView.findViewById(R.id.tv_single_item_job);
            mTextViewUserPrice = itemView.findViewById(R.id.tv_single_item_price);
            mTextViewUserCity = itemView.findViewById(R.id.tv_single_item_city);
            mTextViewUserAvailableDays = itemView.findViewById(R.id.tv_single_item_available_days);
            mButtonMonday = itemView.findViewById(R.id.button_detail_calendar_mon);
            mButtonTuesday = itemView.findViewById(R.id.button_detail_calendar_tue);
            mButtonWednesday = itemView.findViewById(R.id.button_detail_calendar_wed);
            mButtonThursday = itemView.findViewById(R.id.button_detail_calendar_thur);
            mButtonFriday = itemView.findViewById(R.id.button_detail_calendar_fri);
            mButtonSaturday = itemView.findViewById(R.id.button_detail_calendar_sat);
            mButtonSunday = itemView.findViewById(R.id.button_detail_calendar_sun);
            mListener = listener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
        int clickedPosition = getAdapterPosition();
            Intent detailActivityIntent = new Intent(mContext, CalendarActivity.class );
            detailActivityIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(mSingleItemUser.get(clickedPosition).getItemUserName()));
            mContext.startActivity(detailActivityIntent);
        }
    }



}
