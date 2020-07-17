package com.itxiaox.java.utils.data;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertUtilsTest extends TestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testToInt() {
        int ret = ConvertUtils.toInt(6.6f, 0);
        System.out.println("ret:" + ret);
    }

    public void testByteToHexString() {
    }

    @Test
    public void testListToString() {
        List<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        String result = ConvertUtils.listToString(list);
        System.out.println("list:" + result);
    }

    @Test
    public void testMapToString() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", 45);
        map.put("key3", 0.5f);
        System.out.println("map:" + map);
        System.out.println("map2:" + ConvertUtils.mapToString(map));

    }

    @Test
    public void testFloatToDouble() {
        double d = 0.32;
        float f = ConvertUtils.doubleToFloat(d);
        System.out.println("f:" + f);
//        Assert.assertTrue(f == 0.32);
    }

    @Test
    public void testDoubleFloat() {
        float f = 1.2f;
        double d = ConvertUtils.floatToDouble(f);
        System.out.println("d:" + (double)f);
    }
}