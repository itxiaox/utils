package com.itxiaox.android.xutils;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.itxiaox.android.xutils.file.FileUtils;
import com.itxiaox.android.xutils.file.JsonUtils;
import com.itxiaox.android.xutils.file.JsonUtilsTest;
import com.itxiaox.android.xutils.log.LogUtils;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class FileUtilsTest {
    private static final String TAG = "FileUtilsTest";
    Context appContext;
    @Before
    public void useAppContext() {
        // Context of the app under test.
         appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.itxiaox.android.xutils.test", appContext.getPackageName());
    }
    @Test
    public void testSave(){
        String content = "Hello World";
        String filePath = appContext.getExternalCacheDir().getAbsolutePath()+"hello.txt";
//        boolean result = FileUtils.save(filePath,content);
       FileUtils.writeFile(filePath,content,false);
       LogUtils.d_format("filePath=%s ",filePath);

    }

    @Test
    public void testReadFile(){
        String filePath = appContext.getExternalCacheDir().getAbsolutePath()+"hello.txt";
        String conent = FileUtils.readFile(filePath).trim();
        LogUtils.d_format("conent=%s ",conent);
    }
    @Test
    public void testDebug(){
        for(int i=0;i<10;i++){
            int selector = i;
            System.out.println("i="+selector);
            //调用方法
            stepNext(i);
        }
    }

    public int stepNext(int i){
        return i*2;
    }
    class User {
        private String name;
        private Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }
    @Test
    public void testList2JsonArray() {
        List list = new ArrayList();
        list.add(new User("张", 30));
        list.add(new User("李2", 25));
        list.add(new User("昂2", 24));

        JSONArray jsonArray = JsonUtils.list2JsonArray(list);

        System.out.println("jsonarray:" + jsonArray);
    }

}
