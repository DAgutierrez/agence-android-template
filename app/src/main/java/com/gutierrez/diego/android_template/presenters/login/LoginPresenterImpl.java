package com.gutierrez.diego.android_template.presenters.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.gutierrez.diego.android_template.MainActivity;
import com.gutierrez.diego.android_template.RestClient.RestClient;
import com.gutierrez.diego.android_template.RestClient.RestRequest;
import com.gutierrez.diego.android_template.fragments.login.LoginView;
import com.gutierrez.diego.android_template.managers.SessionManagerImpl;
import com.gutierrez.diego.android_template.utils.ErrorHandlerUtil;
import com.gutierrez.diego.android_template.utils.JsonUtil;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by diego on 27-12-16.
 */
public class LoginPresenterImpl implements LoginPresenter {

    Context context;
    LoginView mainView;
    ExecutorService executor = Executors.newFixedThreadPool(1);
    SessionManagerImpl sessionManager;



    public  LoginPresenterImpl (LoginView mainView,Context context) {

        this.mainView = mainView;
        this.context = context;
        sessionManager =  new SessionManagerImpl(context);

    }

    @Override
    public void login (String username, String password) {

        try {
            String params =
                    "username=" + URLEncoder.encode(username, "UTF-8") +
                            "&password=" + URLEncoder.encode(password, "UTF-8") +
                            "&grant_type=" + URLEncoder.encode("password", "UTF-8");


            RestRequest restRequest =  new RestRequest();
            restRequest.setApi("/oauth/token");
            restRequest.setAuthorization("Basic czZCaGRSa3F0NDpnWDRmQmF0M2JW");
            restRequest.setMethod("POST");
            restRequest.setParams(params);

            Callable<String> restWebServiceCallable = new RestClient(context, restRequest);
            Future<String> future = executor.submit(restWebServiceCallable);

            String response = future.get();

            String err = ErrorHandlerUtil.checkError(response);

            if(err != null) {

                mainView.onErrorLogin(err);

            } else {

                JSONObject responseJson = JsonUtil.parseJsonObject(response);

                try {

                    String token = responseJson.getString("access_token");

                    sessionManager.createSession(username,password,token);

                    mainView.onSuccessLogin();

                } catch (Exception e) {
                    e.printStackTrace();
                    mainView.onErrorLogin(e.getMessage());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            mainView.onErrorLogin(e.getMessage());
        }

    }


}
