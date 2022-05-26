package com.itxiaox.android.xutils.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Author:xiao
 * Time: 2021/5/4 16:03
 * Description:This is JsonConvert
 */
public class JsonConvert {

    public static JSONArray jsonArrayConvert(JsonArray jsonArray) throws JSONException {
        JSONArray array = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
             array = new JSONArray(jsonArray.toString());
        }
        return array;
    }

    public static  JsonArray JSONArrayConvertJsonArray(JSONArray jsonArray){
        JsonArray json = new Gson().fromJson(jsonArray.toString(),JsonArray.class);
        return json;
    }



}
