package com.itxiaox.android.xutils.file;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JsonUtils {

    private static Gson  gson;
    static {
        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()//开启map序列化
                .create();
    }
    public static <T> T jsonToBean(String json,Class<T> clazz){
        return gson.fromJson(json,clazz);
    }

    public static <T> String beanToJson(T t){
        return gson.toJson(t);
    }

    public static String mapToJson(Map map){
        return  gson.toJson(map);
    }

    public static Map jsonToMap(String mapJson){
        return gson.fromJson(mapJson,Map.class);
    }
    public static <T> JSONArray list2JsonArray(List<T> list){
        JSONArray jsonArray = new JSONArray();
        for (T t: list) {
            jsonArray.put(t);
        }
        return jsonArray;
    }
    public static <T> List<T> jsonArrayToBean(JSONArray jsonArray,Class<T> tClass){
        List<T> list = new ArrayList<>();
        if (jsonArray!=null&&jsonArray.length()>0){
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                T t = gson.fromJson(jsonObject.toString(),tClass);
                list.add(t);
            }
        }
        return list;
    }
    public static boolean isJson(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonArray()) {
            return false;
        }
        return true;
    }
    public static boolean isJSONArray(String string){
        if (TextUtils.isEmpty(string)){
            return false;
        }
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(string);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonArray()) {
            return false;
        }
        return true;
    }
    public static boolean isJSONObject(String string){
        if (TextUtils.isEmpty(string)){
            return false;
        }
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(string);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

    public static JsonObject beanToJSONObject(Object tClass)  {
        String str = gson.toJson(tClass);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(str);
        sort(jsonObject);
        return jsonObject;
    }

    private static Comparator<String> getComparator() {
        Comparator<String> c = new Comparator<String>() {
            public int compare(String o1, String o2)
            {
                return o1.compareTo(o2);
            }
        };

        return c;
    }

    public static void sort(JsonElement e)
    {
        if (e.isJsonNull())
        {
            return;
        }

        if (e.isJsonPrimitive())
        {
            return;
        }

        if (e.isJsonArray())
        {
            JsonArray a = e.getAsJsonArray();
            for (Iterator<JsonElement> it = a.iterator(); it.hasNext();)
            {
                sort(it.next());
            }
            return;
        }

        if (e.isJsonObject())
        {
            Map<String, JsonElement> tm = new TreeMap<String, JsonElement>(getComparator());
            for (Map.Entry<String, JsonElement> en : e.getAsJsonObject().entrySet())
            {
                tm.put(en.getKey(), en.getValue());
            }

            for (Map.Entry<String, JsonElement> en : tm.entrySet())
            {
                e.getAsJsonObject().remove(en.getKey());
                e.getAsJsonObject().add(en.getKey(), en.getValue());
                sort(en.getValue());
            }
            return;
        }
    }

}
