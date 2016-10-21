package com.gutierrez.diego.android_template.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gutierrez.diego.android_template.MainActivity;
import com.gutierrez.diego.android_template.R;
import com.gutierrez.diego.android_template.TestingActivity;
import com.gutierrez.diego.android_template.calls.home.GetUsersHomeCall;
import com.gutierrez.diego.android_template.elements.ProgressDialogElement;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by diego on 20-10-16.
 */
public class LoginFragment extends Fragment {

    Context context;
    ProgressDialogElement progressDialogElement;
    LoadScreen loadScreen;
    String userText;
    Handler handler;
    TextView txtLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();


    }

    @Override
    public View onCreateView(final LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState)
    {
        progressDialogElement =  new ProgressDialogElement(context, "Probando");
        progressDialogElement.show();

        return inflater.inflate(R.layout.fragment_login, container, false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            txtLogin =  (TextView) view.findViewById(R.id.txtLogin);

    }

    @Override
    public void onResume() {
        super.onResume();

        loadScreen = new LoadScreen();
        loadScreen.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                progressDialogElement.dismiss();
                LoadView();
            }
        };
    }

    public void LoadView() {

        txtLogin.setText(userText);
    }

    private class LoadScreen extends Thread  {

        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final Callable<String> getUsersHome = new GetUsersHomeCall();

        public void run() {

            try {

                Future<String> users = executor.submit(getUsersHome);
                userText = users.get();

                Message msg = new Message();
                handler.sendMessage(msg);


            } catch(Exception e) {
                e.printStackTrace();
            }

        }

    }


}
