package com.example.ahmetanilgur.fakearmut;

public class SingleItemUser {
    private String mUserName;
    private String mUserJob;
    private String mUserCity;
    private String mUserPrice;
    public SingleItemUser(String userName,
                          String userJob,
                          String userCity,
                          String userPrice) {
        mUserName = userName;
        mUserJob = userJob;
        mUserPrice = userPrice;
        mUserCity = userCity;
    }
    public String getItemUserName(){
        return mUserName;
    }
    public String getItemUserJob(){
        return mUserJob;
    }
    public String getItemUserCity(){ return mUserCity;}
    public String getItemUserPrice(){ return mUserPrice; }
}
