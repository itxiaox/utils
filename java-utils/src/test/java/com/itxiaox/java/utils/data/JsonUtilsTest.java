package com.itxiaox.java.utils.data;

import com.google.gson.JsonArray;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:xiao
 * Time: 2021/5/4 15:34
 * Description:This is JsonUtilsTest
 */
public class JsonUtilsTest {
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
    public void testList2JsonArray() {
        System.out.println("test");
        List list = new ArrayList();
        list.add(new User("张", 30));
        list.add(new User("李2", 25));
        list.add(new User("昂2", 24));
//        list.add("sss1");
//        list.add("sss2");
//        list.add("sss3");
//        list.add("sss4");
        //集合转JsonArray ， gson的JsonArray
        JsonArray jsonArray = JsonUtils.list2JsonArray(list);
        System.out.println("testList2JsonArray: jsonarray="+jsonArray);
//            System.out.println("testList2JsonArray: JSONArray="+new JSONArray(jsonArray));
        //JsonArray转List
        List list2 = JsonUtils.jsonArray2List(jsonArray);
        System.out.println("testList2JsonArray: list2="+list2);
    }

}
