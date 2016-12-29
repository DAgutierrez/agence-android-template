package com.agence.pharma_investi.utils;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by diego on 25-10-16.
 */
public class ErrorHandlerUtil {

    Context context;

    public ErrorHandlerUtil(Context context) {
        this.context =  context;
    }

    public static String checkError(String response) {

        String err = null;

        if(stringMatch("Error",response)) {
            if(stringMatch("User credentials are invalid", response)) {
                err = "Credenciales invalidas";
            } else if (stringMatch("failed to connect", response)) {
                err =  "Error de conexión";
            } else {
                err = "Error Desconocido";
            }
        }

        return err;
    }


    public static Boolean stringMatch (String text, String stringText ) {

        Pattern pattern = Pattern.compile(".*"+text+".*");
        Matcher matcher = pattern.matcher(stringText);

        if (matcher.matches()) {
            return true;
        } else {
            return  false;
        }

    }
}
