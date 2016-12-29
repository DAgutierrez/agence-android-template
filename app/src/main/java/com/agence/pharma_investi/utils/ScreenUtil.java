package com.agence.pharma_investi.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by diego on 21-10-16.
 */
public class ScreenUtil {

    public static void FullScreen(View view) {

        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);

    }

    public static void HideActionBar(AppCompatActivity appCompatActivity) {
        appCompatActivity.getSupportActionBar().hide();
    }
}
