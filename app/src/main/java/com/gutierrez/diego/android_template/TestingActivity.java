package com.gutierrez.diego.android_template;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gutierrez.diego.android_template.calls.home.GetUsersHomeCall;
import com.gutierrez.diego.android_template.elements.ProgressDialogElement;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestingActivity extends AppCompatActivity {

    String userText = "";
    static Context context;
    ProgressDialogElement progressDialogElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        context =  this;

    }


    @Override
    protected void onResume() {
        super.onResume();

        progressDialogElement =  new ProgressDialogElement(this, "Probando");
        progressDialogElement.show();

        LoadScreen loadScreen = new LoadScreen();
        loadScreen.start();

    }

    public void LoadView() {

        TextView txt =  (TextView) findViewById(R.id.txtTesting);
        txt.setText(userText);

    }

    private class LoadScreen extends Thread  {

        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final Callable<String> getUsersHome = new GetUsersHomeCall();

        public void run() {

            try {

                Future<String> users = executor.submit(getUsersHome);
                userText = users.get();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progressDialogElement.dismiss();
                        LoadView();

                    }
                });


            } catch(Exception e) {
                e.printStackTrace();
            }

        }

    }


}
