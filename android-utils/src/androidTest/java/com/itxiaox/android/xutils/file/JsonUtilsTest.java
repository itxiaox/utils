package com.itxiaox.android.xutils.file;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.JsonArray;
import com.itxiaox.android.xutils.data.JsonConvert;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class JsonUtilsTest {


    private static final String TAG = "JsonUtilsTest";

    @Before
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
    }
    class User {
        private String name;
        private Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Test
    public void testJsonConvert(){
        List list = new ArrayList();
        list.add(new User("张", 30));
        list.add(new User("李2", 25));
        list.add(new User("昂2", 24));
//        list.add("sss1");
//        list.add("sss2");
//        list.add("sss3");
//        list.add("sss4");
        JsonArray jsonArray = JsonUtils.list2JsonArray(list);
        Log.d(TAG, "testJsonConvert:jsonArray= "+jsonArray);
        try{
            JSONArray jsonArray1 = JsonConvert.jsonArrayConvert(jsonArray);
            Log.d(TAG, "JsonArray 转为JSONArray testJsonConvert:JSONArray= "+jsonArray1);

           JsonArray jsonArray2 =  JsonConvert.JSONArrayConvertJsonArray(jsonArray1);

            Log.d(TAG, "testJsonConvert: jsonArray2="+jsonArray2);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
