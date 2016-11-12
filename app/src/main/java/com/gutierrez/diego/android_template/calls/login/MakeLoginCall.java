package com.gutierrez.diego.android_template.calls.login;

import android.content.Context;

import com.gutierrez.diego.android_template.services.RestRequest;
import com.gutierrez.diego.android_template.services.RestWebServiceCallable;
import com.gutierrez.diego.android_template.utils.JsonUtil;

import org.json.JSONObject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by diego on 24-10-16.
 */
public class MakeLoginCall implements Callable{

    Context context;
    String params;
    ExecutorService executor = Executors.newFixedThreadPool(1);

    public MakeLoginCall(String params){
        this.params = params;
    }

    @Override
    public String call() throws Exception {

        RestRequest restRequest =  new RestRequest();
        restRequest.setApi("/oauth/token");
        restRequest.setAuthorization("Basic czZCaGRSa3F0NDpnWDRmQmF0M2JW");
        restRequest.setMethod("POST");
        restRequest.setParams(params);


        Callable<String> restWebServiceCallable = new RestWebServiceCallable(context, restRequest);
        Future<String> future = executor.submit(restWebServiceCallable);

        String response = future.get();

        return response;

    }
}
