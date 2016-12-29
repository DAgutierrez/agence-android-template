package com.agence.pharma_investi.elements;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by diego on 18-10-16.
 */
public  class ProgressDialogElement {

    private String message;
    private Context context;
    private ProgressDialog progressDialog;


    public ProgressDialogElement (Context context, String message){
        this.context = context;
        this.message = message;
        this.progressDialog = new ProgressDialog(context);
    }

    public  void showSpinner() {

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    public void dismiss() {
        progressDialog.dismiss();
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
