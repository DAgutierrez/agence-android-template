package com.gutierrez.diego.android_template.services;

/**
 * Created by diego on 17-10-16.
 */
public class RestRequest {

    private String address;
    private String method;
    private String params;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
