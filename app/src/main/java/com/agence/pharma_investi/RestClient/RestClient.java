package com.agence.pharma_investi.RestClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by diego on 17-10-16.
 */
public class RestClient implements Callable<String> {


    RestRequest restRequest;

    int CONNECT_TIMEOUT_VALUE = 10000;
    int TIMEOUT_VALUE = 120000;


    public RestClient(RestRequest restRequest){
        super();
        this.restRequest = restRequest;
    }

    @Override
    public String call() throws Exception {

        String address = RestClientConfig.SERVER_ADDRESS + restRequest.getApi();
        String method = restRequest.getMethod();
        String requestParams = restRequest.getParams();
        String authorization = restRequest.getAuthorization();

        String responseString = "";

        try {

            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(method);
            conn.setConnectTimeout(CONNECT_TIMEOUT_VALUE);
            conn.setReadTimeout(TIMEOUT_VALUE);
            conn.addRequestProperty("Authorization", authorization);
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if(requestParams != null) {

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                bw.write(requestParams);
                bw.flush();
                bw.close();
            }

            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending '"+ method +"' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            if(responseCode == 200) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                responseString = response.toString();

            } else {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer responseError = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseError.append(inputLine);
                }

                return responseError.toString();
            }

        } catch(Exception e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }

        return responseString;

    }
}
