package com.itxiaox.android.xutils.file;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SPUtilsTest {
    private Context appContext;
    private String key = "testSync";
    private String key_async = "testAsync";

    @Before
    public void init() {
        appContext = InstrumentationRegistry.getTargetContext();
        SPUtils.init(appContext, "sp-test");
    }

    @Test
    public void testPutSync() {
        //test ok by xiaox 2020/1/10
        String str = "test";
        SPUtils.putSync("test-string", str);
        float f = 0.2f;
        SPUtils.putSync("test-float", f);
        //不支持double类型数据
//        double d = 0.23;
//        SPUtils.putSync("test-double", d);
        boolean b = true;
        SPUtils.putSync("test-boolean", b);
        int count = 3;
        SPUtils.putSync("test-int",count);
        long lcount = 123456789;
        SPUtils.putSync("test-long",lcount);
//        SPUtils.putSync("test-object", );
    }

    @Test
    public void testPutAsync() {
        //not ok by xiaox 2020/1/10
       String text = (String) SPUtils.get("test-string", "str");
        System.out.println("text:"+text);
        float f = (float) SPUtils.get("test-float", 0f);
        System.out.println("f:"+f);

        double d = 0.00;
        double ds = (double) SPUtils.get("test-double", d);
        System.out.println("d:"+ds);
    }


    @Test
    public void testClearSync() {
        //test ok by xiaox 2020/1/10
        SPUtils.clearSync(key);
    }

    @Test
    public void testClearAllSync() {
        //test ok
        SPUtils.clearAllSync();
    }

    @Test
    public void get() {
        System.out.printf("" + SPUtils.get(key, ""));
    }

    @Test
    public void get1() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void remove1() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void clear1() {
    }

    @Test
    public void contains() {
    }

    @Test
    public void contains1() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getAll1() {
    }
}