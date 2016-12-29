package com.agence.pharma_investi.fragments.login;

import android.content.Context;

/**
 * Created by diego on 27-12-16.
 */
public interface LoginView {

    public void onErrorLogin(String error);

    public void onSuccessLogin();
}
