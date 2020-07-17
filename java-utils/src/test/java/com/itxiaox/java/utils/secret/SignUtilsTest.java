package com.itxiaox.java.utils.secret;

import org.junit.Test;

import java.util.TreeMap;


public class SignUtilsTest {

    @Test
    public  void testGetSign() {
        //treeMap默认是key升序排序 ,如果需要降序,可以使用Comparator中的compare方法
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("name", "zychen");
        map.put("password", "1234526");
        map.put("project", "base");
        map.put("tenantId", "192319387131");
        String sign = SignUtils.getSign(map, "helloWorld");
        System.out.printf("sign="+sign);
        //9bebb6d90eba4439231478e98686df69
        //90103d4205e81ad35fd5cffde5d3afd5
        //90103d4205e81ad35fd5cffde5d3afd5
    }
}