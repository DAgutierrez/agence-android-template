package com.agence.pharma_investi.presenters.login;

import android.content.Context;
import android.util.Log;

import com.agence.pharma_investi.calls.client.LoginCall;
import com.agence.pharma_investi.fragments.login.LoginView;
import com.agence.pharma_investi.managers.SessionManagerImpl;
import com.agence.pharma_investi.utils.JsonUtil;

import org.json.JSONObject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by diego on 27-12-16.
 */
public class LoginPresenterImpl implements LoginPresenter{

    Context context;
    LoginView mainView;
    SessionManagerImpl sessionManager;
    ExecutorService executor = Executors.newFixedThreadPool(1);


    public  LoginPresenterImpl (LoginView mainView,Context context) {

        this.mainView = mainView;
        this.context = context;
        sessionManager =  new SessionManagerImpl(context);

    }

    @Override
    public Runnable login (final String username,final String password) {
        return new Runnable() {
            @Override
            public void run() {

                try {

                    Callable<String> loginCall = new LoginCall(username,password);
                    Future<String> future = executor.submit(loginCall);
                    String response = future.get();

                    Log.e("log", response);

                    JSONObject responseJson = JsonUtil.parseJsonObject(response);

                    try {

                        String token = responseJson.getString("access_token");

                        sessionManager.createSession(username,password,token);

                        mainView.onSuccessLogin();

                    } catch (Exception e) {
                        e.printStackTrace();
                        mainView.onErrorLogin(e.getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    mainView.onErrorLogin(e.getMessage());
                }

            }
        };

    }

}
