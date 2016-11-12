package com.gutierrez.diego.android_template.utils;

import org.json.JSONObject;

/**
 * Created by diego on 25-10-16.
 */
public class JsonUtil {


    public static JSONObject parseJsonObject (String raw) {

        JSONObject object = null;

        try{
            object = new JSONObject(raw);
        }catch(Exception e){
            e.printStackTrace();
        }

        return object;

    }
}
