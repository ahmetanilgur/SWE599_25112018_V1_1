package com.example.ahmetanilgur.fakearmut;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int
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

    public class UserViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        public TextView mTextViewUserName;
        public TextView mTextViewUserJob;
        public TextView mTextViewUserPrice;
        public TextView mTextViewUserCity;
        public ItemClickListener mListener;

        public UserViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            mTextViewUserName = itemView.findViewById(R.id.tv_single_item_name);
            mTextViewUserJob = itemView.findViewById(R.id.tv_single_item_job);
            mTextViewUserPrice = itemView.findViewById(R.id.tv_single_item_price);
            mTextViewUserCity = itemView.findViewById(R.id.tv_single_item_city);
            mListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        int clickedPosition = getAdapterPosition();
            Intent detailActivityIntent = new Intent(mContext, CalendarActivity.class );
            detailActivityIntent.putExtra(Intent.EXTRA_TEXT, clickedPosition);
            mContext.startActivity(detailActivityIntent);

        }

    }



}
