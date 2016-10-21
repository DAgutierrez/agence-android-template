package com.gutierrez.diego.android_template.calls.home;

import android.content.Context;

import com.gutierrez.diego.android_template.services.RestRequest;
import com.gutierrez.diego.android_template.services.RestWebService;
import com.gutierrez.diego.android_template.services.RestWebServiceCallable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by diego on 17-10-16.
 */
public class GetUsersHomeCall implements Callable{

    Context context;

    public GetUsersHomeCall (){
    }

    @Override
    public String call() throws Exception {

        RestRequest restRequest =  new RestRequest();
        restRequest.setAddress("http://192.168.1.149:3000/users/validateUser");
        restRequest.setMethod("GET");

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> restWebServiceCallable = new RestWebServiceCallable(context, restRequest);
        Future<String> future = executor.submit(restWebServiceCallable);

        String response = future.get();

        return response;


    }
}
