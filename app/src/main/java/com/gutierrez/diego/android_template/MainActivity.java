package com.gutierrez.diego.android_template;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.gutierrez.diego.android_template.utils.ScreenUtil;
import com.gutierrez.diego.android_template.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    SessionManager sessionManager;
    Context context;
    AppCompatActivity appCompatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        context = this;

//        appCompatActivity = new AppCompatActivity();
//        ScreenUtil.HideActionBar(appCompatActivity);

        fragmentManager = getSupportFragmentManager();

        sessionManager = new SessionManager(context,fragmentManager);

        sessionManager.checkLogin();


    }
}
