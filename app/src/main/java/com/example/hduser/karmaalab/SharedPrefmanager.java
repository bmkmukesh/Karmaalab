package com.example.hduser.karmaalab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefmanager {

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "";
    private static final String KEY_GENDER = "";
    private static final String KEY_TOKEN = "keytoken";


    private static SharedPrefmanager mInstance;
    private static Context mCtx;

    private SharedPrefmanager(Context context) {
        mCtx = context;
    }


    public static synchronized SharedPrefmanager getInstance(Context context){

        if(mInstance==null){
            mInstance = new SharedPrefmanager(context);
        }
        return mInstance;
    }

    public void userLogin(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_TOKEN, user.getToken());
       //editor.putString(KEY_USERNAME, user.getUsername());
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(KEY_TOKEN, null) !=null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_TOKEN, -1)
        );
    }

    public void logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }

}
