package com.example.ahmetanilgur.fakearmut;

import org.json.JSONArray;

import java.util.ArrayList;

public class SingleItemUser {
    private String mUserName;
    private String mUserJob;
    private String mUserCity;
    private String mUserPrice;
    private String mButtonMonday;
    private String mButtonTuesday;
    private String mButtonWednesday;
    private String mButtonThursday;
    private String mButtonFriday;
    private String mButtonSaturday;
    private String mButtonSunday;
    private JSONArray  mUserAvailableDays;
    public SingleItemUser(String userName,
                          String userJob,
                          String userCity,
                          String userPrice,
                          JSONArray userAvailableDays,
                          String buttonMonday,
                          String buttonTuesday,
                          String buttonWednesday,
                          String buttonThursday,
                          String buttonFriday,
                          String buttonSaturday,
                          String buttonSunday) {
        mUserName = userName;
        mUserJob = userJob;
        mUserPrice = userPrice;
        mUserCity = userCity;
        mUserAvailableDays = userAvailableDays;
        mButtonMonday = buttonMonday;
        mButtonTuesday = buttonTuesday;
        mButtonWednesday = buttonWednesday;
        mButtonThursday = buttonThursday;
        mButtonFriday = buttonFriday;
        mButtonSaturday = buttonSaturday;
        mButtonSunday = buttonSunday;
    }
    public String getItemUserName(){
        return mUserName;
    }
    public String getItemUserJob(){
        return mUserJob;
    }
    public String getItemUserCity(){ return mUserCity;}
    public String getItemUserPrice(){ return mUserPrice; }
    public JSONArray getItemUserAvailableDays(){return mUserAvailableDays; }
    public String getButtonMonday() {return mButtonMonday; }
    public String getButtonTuesday() {return mButtonTuesday; }
    public String getButtonWednesday() {return mButtonWednesday; }
    public String getButtonThursday() {return mButtonThursday; }
    public String getButtonFriday() {return mButtonFriday; }
    public String getButtonSaturday() {return mButtonSaturday; }
    public String getButtonSunday() {return mButtonSunday; }

}
