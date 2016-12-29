package com.agence.pharma_investi.presenters.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.agence.pharma_investi.MainActivity;
import com.agence.pharma_investi.calls.client.LoginCall;
import com.agence.pharma_investi.fragments.login.LoginFragment;
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


                    JSONObject responseJson = JsonUtil.parseJsonObject(response);

                    if (!responseJson.has("error")){

                        String token = responseJson.getString("id");

                        sessionManager.createSession(username,password,token);

                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mainView.onSuccessLogin();
                            }
                        });

                    } else {

                        JSONObject error = responseJson.getJSONObject("error");
                        final String errorMessage = error.getString("message");

                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mainView.onErrorLogin(errorMessage);
                            }
                        });

                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainView.onErrorLogin(e.getMessage());
                        }
                    });

                }

            }
        };

    }

}
