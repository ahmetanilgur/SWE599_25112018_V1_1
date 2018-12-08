package com.example.ahmetanilgur.fakearmut;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    public Context mContext;
    public ArrayList<SingleItemUser> mSingleItemUser;
    public UserAdapter(Context context, ArrayList<SingleItemUser> userList){
        mContext = context;
        mSingleItemUser = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                     parent,
                                             int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout
                .cv_user_single_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int
            i) {
        SingleItemUser currentItem  = mSingleItemUser.get(i);
        String userName = currentItem.getItemUserName();
        String userJob = currentItem.getItemUserJob();
        String userCity = currentItem.getItemUserCity();
        String userPrice = currentItem.getItemUserPrice();
        holder.mTextViewUserName.setText(userName);
        holder.mTextViewUserJob.setText("Job: "+userJob);
        holder.mTextViewUserPrice.setText("Price: "+userPrice);
        holder.mTextViewUserCity.setText("City: "+userCity);

    }

    @Override
    public int getItemCount() {
        return mSingleItemUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewUserName;
        public TextView mTextViewUserJob;
        public TextView mTextViewUserPrice;
        public TextView mTextViewUserCity;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewUserName = itemView.findViewById(R.id.tv_single_item_name);
            mTextViewUserJob = itemView.findViewById(R.id.tv_single_item_job);
            mTextViewUserPrice = itemView.findViewById(R.id.tv_single_item_price);
            mTextViewUserCity = itemView.findViewById(R.id.tv_single_item_city);
        }
    }


}
