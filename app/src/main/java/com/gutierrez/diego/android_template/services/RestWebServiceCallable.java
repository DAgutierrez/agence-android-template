package com.gutierrez.diego.android_template.services;

import android.content.Context;

import com.gutierrez.diego.android_template.MainActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by diego on 17-10-16.
 */
public class RestWebServiceCallable  implements Callable<String> {

    Context context;
    RestRequest restRequest;

    int CONNECT_TIMEOUT_VALUE = 10000;
    int TIMEOUT_VALUE = 120000;



    public RestWebServiceCallable(Context context, RestRequest restRequest){
        super();
        this.context = context;
        this.restRequest = restRequest;
    }

    @Override
    public String call() throws Exception {

        String address = restRequest.getAddress();
        String method = restRequest.getMethod();
        String requestParams = restRequest.getParams();
        String token = "f0f8da77771cd4ed2019c0a5efc30fa2516674c8";

        String responseString = "";

        try {

            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setConnectTimeout(CONNECT_TIMEOUT_VALUE);
            conn.setReadTimeout(TIMEOUT_VALUE);
            conn.addRequestProperty("Authorization", "Bearer " + token);


//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
//            bw.write(requestParams);
//            bw.flush();
//            bw.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            responseString = response.toString();

            System.out.println("response =>" + responseString);


        } catch(Exception e) {
            e.printStackTrace();
        }

        return responseString;


    }
}
