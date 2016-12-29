package com.agence.pharma_investi.managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.agence.pharma_investi.MainActivity;

import java.util.HashMap;

/**
 * Created by diego on 27-12-16.
 */
public class SessionManagerImpl implements SessionManager {

    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "token";

    public SessionManagerImpl(Context context) {

        this.context = context;
        this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();


    }

    @Override
    public void createSession (String username, String password, String token) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, username);

        editor.putString(KEY_PASSWORD, password);

        editor.putString(KEY_TOKEN, token);

        editor.commit();

//        Boolean isLogin = pref.getBoolean(IS_LOGIN, false);
//
//        Log.e("log",isLogin.toString());

    }

    @Override public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        // return user
        return user;
    }

    @Override
    public boolean isLoggedIn(){
        Boolean isLogin =  pref.getBoolean(IS_LOGIN, false);
        return pref.getBoolean(IS_LOGIN, false);
    }

    @Override
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


        Intent mainActivity = new Intent(context, MainActivity.class);
        context.startActivity(mainActivity);

    }
}
