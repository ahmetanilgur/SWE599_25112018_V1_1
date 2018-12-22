package com.example.ahmetanilgur.fakearmut;

import org.json.JSONArray;

public class ProfileSingleItemUser {

    private String mRequester;
    private String mRequestedDay;
    public ProfileSingleItemUser
            (String requester,
             String requestedDay)
    {
        mRequester = requester;
        mRequestedDay = requestedDay;

    }
    public String getRequester(){
        return mRequester;
    }
    public String getRequestedDay(){
        return mRequestedDay;
    }
}
