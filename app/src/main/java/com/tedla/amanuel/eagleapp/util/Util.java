package com.tedla.amanuel.eagleapp.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dVentus-hq on 7/27/2017.
 */
public class Util {
    public static String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
}
