package com.itxiaox.java.utils.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StringUtilTest {


    @Test
    public void getUUID() {
        String[] strings = StringUtil.getUUID(1);
        System.out.println("UUID:"+ConvertUtils.arraysToString(strings));
        System.out.println("UUID:"+ConvertUtils.arraysToString(strings).length());
    }

    @Test
    public void codeIncrement() {
    }

    @Test
    public void int2String() {
    }

    @Test
    public void double2string() {
    }

    @Test
    public void mul() {
    }

    @Test
    public void div() {
    }

    @Test
    public void isEqual() {
    }

    @Test
    public void isEmpty() {
        String result = null;
        Assert.assertEquals(true,StringUtil.isEmpty(result));
        List<String> list = new ArrayList<>();
        assertTrue(StringUtil.isEmpty(list));
    }

    @Test
    public void getFunName() {
    }

    @Test
    public void formatNullToInteger() {
    }

    @Test
    public void formatNullToDouble() {
    }

    @Test
    public void formatNullToString() {
    }

    @Test
    public void formatNull() {
    }

    @Test
    public void testIsNull1() {
    }

    @Test
    public void arrayToString() {
    }

    @Test
    public void stringSplit() {
    }

    @Test
    public void toDBC() {
    }

    @Test
    public void stringFilter() {
    }

    @Test
    public void getImgSrcList() {
    }

    @Test
    public void getDouble() {
    }

    @Test
    public void createRandomStr() {
    }

    @Test
    public void format() {
    }
}