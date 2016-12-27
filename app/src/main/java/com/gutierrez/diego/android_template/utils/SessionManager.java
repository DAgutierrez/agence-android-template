package com.gutierrez.diego.android_template.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;

import com.gutierrez.diego.android_template.MainActivity;
import com.gutierrez.diego.android_template.R;
import com.gutierrez.diego.android_template.activities.HomeActivity;
import com.gutierrez.diego.android_template.fragments.HomeFragment;
import com.gutierrez.diego.android_template.fragments.LoginFragment;

import java.util.HashMap;

/**
 * Created by diego on 24-10-16.
 */
public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    FragmentManager fm;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TOKEN = "token";

    public SessionManager(Context context, FragmentManager fm) {
        this._context = context;
        this.fm = fm;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public  void createLoginSession( String username, String email, String token) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, username);

        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_TOKEN, token);


        editor.commit();
    }

    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {

            fm.beginTransaction()
                    .add(R.id.main_container, new LoginFragment())
                    .commit();
        } else {

//            Intent homeActivity = new Intent(_context, HomeActivity.class);
//            _context.startActivity(homeActivity);

            fm.beginTransaction()
                    .add(R.id.main_container, new HomeFragment())
                    .commit();

        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


        Intent mainActivity = new Intent(_context, MainActivity.class);
        _context.startActivity(mainActivity);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        Boolean bolean = pref.getBoolean(IS_LOGIN, false);
        return pref.getBoolean(IS_LOGIN, false);
    }
}

