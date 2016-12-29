package com.agence.pharma_investi.calls.client;

import com.agence.pharma_investi.RestClient.RestClient;
import com.agence.pharma_investi.RestClient.RestRequest;

import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by diego on 29-12-16.
 */

public class LoginCall implements Callable<String> {

    String username;
    String password;
    ExecutorService executor = Executors.newFixedThreadPool(1);

    public LoginCall(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String call() throws Exception {

        String params =
                "username=" + URLEncoder.encode(username, "UTF-8") +
                "&password=" + URLEncoder.encode(password, "UTF-8");

        RestRequest restRequest =  new RestRequest();
        restRequest.setApi("/Clients/login");
        restRequest.setMethod("POST");
        restRequest.setParams(params);

        Callable<String> restClient = new RestClient(restRequest);
        Future<String> future = executor.submit(restClient);

        String response = future.get();

        return  response;

    }
}
